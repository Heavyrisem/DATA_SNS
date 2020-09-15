package edu.data_sns;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    AsyncTask<URL, Integer, Integer> httpRequest;
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

//                        Intent first_intent = new Intent(MainActivity.this, FeedActivity.class);
//                        startActivity(first_intent);

                        String user_id = ( (EditText)findViewById(R.id.user_id) ).getText().toString();
                        String user_pw = ( (EditText)findViewById(R.id.user_pw) ).getText().toString();

                        try{
                            httpRequest = new HttpRequest();
                            httpRequest.execute(new URL("http://kumquat601.duckdns.org/data_sns/login.php?user_id=" + user_id + "&user_pw=" + user_pw));
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

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
    void afterGetHttpResponse(String result){
        try {
            JSONObject http_res = new JSONObject(result);
            if( http_res.getString("result") == "1" ){
                Log.d("DATA_SNS Login", "로그인 성공");
            }
            else{
                Handler handler =  new Handler(getApplicationContext().getMainLooper());
                handler.post( new Runnable(){
                    public void run(){
                        Toast.makeText( getApplicationContext(), "로그인 정보를 확인해주세요.", Toast.LENGTH_SHORT ).show();
                    }
                });
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    class HttpRequest extends AsyncTask<URL, Integer, Integer>{
        @Override
        protected Integer doInBackground(URL... urls) {
            try{
//                    URL url = new URL("http://kumquat601.duckdns.org/data_sns/login.php?user_id=kumquat601&user_pw=123123");
                HttpURLConnection urlConnection = (HttpURLConnection) urls[0].openConnection();
                try {
                    StringBuilder sb = new StringBuilder();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String line;
                    while ( (line = rd.readLine()) != null ) {
                        sb.append(line);
                    }
                    Log.d("DATA_SNS", sb.toString());
                    afterGetHttpResponse(sb.toString());
                    return 1;
                }
                catch (Exception e){
                    e.printStackTrace();
                    return 0;
                }
                finally {
                    urlConnection.disconnect();
                }
            }
            catch (Exception e){
                e.printStackTrace();
                return 0;
            }
            finally {
                this.cancel(true);
            }
        }
    }
}