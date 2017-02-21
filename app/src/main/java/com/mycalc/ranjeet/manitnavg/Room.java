package com.mycalc.ranjeet.manitnavg;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.io.IOException;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Room extends Activity {

    databasehelper db;
    Button btnAdd;
    Button btnDelete;
    Button btnUpdate;
    Button btnView;
    EditText editLattitude;
    EditText editLongitude;
    EditText editBranch;
    EditText editName;
    EditText editId;
    EditText editDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        editId = (EditText)findViewById(R.id.editRoomId);
        editName = (EditText)findViewById(R.id.editRoomName);
        editBranch = (EditText)findViewById(R.id.editBranch);
        editLongitude = (EditText)findViewById(R.id.longitude);
        editLattitude = (EditText)findViewById(R.id.lattitude);
        editDistance=(EditText)findViewById(R.id.distance);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnView = (Button)findViewById(R.id.btnView);
        db=new databasehelper(this);
        try{
            db.createDatabase();
        }catch (IOException ioe) {

            throw new Error("Unable to create database");
        }
        try{
            if(db.openDatabase())
            {
            }
        }catch(SQLException e)
        {
            throw new Error("unable to open database");
        }
    }

    public void clearText()
    {
        editId.setText("");
        editName.setText("");
        editBranch.setText("");
        editLongitude.setText("");
        editLattitude.setText("");
        editDistance.setText("");
        editId.requestFocus();
    }

    public void myclick(View view)
    {
        int id;
        String name=new String();
        String branch=new String();
        int distance;
        double longitude;
        double latitude;
        id=Integer.parseInt(editId.getText().toString().trim());

        if (view == btnAdd) {
            if (editId.getText().toString().trim().length() == 0 || editName.getText().toString().trim().length() == 0  || editDistance.getText().toString().trim().length() == 0  || editLongitude.getText().toString().trim().length() == 0 || editLattitude.getText().toString().trim().length() == 0  || editBranch.getText().toString().trim().length() == 0)
            {Toast.makeText(getBaseContext(), "Please Enter All Values",Toast.LENGTH_LONG).show();
                return;}
            distance=Integer.parseInt(editDistance.getText().toString().trim());
            branch=editBranch.getText().toString().trim();
            name=editName.getText().toString().trim();
            longitude=Double.parseDouble(editLongitude.getText().toString().trim());
            latitude=Double.parseDouble(editLattitude.getText().toString().trim());
            if(!db.checkRoom(id)){
                db.addRoom(id,name,branch,longitude,latitude,distance);
                Toast.makeText(getBaseContext(), "RECORD ADDED",Toast.LENGTH_LONG).show();
                clearText();}
            else
            {
                Toast.makeText(getBaseContext(), "Room Already Exist",Toast.LENGTH_LONG).show();
            }
        }
        if (view == btnDelete)
        {
            if (editId.getText().toString().trim().length() == 0)
            {
                Toast.makeText(getBaseContext(), "Please Enter RoomId",Toast.LENGTH_LONG).show();
                return;
            }
            if(db.checkRoom(Integer.parseInt(editId.getText().toString().trim()))){

                db.deleteRoom(id);
                Toast.makeText(getBaseContext(), "Room Deleted",Toast.LENGTH_LONG).show();
            } else
            {
                Toast.makeText(getBaseContext(), "Invalid Room Id",Toast.LENGTH_LONG).show();
            }
            clearText();
        }
        if (view == btnUpdate)
        {
            if (editId.getText().toString().trim().length() == 0)
            {
                Toast.makeText(getBaseContext(), "Please Enter RoomId",Toast.LENGTH_LONG).show();
                return;
            }
            distance=Integer.parseInt(editDistance.getText().toString().trim());
            branch=editBranch.getText().toString().trim();
            name=editName.getText().toString().trim();
            longitude=Double.parseDouble(editLongitude.getText().toString().trim());
            latitude=Double.parseDouble(editLattitude.getText().toString().trim());
            if(db.checkRoom(Integer.parseInt(editId.getText().toString().trim()))){


                db.updateRoom(id,name,branch,longitude,latitude,distance);
                Toast.makeText(getBaseContext(), "Record Updated",Toast.LENGTH_LONG).show();
            } else
            {
                Toast.makeText(getBaseContext(), "Invalid Room Id",Toast.LENGTH_LONG).show();
            }
            clearText();
        }
        if (view == btnView)
        {
            if (editId.getText().toString().trim().length() == 0)
            {
                Toast.makeText(getBaseContext(), "Please Eter Room Id",Toast.LENGTH_LONG).show();
                return;
            }
            if(db.checkRoom(Integer.parseInt(editId.getText().toString().trim()))){

                String arr[] = new String[6];

                db.viewRoom(id,arr);
                Log.v("arrr",arr[0]+ " "+arr[1]+" "+arr[2]);
                editId.setText(arr[0]);
                editName.setText(arr[1]);
                editBranch.setText(arr[2]);
                editLongitude.setText(arr[3]);
                editLattitude.setText(arr[4]);
                editDistance.setText(arr[5]);
            } else
            {
                Toast.makeText(getBaseContext(), "Invalid Room Id",Toast.LENGTH_LONG).show();
                clearText();
            }


        }

    }
    public void showMessage(String s, String s1)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(s);
        builder.setMessage(s1);
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_room, menu);
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
