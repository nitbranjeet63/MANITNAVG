package com.mycalc.ranjeet.manitnavg;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import java.io.IOException;
import java.util.*;
import android.content.Intent;
import android.database.SQLException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import java.util.Stack;


public class Response extends ActionBarActivity implements OnItemClickListener {
    databasehelper db;
    findpath fp;
    String src;
    String dest;
    int s,d;
    int inner[];
    MainActivity obj=new MainActivity();
    int srcJunc[],destJunc[];
    static Stack<Integer> visitedNodesStack;
    static boolean found = false;
    private static int numberOfCycle = 0;
    ListView listview;
    StringBuffer str[] = new StringBuffer[10];
    String str1[] ;
    String str2[];
    int r=0;
    Button btn;
    int srcroute=-1,desroute=-1,srcroomid=-1,desroomid=-1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        btn=(Button)findViewById(R.id.btn);
        Log.v("hi","jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
        src=getIntent().getExtras().getString("src");
        dest=getIntent().getExtras().getString("dest");

        int junc1[]=new int[]{-1,-1};
        int junc2[]=new int[]{-1,-1};
        listview = (ListView)findViewById(R.id.listView);
        List<Integer>[] Adj = new ArrayList[23];

        for(int i=1;i<22;i++)
            Adj[i]=new ArrayList<Integer>();
        db=new databasehelper(this);
        try{
            db.createDatabase();
        }catch (IOException ioe) {

            throw new Error("Unable to create database");
        }
        try{
            if(db.openDatabase())
            {

                db.intialiseList(Adj);
                srcroute=db.getRouteNo(src);
                desroute=db.getRouteNo(dest);
                srcroomid=db.getRoomId(src);
                desroomid=db.getRoomId(dest);
                db.getJunc(srcroute,junc1);
                db.getJunc(desroute,junc2);
                srcJunc=junc1;
                destJunc=junc2;
                check();
            }
        }catch(SQLException e){
            throw new Error("unable to open database");
        }
        visitedNodesStack=new Stack<Integer>();
        findAllDistancebetweentwovertices(Adj,s,d);
        if(!found){
            Log.v("No path found","no path found");
        }
        str1=new String[r];
        str2=new String[r];
        for(int k=0;k<r;k++)
        {
            str1[k]=str[k].toString();
            str2[k]="Path "+(k+1);
        }
        //listview.setAdapter(null);
        //ArrayAdapter adapter = new ArrayAdapter(Response.this,android.R.layout.select_dialog_item,str1);
        ArrayAdapter adapter = new ArrayAdapter(Response.this,android.R.layout.select_dialog_item,str2);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        //adapter.clear();
        //adapter.remove(0);
        //adapter.notifyDataSetChanged();
    }

    public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
        // TODO Auto-generated method stub
        //String item = adapter.getItemAtPosition(position).toString();

        //StringTokenizer st = new StringTokenizer(item," ");
        StringTokenizer st = new StringTokenizer(str1[position]," ");
        int arr[]=new int[20];int j=0;for(int k=0;k<20;k++)arr[k]=-1;
        Intent i = new Intent("com.mycalc.ranjeet.manitnavg.Result");

        while (st.hasMoreTokens()) {
            //Log.v("str",st.nextToken());
            arr[j++]=Integer.parseInt(st.nextToken());
        }

        //Toast.makeText(this, "CLICK: " + item, Toast.LENGTH_SHORT).show();
        if(linearIn(inner,arr)){
            Bundle b = new Bundle();
            b.putInt("srcroomid", srcroomid);
            b.putInt("desroomid", desroomid);
            b.putIntArray("arr", arr);
            i.putExtras(b);
            startActivity(i);}
        else
        {
            if(!linearIn(srcJunc[0],arr))
            {
                insertFirst(srcJunc[0],arr);
            }
            else if(!linearIn(srcJunc[1],arr))
            {
                insertFirst(srcJunc[1],arr);
            }
            if(!linearIn(destJunc[0],arr))
            {
                Log.v("kl","hjk");
                insertLast(destJunc[0],arr);
            }
            else if(!linearIn(destJunc[1],arr))
            {
                Log.v("hjkl","ghjkl");
                insertLast(destJunc[1],arr);
            }
            Bundle b = new Bundle();
            b.putInt("srcroomid", srcroomid);
            b.putInt("desroomid", desroomid);
            b.putIntArray("arr", arr);
            i.putExtras(b);
            startActivity(i);
        }
    }
    private   void findAllDistancebetweentwovertices(List<Integer>[] adj,int startingVertix,int endVertex){
        if(startingVertix==endVertex){
            printNodesInStack(visitedNodesStack);
            str[r].append(endVertex);r++;
            found=true;
            return;
        }
        if(!visitedNodesStack.contains(startingVertix)){
            visitedNodesStack.push(startingVertix);
            if(adj[startingVertix]!=null){
                for(int i:adj[startingVertix]){
                    if(!visitedNodesStack.contains(i)){
                        findAllDistancebetweentwovertices(adj, i, endVertex);
                    }
                }
                visitedNodesStack.pop();
            }

        }
    }
    private  void printNodesInStack(Stack<Integer> stk){
        ListIterator<Integer> itr=stk.listIterator();
        str[r]=new StringBuffer();
        while(itr.hasNext()){
            str[r].append(itr.next()+" ");
        }

    }
    public void myclick(View v)
    {
        Intent i = new Intent("com.mycalc.ranjeet.manitnavg.Result");
        fp=new findpath();
        fp.getRoute(obj.junction, s, d);
        Bundle b=new Bundle();
        b.putIntArray("arr", fp.junc);
        b.putInt("srcroomid", srcroomid);
        b.putInt("desroomid", desroomid);
        i.putExtras(b);
        startActivity(i);

    }

    public void check()
    {
        inner=new int[]{srcJunc[0],srcJunc[1],destJunc[0],destJunc[1]};
        boolean b;
        boolean flag=true;
        if(flag){fp=new findpath();
            fp.getRoute(obj.junction, srcJunc[0], destJunc[0]);
            b=linearIn(inner,fp.junc);
            if(b) { Log.v("fgh","higgggggggggg");flag=false; s=srcJunc[0];d=destJunc[0];}}
        if(flag){fp=new findpath();
            fp.getRoute(obj.junction, srcJunc[0], destJunc[1]);
            b=linearIn(inner,fp.junc);
            if(b) { Log.v("fgh","immmmmmm"); flag=false;s=srcJunc[0];d=destJunc[1];}}
        if(flag){fp=new findpath();
            fp.getRoute(obj.junction, srcJunc[1], destJunc[0]);
            b=linearIn(inner,fp.junc);
            if(b) { Log.v("fgh","hello"); flag=false;s=srcJunc[1];d=destJunc[0];}}
        if(flag){ fp=new findpath();
            fp.getRoute(obj.junction, srcJunc[1], destJunc[1]);
            b=linearIn(inner,fp.junc);
            if(b){ Log.v("fgh","hi");s=srcJunc[1];d=destJunc[1];}}
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


    public boolean linearIn(int outer, int[] inner) {
        for (int j = 0; j < inner.length; j++) {
            if (outer == inner[j])
            { return true;}
        }
        return false;
    }

    public void insertFirst(int num,int []arr)
    {
        int i=0;while(arr[i]!=-1){i++;}
        for(int k=i;k>0;k--)arr[k]=arr[k-1];arr[0]=num;
    }
    public void insertLast(int num,int []arr)
    {
        int i=0;while(arr[i]!=-1){i++;}
        arr[i]=num;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_response, menu);
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
