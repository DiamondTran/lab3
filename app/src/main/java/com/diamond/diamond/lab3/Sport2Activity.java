package com.diamond.diamond.lab3;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Sport2Activity extends AppCompatActivity {
    private RadioButton ra1;
    private RadioGroup ragrod;

    private Button next;
    private TextView tvhoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport2);
        ragrod= findViewById(R.id.groi);

        next = findViewById(R.id.next);
        tvhoi= findViewById(R.id.tvhoi);
        new RedJson2().execute("http://www.dotplays.com/android/lab3.json");
    }
    public class RedJson2 extends AsyncTask<String , Long , String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content= new StringBuilder();
            try {
                URL url= new URL(strings[0]);

                InputStreamReader inputStream= new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStream);
                String data="";
                while ((data =bufferedReader.readLine())  != null){
                    content.append(data);
                }
                bufferedReader.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject object= new JSONObject(s);
                if (object.has("quiz")){
                    JSONObject jsonObject= object.getJSONObject("quiz");

                    JSONArray array= jsonObject.getJSONArray("maths");
                    for (int i=0; i<array.length(); i++ ){
                        JSONObject object1=  array.getJSONObject(1);
                        String cauhoi= object1.getString("question");
                        tvhoi.setText(cauhoi);
                        final String tl = object1.getString("answer");
                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int radioId= ragrod.getCheckedRadioButtonId();
                                ra1= findViewById(radioId);
                                if (tl.equals(ra1.getText().toString())){
                                    startActivity(new Intent(Sport2Activity.this, ResultActivity.class));
                                    Toast.makeText(Sport2Activity.this, "Bạn đã trả lời chính xác", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(Sport2Activity.this, "Sai rồi", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }




                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
