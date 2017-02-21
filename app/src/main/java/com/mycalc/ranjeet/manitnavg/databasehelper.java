package com.mycalc.ranjeet.manitnavg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TableLayout;

public class databasehelper extends SQLiteOpenHelper {
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    Cursor c;
    private static final String DATABASE_NAME = "navigation_system";
    public final static String DATABASE_PATH ="/data/data/com.mycalc.ranjeet.manitnavg/databases/";
    public static final int DATABASE_VERSION =1;
    StringBuffer str = new StringBuffer();
    String room[]=new String[2];

    public databasehelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext=context;
    }
    public void createDatabase() throws IOException
    {
        boolean dbExist = checkDataBase();

        if(dbExist)
        {
            Log.v("DB Exists", "db exists");
            // By calling this method here onUpgrade will be called on a
            // Writable database, but only if the version number has been
            // bumped
            onUpgrade(myDataBase, 0, DATABASE_VERSION);
        }

        boolean dbExist1 = checkDataBase();
        if(!dbExist1)
        {
            this.getReadableDatabase();
            try
            {
                this.close();
                Log.v("ghj","kkkkkkkkkkkkkkkk");
                copyDataBase();

            }
            catch (IOException e)
            {
                throw new Error("Error copying database");
            }
        }
    }

    //Check database already exist or not
    private boolean checkDataBase()
    {
        boolean checkDB = false;
        try
        {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            File dbfile = new File(myPath);

            checkDB = dbfile.exists();

        }
        catch(SQLiteException e)
        {
        }
        return checkDB;
    }

    //Copies your database from your local assets-folder to the just created empty database in the system folder
    private void copyDataBase() throws IOException
    {
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);

        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0)
        {
            myOutput.write(buffer, 0, length);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();
    }

    //delete database
    public void db_delete()
    {
        File file = new File(DATABASE_PATH + DATABASE_NAME);
        if(file.exists())
        {
            file.delete();
            System.out.println("delete database file.");
        }
    }

    //Open database
    public boolean openDatabase()
    {
        try {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public synchronized void closeDataBase()throws SQLException
    {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion > oldVersion)
        {
            Log.v("Database Upgrade", "Database version higher than old.");
            db_delete();
        }

    }


    public void getData(List<String> l)
    {
        String      query  = "SELECT RoomName FROM room";
        Cursor      cursor  = myDataBase.rawQuery(query, null);
        if(cursor.moveToFirst())
        {int i=0;
            do{
                l.add(cursor.getString(0));
                i++;
            } while(cursor.moveToNext());
        }
        cursor.close();

    }

    public void intialiseList(List<Integer>[] Adj)
    {
        Cursor cursor = myDataBase.rawQuery("Select Source_Junc,Dest_Junc from Junction_Route", null);
        if(cursor.moveToFirst())
        {
            do{
                Adj[cursor.getInt(0)].add(cursor.getInt(1));
                Adj[cursor.getInt(1)].add(cursor.getInt(0));

            } while(cursor.moveToNext());
        }
        cursor.close();
    }

    public int getRouteNo(String str)
    {
        Log.v("sdff",str);
        Cursor cursor =myDataBase.rawQuery("Select Route from Room where RoomName='"+str+"'", null);
        try{ if(cursor.moveToFirst())
        {
            return cursor.getInt(0);
        }
        else
            return -1;}finally{cursor.close();}
    }
    public int getRouteNo(int src,int dest,int []arr)
    {

        Cursor cursor =myDataBase.rawQuery("Select * from Junction_Route where Source_Junc="+src +" and Dest_junc="+dest, null);
        Log.v("raju1","raju");
        try{if(cursor.moveToFirst())
        {

            arr[0]=cursor.getInt(3);
            arr[1]=cursor.getInt(4);
            return cursor.getInt(2);
        }}finally{cursor.close();}
        cursor =myDataBase.rawQuery("Select * from Junction_Route where source_junc="+dest+" and Dest_junc="+src, null);
        Log.v("raju2","raju");
        try{if(cursor.moveToFirst())
        {
            Log.v("raju3","raju");
            arr[0]=cursor.getInt(4);
            arr[1]=cursor.getInt(3);
            return cursor.getInt(2);
        }
            return -1;}finally{cursor.close();}
    }
    public void getJunc(int route,int[] jun)
    {
        //Log.v("sdff",route);
        Cursor cursor =myDataBase.rawQuery("Select Source_Junc,Dest_Junc from Junction_Route where Route="+route, null);
        if(cursor.moveToFirst())
        {
            jun[0]= cursor.getInt(0);
            jun[1]= cursor.getInt(1);

        }
        cursor.close();

    }
    public int getRoomId(String name)
    {
        Cursor cursor =myDataBase.rawQuery("Select RoomId from Room where RoomName='"+name+"'", null);
        try{ if(cursor.moveToFirst())
        {

            return cursor.getInt(0);
        }
        else
            return -1;}finally{cursor.close();}
    }
    public boolean checkRoom(int id)
    {
        Cursor cursor =myDataBase.rawQuery("Select RoomId from Room where RoomId="+id, null);
        try{ if(cursor.moveToFirst())
        {

            return true;
        }
        else
            return false;}finally{cursor.close();}
    }

    public boolean checkRoom(String name)
    {
        Cursor cursor =myDataBase.rawQuery("Select RoomId from Room where RoomName='"+name+"'", null);
        try{ if(cursor.moveToFirst())
        {

            return true;
        }
        else
            return false;}finally{cursor.close();}
    }
    public void addRoom(int id,String name,String branch,double lng,double lat,int dist)
    {
        myDataBase.execSQL("Insert into Room values("+id+",'"+name+"','"+branch+"',"+lng+","+lat+","+dist+","+1+");");

    }
    public void updateRoom(int id,String name,String branch,double lng,double lat,int dist)
    {
        myDataBase.execSQL("Update  Room set RoomName='"+name+"', BranchName='"+branch+"',Longitude="+lng+",Latitude="+lat+",Distance="+dist+" where RoomId="+id+";");

    }
    public void viewRoom(int id,String arr[])
    {
        Cursor cursor =myDataBase.rawQuery("Select * from Room where RoomId="+id, null);
        try{ if(cursor.moveToFirst())
        {

            arr[0]=Integer.toString(cursor.getInt(0));
            arr[1]=cursor.getString(1);
            arr[2]=cursor.getString(2);
            arr[3]=cursor.getString(3);
            arr[4]=cursor.getString(4);
            arr[5]=cursor.getString(5);

        }

        }finally{cursor.close();}
    }
    public void deleteRoom(int id)
    {
        myDataBase.execSQL("Delete from room where RoomId="+id+";");
    }
    public StringBuffer getRoom(int s,int d)
    {
        str=new StringBuffer();
        if(s<d)
        {
            for(int i=s;i<=d;i++)
            {
                c=myDataBase.rawQuery("SELECT RoomName,distance FROM Room WHERE RoomId="+i, null);
                try{if(c.moveToFirst())
                {
                    str.append(c.getString(0)+" ");
                    str.append(c.getInt(1)+"\n");
                }
                else
                {
                    str.append("DATA NOT FOUND");
                }}finally{c.close();}
            }
        }
        else
        {
            for(int i=s;i>=d;i--)
            {
                c=myDataBase.rawQuery("SELECT RoomName,distance FROM Room WHERE RoomId="+i, null);
                try{if(c.moveToFirst())
                {
                    str.append(c.getString(0)+" ");
                    str.append(c.getInt(1)+"\n");
                }
                else
                {
                    str.append("DATA NOT FOUND");
                }}finally{c.close();}
            }
        }

        return str;
    }



    public int getRoom1(int s,int d,String str[][])
    {
        int k=0;
        if(s<d)
        {
            for(int i=s;i<=d;i++,k++)
            {
                c=myDataBase.rawQuery("SELECT RoomId,RoomName,distance FROM Room WHERE RoomId="+i, null);
                try{if(c.moveToFirst())
                {
                    str[k][0]=c.getString(0);
                    str[k][1]=c.getString(1);
                    str[k][2]=c.getString(2);

                }
                else
                {

                }}finally{c.close();}
            }

        }
        else
        { k=0;
            for(int i=s;i>=d;i--,k++)
            {
                c=myDataBase.rawQuery("SELECT RoomId, RoomName,distance FROM Room WHERE RoomId="+i, null);
                try{if(c.moveToFirst())
                {
                    str[k][0]=c.getString(0);
                    str[k][1]=c.getString(1);
                    str[k][2]=c.getString(2);
                }
                else
                {

                }}finally{c.close();}
            }

        }

        return (k-1);
    }



    public String returnLocation(int id,double[]loc)
    {   String str=new String();
        c=myDataBase.rawQuery("select * from room where RoomId="+id, null);
        try{
            if(c.moveToFirst())
            {
                str=c.getString(1);
                loc[0]=c.getDouble(3);
                loc[1]=c.getDouble(4);
            }
        }finally{c.close();}
        return str;
    }

    public void getRouteRoom(int route,int []arr)
    {
        c=myDataBase.rawQuery("select Start_RoomNo, End_RoomNo from Route where RouteId="+route, null);
        try{do
        {
            if(c.moveToFirst())
            {
                arr[0]=c.getInt(0);
                arr[1]=c.getInt(1);
            }
        }while(c.moveToNext());}finally{c.close();}
    }

    public void getDirection(int src,int dest,StringBuffer str)
    {
        c=myDataBase.rawQuery("select  Junc_No from Junction where Junc_No="+src+" and North_junc="+dest,null);

        try{if(c.moveToFirst())
        {
            str.append("North");
            return;
        }}finally{c.close();}
        c=myDataBase.rawQuery("select  Junc_No from Junction where Junc_No="+src+" and South_junc="+dest,null);

        try{if(c.moveToFirst())
        {
            str.append("South");
            return;
        }}finally{c.close();}

        c=myDataBase.rawQuery("select  Junc_No from Junction where Junc_No="+src+" and East_junc="+dest,null);
        try{
            if(c.moveToFirst())
            {
                str.append("East");
                return;
            }}finally{c.close();}
        c=myDataBase.rawQuery("select  Junc_No from Junction where Junc_No="+src+" and West_junc="+dest,null);
        try{
            if(c.moveToFirst())
            {
                str.append("West");
                return;
            }}finally{c.close();}
        c=myDataBase.rawQuery("select  Junc_No from Junction where Junc_No="+src+" and Up="+dest,null);
        try{
            if(c.moveToFirst())
            {
                str.append("Up");
                return;
            }}finally{c.close();}
        c=myDataBase.rawQuery("select  Junc_No from Junction where Junc_No="+src+" and Down="+dest,null);
        try{
            if(c.moveToFirst())
            {
                str.append("Down");
                return;
            }}finally{c.close();}
    }
}
