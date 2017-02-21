package com.mycalc.ranjeet.manitnavg;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Maps extends Activity {
    Button btnSearch,btnMaps,btnGF,btnFF,btnSF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        btnSearch=(Button)findViewById(R.id.btnSearch);
        btnMaps=(Button)findViewById(R.id.btnMaps);
        btnGF=(Button)findViewById(R.id.btnGroundFloor);

    }
    public void myClick(View v)
    {   if(v==btnSearch)
    {
        Intent in = new Intent("com.mycalc.ranjeet.manitnavg.MainActivity");
        startActivity(in);
    }
    else if(v == btnMaps){
        Intent in = new Intent("com.mycalc.ranjeet.manitnavg.Maps");
        startActivity(in);
    }
    else if(v == btnGF){
        Intent in = new Intent("com.mycalc.ranjeet.manitnavg.MapView");
        startActivity(in);
    }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
