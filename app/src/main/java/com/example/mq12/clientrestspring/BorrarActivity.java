package com.example.mq12.clientrestspring;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BorrarActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textViewid;
    Button botonCancelar, botonAceptar;
    String cadena = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar);
        textViewid = (TextView) findViewById(R.id.textViewID);
        botonAceptar = (Button) findViewById(R.id.buttonAceptar);
        botonCancelar = (Button) findViewById(R.id.buttonCancelar);
        botonAceptar.setOnClickListener(this);
        botonCancelar.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle !=  null){
             cadena = (String)bundle.get("id");
            textViewid.setText("Eliminara el partido  con ID " +cadena);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonAceptar:
                super.onStart();
                new BorrarActivity.HttpRequestTask(this).execute();
                break;
            case R.id.buttonCancelar:
                Intent intent  = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;


        }

    }
    public class HttpRequestTask extends AsyncTask<Void, Void,  String> {
        private Context mContext;

        public HttpRequestTask(Context context){ // este constructor permite usar un Toast
            mContext = context;
        }


        @Override
        protected  String doInBackground(Void... params) {

            try {

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                String getUrl = "http://192.168.1.68:8080/partido/"+ cadena;
                restTemplate.delete(getUrl);
                return "OK";
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String cadena) {
            Intent intent  = new Intent(mContext, MainActivity.class);
            mContext.startActivity(intent);

        }

    }

}
