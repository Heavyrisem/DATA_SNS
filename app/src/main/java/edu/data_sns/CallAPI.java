package edu.data_sns;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CallAPI extends AsyncTask<String, String, String> {

    // inner interface (일종의 class)
    public interface AsyncResponse {
        void processFinish(String output);
    }

    // 변수 선언 delegate 뜻 : 대리인, 대행자
    public AsyncResponse delegate = null;

    // 생성자
    public CallAPI(AsyncResponse delegate){
        this.delegate = delegate;
    }

    // AsyncTask 실행 전에 실행되는 함수
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    // AsyncTask 실행 중에 실행되는 함수
    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0]; // URL to call
        String data = params[1]; //data to post

        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            out.close();

            urlConnection.connect();

            StringBuilder sb = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ( (line = rd.readLine()) != null ) {
                sb.append(line);
            }
            Log.d("DATA_SNS POST_RESPONSE", sb.toString());

            return sb.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "오류";
        }
    }

    // AsyncTask 실행 후에 실행되는 함수
    @Override
    protected void onPostExecute(String res){
        delegate.processFinish(res);
    }
}