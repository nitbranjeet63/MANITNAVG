package com.mycalc.ranjeet.manitnavg;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ImageButton;

public class MainActivity extends ActionBarActivity {
    databasehelper db;
    AutoCompleteTextView editSource,editDest;
    Button btnGet;
    Button reset;
    Button btnSearch,btnMaps;
    ImageButton imgOption;
    findpath fp;
    StringBuffer str1 = new StringBuffer();
    String str2[]=new String[]{"jk","indu","inghj","paju","fgghh","plkhg"};
    public int junction[][] = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,181,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,181,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,15,0,0,58,0,0,0,0,0,0,0,0,0,0,0,45,15,0,0,0,0},
            {0,0,0,58,0,54,0,0,0,0,0,0,0,0,0,30,0,0,0,0,0,0},
            {0,0,0,0,40,0,16,0,0,0,0,0,0,125,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,16,0,100,0,0,0,0,0,0,84,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,100,0,26,25,54,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,26,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,25,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,54,0,0,0,32,104,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,32,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,104,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,125,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,84,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,30,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,45,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,245,45,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,245,0,0,0,56},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,45,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,56,0,0}
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new databasehelper(this);
        fp=new findpath();
        List<String> list=new ArrayList();
        editSource=(AutoCompleteTextView)findViewById(R.id.edtSource);
        editDest=(AutoCompleteTextView)findViewById(R.id.edtDestination);
        btnGet=(Button)findViewById(R.id.btnGet);
        reset=(Button)findViewById(R.id.reset);
        btnSearch=(Button)findViewById(R.id.btnSearch);
        btnMaps=(Button)findViewById(R.id.btnMaps);
        imgOption=(ImageButton)findViewById(R.id.imgOption);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        //txtName is a reference of an EditText Field
        imm.hideSoftInputFromWindow(editDest.getWindowToken(), 0);
        try{
            db.createDatabase();
        }catch (IOException ioe) {

            throw new Error("Unable to create database");
        }
        try{
            if(db.openDatabase())
            {
                db.getData(list);
            }
        }catch(SQLException e){
            throw new Error("unable to open database");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,list);
        //AutoCompleteTextView actv= (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
        editSource.setThreshold(1);
        editDest.setThreshold(1);
        editSource.setAdapter(adapter);
        editDest.setAdapter(adapter);
    }

    public void myClick(View view)
    {   if(view==btnGet)
    {
        if(editSource.getText().toString().trim().length()==0||editDest.getText().toString().trim().length()==0)
        {
            Toast.makeText(getBaseContext(),"Please enter all values",Toast.LENGTH_LONG).show();
            return;
        }
        else if(editSource.getText().toString().trim().equalsIgnoreCase(editDest.getText().toString().trim()))
        {
            Toast.makeText(getBaseContext(),"Source and Destination are same",Toast.LENGTH_LONG).show();
            return;
        }
        if(!db.checkRoom(editSource.getText().toString().trim()))
        {
            Toast.makeText(getBaseContext(),"Please Enter Right Source",Toast.LENGTH_LONG).show();
            return;
        }
        else if(!db.checkRoom(editDest.getText().toString().trim()))
        {
            Toast.makeText(getBaseContext(),"Please Enter Right Destination",Toast.LENGTH_LONG).show();
            return;
        }
        Intent in = new Intent("com.mycalc.ranjeet.manitnavg.Response");
        Bundle b = new Bundle();
        b.putString("src",editSource.getText().toString().trim());
        b.putString("dest",editDest.getText().toString().trim());
        in.putExtras(b);
        startActivity(in);
    }
    else if(view==reset)
    {
        editSource.setText("");
        editDest.setText("");
        editSource.requestFocus();
    }
    else if(view == btnSearch)
    {
        Intent in = new Intent("com.mycalc.ranjeet.manitnavg.MainActivity");
        startActivity(in);

    }
    else if(view == btnMaps)
    {
        Intent in = new Intent("com.mycalc.ranjeet.manitnavg.Maps");
        startActivity(in);
    }
    else if(view == imgOption)
    {
       // Intent in = new Intent("com.mycalc.ranjeet.manitnavg.Login");
       // startActivity(in);
        imgOption.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this,Login.class);
                startActivity(intentLoadNewActivity);
            }
        });
    }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
