package com.mycalc.ranjeet.manitnavg;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;



public class editdatabase extends Activity {
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdatabase);
        btn1=(Button)findViewById(R.id.btnBranch);
    }
    public void myclick(View v)
    {
        int id=v.getId();
        if(id==R.id.btnBranch)
        {
            Intent i = new Intent("com.mycalc.ranjeet.manitnavg.Building");
            startActivity(i);
        }
        if(id==R.id.btnBuilding)
        {
            Intent i = new Intent("com.mycalc.ranjeet.manitnavg.Branch");
            startActivity(i);
        }
        if(id==R.id.btnRoom)
        {
            Intent i = new Intent("com.mycalc.ranjeet.manitnavg.Room");
            startActivity(i);
        }
        if(id==R.id.btnRoute)
        {
            Intent i = new Intent("com.mycalc.ranjeet.manitnavg.Route");
            startActivity(i);
        }
        if(id==R.id.btnJunction)
        {
            Intent i = new Intent("com.mycalc.ranjeet.manitnavg.Junction");
            startActivity(i);
        }
        if(id==R.id.btnJunctionRoute)
        {
            Intent i = new Intent("com.mycalc.ranjeet.manitnavg.Junction_Route");
            startActivity(i);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editdatabase, menu);
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
