package com.mycalc.ranjeet.manitnavg;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import java.io.IOException;
import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class track extends Activity implements LocationListener{
    double long2,lat2;
    private LocationManager locationManager;
    databasehelper db;
    //a3Result result1 = new Result();
    int roomid[];
    String direction[];
    int dcount;
    TableLayout table_layout;
    TableRow.LayoutParams rowparam;
    TableLayout.LayoutParams tbparam;
    int count;
    TextView text;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        roomid=getIntent().getExtras().getIntArray("roomid");
        count=getIntent().getExtras().getInt("count");
        dcount=getIntent().getExtras().getInt("dcount");
        direction=getIntent().getExtras().getStringArray("direction");
        text=(TextView)findViewById(R.id.txt2);
        table_layout=(TableLayout)findViewById(R.id.table);
        rowparam=new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tbparam=new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tbparam.setMargins(0, 0, 0, 0);
        for(int k=0,l=1;k<dcount;k++)
        {
            if(direction[k].startsWith("Go")){
                TableRow row = new TableRow(this);
                TextView tv1 = new TextView(this);
                TextView tv = new TextView(this);
                tv1.setText("   ");
                tv.setText(direction[k]);
                tv.setTextSize(25);
                tv.setTextColor(Color.RED);
                tv.setGravity(Gravity.CENTER);
                //row.setGravity(Gravity.CENTER);
                row.setBackgroundColor(Color.argb(300, 0, 0, 0));
                row.addView(tv1);
                row.addView(tv);

                table_layout.addView(row, tbparam);
            }
            else{
                TableRow row = new TableRow(this);
                TextView tv = new TextView(this);
                TextView tv1 = new TextView(this);
                TextView tv2 = new TextView(this);
                tv.setText(" "+(l++)+" ");
                tv.setTextSize(15);
                tv.setTextColor(Color.BLACK);
                tv1.setText(direction[k]+" ");
                tv1.setTextSize(15);
                tv1.setTextColor(Color.BLACK);
                tv2.setText("   ");
                tv2.setTextSize(15);
                tv2.setTextColor(Color.BLACK);
                row.setBackgroundColor(Color.argb(300, 0, 0, 0));
                row.addView(tv);
                row.addView(tv1);
                row.addView(tv2);

                table_layout.addView(row, tbparam);	}
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
                5000,   // 3 sec
                10, this);
        db=new databasehelper(this);
        try{
            db.createDatabase();
        }catch (IOException ioe) {

            throw new Error("Unable to create database");
        }
        try{
            if(db.openDatabase())
            {
                //db.returnLocation()
            }
        }catch (SQLException e) {

            throw new Error("Unable to open database");
        }

    }
    public void onLocationChanged(Location location) {

        String str = "Latitude: "+location.getLatitude()+" Longitude: "+location.getLongitude();
        double long1=location.getLongitude();
        double lat1=location.getLatitude();
        double loc[] = new double[2];
        float []result={0};
        String name=new String();
        double temp=78999.0D;
        int min=0;
//Log.v("count",Integer.toString(count));
        for(int i=0;i<count;i++)
        {
            name=db.returnLocation(roomid[i],loc);
            //Log.v("count",Integer.toString(roomid[i]));
            Location.distanceBetween(lat1,long1,loc[1],loc[0],result);
            if(temp>result[0])
            {
                temp=result[0];
                min=roomid[i];

            }
        }
//Toast.makeText(getBaseContext(),String.valueOf(min), Toast.LENGTH_LONG).show();
        name=db.returnLocation(min,loc);
        text.setText("  "+name);
    }

    @Override
    public void onProviderDisabled(String provider) {

        /******** Called when User off Gps *********/

        Toast.makeText(getBaseContext(), "Gps is off ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {

        /******** Called when User on Gps  *********/

        Toast.makeText(getBaseContext(), "Gps is on ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
}

