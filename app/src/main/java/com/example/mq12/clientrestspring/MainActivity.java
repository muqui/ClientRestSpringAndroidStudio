package com.example.mq12.clientrestspring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
