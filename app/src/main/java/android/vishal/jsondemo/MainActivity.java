package android.vishal.jsondemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity  {


    public class DownloadTask extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                urlConnection =(HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                StringBuilder total = new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null){

                    total.append(line).append('\n');

                }

                result = total.toString();

                return result;

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);

                 String weatherInfo = jsonObject.getString("weather");

                Log.i("Web Content",weatherInfo);

                JSONArray jsonArray = new JSONArray(weatherInfo);

                for (int i = 0; i < jsonArray.length(); i++){

                    JSONObject jsonPart = jsonArray.getJSONObject(i);

                    Log.i("main",jsonPart.getString("main"));

                    Log.i("description",jsonPart.getString("description"));


                }


            } catch (JSONException e) {

                e.printStackTrace();

            }

            Log.i("web Content",result);



        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();

        task.execute("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");

    }
}
