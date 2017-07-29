package com.example.mq12.clientrestspring;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class EditarActivity extends AppCompatActivity implements View.OnClickListener {
    Partido partido = new Partido();
    Button boton;
    TextView textViewId;
    EditText editTextLocal, editTextGolesLocal, editTextVisita, editTextGolesVisita, editTextEstadio, editTextFecha, editTextJornada, editTextTorneo;
    String id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        textViewId = (TextView)findViewById(R.id.textViewEditarID);
        boton = (Button) findViewById(R.id.buttonEditarGuardar);
        boton.setOnClickListener(this);
        editTextLocal = (EditText) findViewById(R.id.editTextEditarLocal);
        editTextGolesLocal = (EditText) findViewById(R.id.editTextEditarGolesLocal);
        editTextVisita = (EditText) findViewById(R.id.editTextEditarVisita);
        editTextGolesVisita = (EditText) findViewById(R.id.editTextEditarGolesVisita);
        editTextEstadio = (EditText) findViewById(R.id.editTextEditarEstadio);
        editTextFecha = (EditText) findViewById(R.id.editTextEditarFEcha);
        editTextJornada = (EditText) findViewById(R.id.editTextEditarJornada);
        editTextTorneo = (EditText) findViewById(R.id.editTextEditarTorneo);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle !=  null){
            id = (String)bundle.get("id");
            textViewId.setText("Editar  el partido  con ID " +id);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonEditarGuardar:
                super.onStart();
                new EditarActivity.HttpRequestTask(this).execute();
                break;



        }


    }
    private void editar() {

        partido.setLocal(editTextLocal.getText().toString());
        partido.setGoleslocal(Integer.parseInt(editTextGolesLocal.getText().toString()));
        partido.setVisita(editTextVisita.getText().toString());
        partido.setGolesvista(Integer.parseInt(editTextGolesVisita.getText().toString()));
        partido.setEstadio(editTextEstadio.getText().toString());
        partido.setFechaTemporal(editTextFecha.getText().toString());
        partido.setJornada(editTextJornada.getText().toString());
        partido.setTorneo(editTextTorneo.getText().toString());



    }
    public class HttpRequestTask extends AsyncTask<Void, Void,  Partido> {
        private Context mContext;

        public HttpRequestTask(Context context){ // este constructor permite usar un Toast
            mContext = context;
        }


        @Override
        protected  Partido doInBackground(Void... params) {

            try {
               editar();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                String getUrl = "http://192.168.1.68:8080/partido/" +id;
                restTemplate.put(getUrl, partido);
                return partido;
            } catch (Exception e) {
                Log.e("EditarActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Partido partido) {
            Intent intent  = new Intent(mContext, MainActivity.class);
            mContext.startActivity(intent);

        }

    }
}
