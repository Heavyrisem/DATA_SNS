package edu.data_sns;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn = findViewById(R.id.login_btn);
        Button signupBtn = findViewById(R.id.signup_btn);

        loginBtn.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Log.d("DATA_SNS", "Login Button Clicked !!!");

                        Intent first_intent = new Intent(MainActivity.this, FeedActivity.class);
                        startActivity(first_intent);

                    }
                }
        );
        signupBtn.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Log.d("DATA_SNS", "Signup Button Clicked !!!");

                        Intent first_intent = new Intent(MainActivity.this, SignupActivity.class);
                        startActivity(first_intent);
                    }
                }
        );
    }
}