package com.mycalc.ranjeet.manitnavg;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.IOException;


public class Result extends Activity {

    TextView txt;
    databasehelper db;
    findpath fp;
    String direction[]=new String[200];
    int dcount=0;
    StringBuffer str = new StringBuffer();
    StringBuffer str2 = new StringBuffer();
    MainActivity obj=new MainActivity();
    StringBuffer str1 = new StringBuffer();
    String str3=new String();
    int arr[]=new int[17];
    int arr1[]=new int[2];
    int j=0;
    int srcroomid;
    int destroomid;
    int array[];
    Button btn;
    Button btnSearch,btnMaps;
    TableLayout table_layout;
    TableRow.LayoutParams rowparam;
    TableLayout.LayoutParams tbparam;
    String room[][]=new String[100][3];
    int roomcount=0;
    public int roomid[] = new int[200];
    public int count=0;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        btn =(Button)findViewById(R.id.btn);
        btnSearch=(Button)findViewById(R.id.btnSearch);
        btnMaps=(Button)findViewById(R.id.btnMaps);
        txt = (TextView)findViewById(R.id.txt);
       // txt1 = (TextView)findViewById(R.id.txt1);
        table_layout=(TableLayout)findViewById(R.id.table);
        rowparam=new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tbparam=new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tbparam.setMargins(0, 0, 0, 0);
        srcroomid=getIntent().getExtras().getInt("srcroomid");
        destroomid=getIntent().getExtras().getInt("desroomid");
        array=getIntent().getExtras().getIntArray("arr");
        db=new databasehelper(this);
        fp=new findpath();
        try{
            db.createDatabase();
        }catch (IOException ioe) {

            throw new Error("Unable to create database");
        }
        try{
            if(db.openDatabase())
            {
                //room=db.getData();


                //String s=Integer.toString(srcroomid);
                //String s1=Integer.toString(desroomid);
                //db.getJunc(srcroute,junc1);
                //db.getJunc(desroute,junc2);
                //Log.v("fgh",s+" "+s1);
                // fp.getRoute(obj.junction, junc1[0], junc2[1]);
                //Log.v("source  ",Integer.toString(srcJunc[0]));
                ///Log.v("source  ",Integer.toString(srcJunc[1]));
                //Log.v("dest  ",Integer.toString(destJunc[0]));
                //Log.v("dest  ",Integer.toString(destJunc[1]));
                //check();
                //fp.getRoute(obj.junction, srcJunc, destJunc);
                calculate(array);
            }

        }catch(SQLException e){
            throw new Error("unable to open database");
        }


