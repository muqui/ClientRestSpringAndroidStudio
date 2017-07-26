package com.example.mq12.clientrestspring;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;

public class InsertarActivity extends AppCompatActivity implements View.OnClickListener {
    Partido partido = new Partido();
    Button boton;
    EditText editTextLocal, editTextGolesLocal, editTextVisita, editTextGolesVisita, editTextEstadio, editTextFecha, editTextJornada, editTextTorneo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);
        boton = (Button) findViewById(R.id.buttonAddGuardar);
        boton.setOnClickListener(this);
        editTextLocal = (EditText) findViewById(R.id.editTextAddLocal);
        editTextGolesLocal = (EditText) findViewById(R.id.editTextAddGolesLocal);
        editTextVisita = (EditText) findViewById(R.id.editTextAddVisita);
        editTextGolesVisita = (EditText) findViewById(R.id.editTextAddGolesVisita);
        editTextEstadio = (EditText) findViewById(R.id.editTextAddEstadio);
        editTextFecha = (EditText) findViewById(R.id.editTextAddFEcha);
        editTextJornada = (EditText) findViewById(R.id.editTextAddJornada);
        editTextTorneo = (EditText) findViewById(R.id.editTextAddTorneo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddGuardar:
                super.onStart();
                new HttpRequestTask(this).execute();

                break;
        }
    }

    private void guardar() {

         partido.setLocal(editTextLocal.getText().toString());
         partido.setGoleslocal(Integer.parseInt(editTextGolesLocal.getText().toString()));
        partido.setVisita(editTextVisita.getText().toString());
        partido.setGolesvista(Integer.parseInt(editTextGolesVisita.getText().toString()));
        partido.setEstadio(editTextEstadio.getText().toString());
        partido.setFechaTemporal(editTextFecha.getText().toString());
        partido.setJornada(editTextJornada.getText().toString());
        partido.setTorneo(editTextTorneo.getText().toString());



    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Partido> {
        private Context mContext;

        public HttpRequestTask(Context context){ // este constructor permite usar un Toast
            mContext = context;
        }


        @Override
        protected Partido doInBackground(Void... params) {

            try {
                guardar();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                String getUrl = "http://192.168.1.67:8080/partido";
                 Partido result = restTemplate.postForObject(getUrl, partido, Partido.class);
                return result;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute( Partido partido) {
            Log.d("pardido " + partido.getId(), "resul");
            Toast toast= Toast.makeText(mContext, "Partido insetado: " +  partido.getId(), Toast.LENGTH_LONG);
            toast.show();

        }

    }

}
