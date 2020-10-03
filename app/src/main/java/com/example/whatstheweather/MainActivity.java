package com.example.whatstheweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;

    public void onclick(View view){
        Log.i("tag","clicked");
        weather weather1 = new weather();

        
        try {
            editText = findViewById(R.id.editTextTextPersonName);

            weather1.execute("https://openweathermap.org/data/2.5/weather?q="+ editText.getText().toString() +"&appid=439d4b804bc8187953eb36d2a8c26a02");
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "SORRY AN ERROR OCCURED TRY CHANGING NAME", Toast.LENGTH_SHORT).show();
        }

        // how to hide the keyboard

        InputMethodManager mgr =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editText.getWindowToken(),0);


    }



    public  class weather extends AsyncTask<String , Void , String> {
        @Override
        protected String doInBackground(String... strings) {
            String reader="";
            URL url;

            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);

                urlConnection = (HttpURLConnection) url.openConnection();


                InputStream inputStream ;

                inputStream = urlConnection.getInputStream();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int form = inputStreamReader.read();

                while( form != -1){
                    char form1 = (char)form;

                    reader += form1;

                    form = inputStreamReader.read();

                }


                return reader;


            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }




        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);

                String weatherInfo = jsonObject.getString("weather");

                Log.i("Weather content", weatherInfo);

                JSONArray jsonArray = new JSONArray(weatherInfo); // [] inside this is array


                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject part = jsonArray.getJSONObject(i);
                    textView = findViewById(R.id.textView2);

                    Log.i("id", part.getString("id"));
                    Log.i("description", part.getString("description"));

                    textView.setText(part.getString("main") +" : "+ "\r\n" + part.getString("description") );


                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "SORRY AN ERROR OCCURED", Toast.LENGTH_SHORT).show();
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }
}