        //txt.setText(str2+" " +str);
        // txt1.setText(" ");
    }
    public boolean linearIn(int[] outer, int[] inner) {
        int f=0;
        for (int i = 0; i < outer.length; i++) {
            f=0;
            for (int j = 0; j < inner.length; j++) {
                if (outer[i] == inner[j])
                { f=1;break;}
            }
            if(f==0)
                return false;
        }
        return true;
    }


    public void myclick(View v)
    {

        //StringTokenizer st = new StringTokenizer(str.toString()," ");
        Intent i = new Intent("com.mycalc.ranjeet.manitnavg.track");
        //fp=new findpath();
        //fp.getRoute(obj.junction, s, d);
        Bundle b=new Bundle();
        b.putIntArray("roomid", roomid);
        b.putInt("count", count);
        b.putInt("dcount", dcount);
        //b.put
        //b.putAll("ghj",table_layout);
        b.putStringArray("direction", direction);
        i.putExtras(b);
        startActivity(i);

    }
    public void myClick(View view)
    {   if(view==btnSearch)
    {
        Intent in = new Intent("com.mycalc.ranjeet.manitnavg.MainActivity");
        startActivity(in);
    }
    else if(view == btnMaps){
        Intent in = new Intent("com.mycalc.ranjeet.manitnavg.Maps");
        startActivity(in);
    }

    }

    public void calculate(int junc[])
    {
        int i=0;
        Log.v("fgh", "fffffffffffffffffffffffffffffffffffmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
        while(junc[i]!=-1)
        {
            Log.v("in",Integer.toString(junc[i]));
            str2.append(junc[i]);
            arr[i]= junc[i];
            i++;
        }
        StringBuffer str2= new StringBuffer();
        if(i==2)
        {
            db.getDirection(arr[0], arr[1], str2);
            str.append("\nGO "+str2+"\n");
            addtable(str2);
            str1=db.getRoom(srcroomid, destroomid);
            roomcount=db.getRoom1(srcroomid,destroomid,room);
            str.append("\n"+str1);
            addtable2(room,roomcount);
        }
        else
        {
            int k=0;
            str2= new StringBuffer();
            TextView tv=new TextView(this);
            TableRow row=new TableRow(this);
            db.getDirection(arr[k], arr[k+1], str2);
            str.append("\nGO "+str2+"\n");
            addtable(str2);
            j=db.getRouteNo(arr[k], arr[k+1],arr1);
            Log.v("array",srcroomid+" "+Integer.toString(arr1[1]));
            str1=db.getRoom(srcroomid, arr1[1]);
            roomcount=db.getRoom1(srcroomid,arr1[1],room);
            str.append("\n"+str1);
            addtable2(room,roomcount);
            for( k=1;k<i-2;k++)
            {
                str2= new StringBuffer();
                j=db.getRouteNo(arr[k], arr[k+1],arr1);
                //db.getRouteRoom(j,arr1);
                //Log.v("array",Integer.toString(arr[k])+" "+Integer.toString(arr[k+1]));
                String s3=Integer.toString(arr1[0]);
                String s4=Integer.toString(arr1[1]);
                Log.v("i",s3+" "+s4);
                str1=db.getRoom(arr1[0], arr1[1]);

                db.getDirection(arr[k], arr[k+1], str2);
                str.append("\nGO "+str2+"\n");
                addtable(str2);
                roomcount=db.getRoom1(arr1[0],arr1[1],room);
                str.append("\n"+str1);
                addtable2(room,roomcount);

            }
            str2=new StringBuffer();
            j=db.getRouteNo(arr[k], arr[k+1],arr1);
            db.getDirection(arr[k], arr[k+1], str2);
            str.append("\nGO "+str2+"\n");
            addtable(str2);
            str1=db.getRoom(arr1[0], destroomid);
            roomcount=db.getRoom1(arr1[0],destroomid,room);
            str.append("\n"+str1);
            addtable2(room,roomcount);
        }

    }
    public void addtable(StringBuffer str)
    {
        TableRow row = new TableRow(this);
        TextView tv1 = new TextView(this);
        TextView tv = new TextView(this);
        tv1.setText("   ");
        tv.setText("Go "+str);
        direction[dcount++]="Go "+str;
        tv.setTextSize(25);
        tv.setTextColor(Color.RED);
        tv.setGravity(Gravity.CENTER);
        //row.setGravity(Gravity.CENTER);
        row.setBackgroundColor(Color.argb(300, 0, 0, 0));
        row.addView(tv1);
        row.addView(tv);

        table_layout.addView(row, tbparam);
    }
    /*public void addtable1(StringBuffer str)
    {
        TableRow row = new TableRow(this);
        TextView tv = new TextView(this);
        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
            tv.setText(str);
            tv.setTextSize(15);
            tv.setTextColor(Color.BLACK);
            tv1.setText(" 1 ");
            tv1.setTextSize(15);
            tv1.setTextColor(Color.BLACK);
            tv2.setText(" 5 ");
            tv2.setTextSize(15);
            tv2.setTextColor(Color.BLACK);
            row.setBackgroundColor(Color.argb(300, 0, 0, 0));
            row.addView(tv2);
            row.addView(tv);
            row.addView(tv1);

            table_layout.addView(row, tbparam);
    }*/
    public void addtable2(String str[][],int r)
    {for(int k=0;k<=r;k++){
        TableRow row = new TableRow(this);
        TextView tv = new TextView(this);
        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        tv.setText(str[k][0]+"  ");
        roomid[count++]=Integer.parseInt(str[k][0]);
        direction[dcount++]=str[k][1];
        tv.setTextSize(15);
        tv.setTextColor(Color.BLACK);
        tv1.setText(str[k][1]+" ");
        tv1.setTextSize(15);
        tv1.setTextColor(Color.BLACK);
        tv2.setText(str[k][2]+"  ");
        tv2.setTextSize(15);
        tv2.setTextColor(Color.BLACK);
        row.setBackgroundColor(Color.argb(300, 0, 0, 0));
        row.addView(tv);
        row.addView(tv1);
        row.addView(tv2);

        table_layout.addView(row, tbparam);	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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
