package com.example.mq12.clientrestspring;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onStart();
        new MainActivity.HttpRequestTask(this).execute();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addPartido:
                Toast.makeText(getApplicationContext(), "add...", Toast.LENGTH_SHORT).show();
                Intent intent  = new Intent(this, InsertarActivity.class);
                startActivity(intent);
                break;
            case R.id.getPartido:
                Toast.makeText(getApplicationContext(), "get partido...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.getPartidos:
                Toast.makeText(getApplicationContext(), "getPartidos...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actualizarPartido:
                Toast.makeText(getApplicationContext(), "update...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.borrarPartido:
                Toast.makeText(getApplicationContext(), "delete...", Toast.LENGTH_SHORT).show();
                break;
            default:

        }
        return false;
    }



    public class HttpRequestTask extends AsyncTask<Void, Void, List<Partido>> {
        private Context mContext;
        List<Partido> partidos;
        public HttpRequestTask(Context context){ // este constructor permite usar un Toast
            mContext = context;
        }


        @Override
        protected  List<Partido> doInBackground(Void... params) {

            try {

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                String getUrl = "http://192.168.1.68:8080/partidos";
                ResponseEntity<Partido[]> entity = restTemplate.getForEntity(new URI(getUrl), Partido[].class);
                HttpStatus statusCode = entity.getStatusCode();
                partidos = Arrays.asList(entity.getBody());

                return partidos;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(  List<Partido> partidos) {
           Log.d("pardido " + partidos, "resul");
            Toast toast= Toast.makeText(mContext, "Partido insetado: " + partidos.get(0).getId(), Toast.LENGTH_LONG);
            toast.show();

        }

    }
}
