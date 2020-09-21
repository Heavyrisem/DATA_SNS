package edu.data_sns;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

public class UploadActivity extends AppCompatActivity {

    final int GALLERY_REQUEST = 0;
    ImageView thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        final EditText title = findViewById(R.id.title);
        final EditText content = findViewById(R.id.content);

        thumbnail = findViewById(R.id.uploaded_images);

        Button imgUploadBtn = findViewById(R.id.image_upload);
        imgUploadBtn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                    }
                }
        );

        Button uploadBtn = findViewById(R.id.upload);
        uploadBtn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        edu.data_sns.CallAPI postReq = new edu.data_sns.CallAPI(new edu.data_sns.CallAPI.AsyncResponse() {
                            @Override
                            public void processFinish(String res) {
                                Log.d("DATA_SNS POST_RESPONSE", res);
                            }
                        });

                        postReq.execute("http://kumquat601.duckdns.org/data_sns/upload.php",
                                "title=" + title.getText().toString()
                                        +"&content=" + content.getText().toString()
                        );
                    }
                }
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);
                        thumbnail.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
        }
    }
}