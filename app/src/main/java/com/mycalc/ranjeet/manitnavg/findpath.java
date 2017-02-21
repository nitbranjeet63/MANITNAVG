package com.mycalc.ranjeet.manitnavg;

/**
 * Created by ranjeet on 17-May-15.
 */

import android.util.Log;

public class findpath {

    public static final int V=22;
    public static final int INT_MIN=-2147483648 ;
    public static final int INT_MAX=2147483647;
    int junc[];
    int counter=-1;
    StringBuffer str = new StringBuffer();
    private int minDistance(int dist[], boolean sptSet[])
    {
        int min = INT_MAX, min_index=0;

        for (int v = 0; v < V; v++)
            if (sptSet[v] == false && dist[v] <= min)
            { min = dist[v]; min_index = v;}

        return min_index;
    }

    private void printSolution(int dist[], int n)
    {
        str.append("RouteNo   Distance from Source Route\n");
        for (int i = 0; i < V; i++)
            str.append("   "+i+" \t\t\t\t "+dist[i]+"\n");
    }

    void printPath(int dest, int prev[])
    {
        // int counter=-1;
        if (prev[dest] != -1){
            printPath(prev[dest], prev);
            str.append(" - ");
        }
        junc[++counter]=dest;
        str.append(dest);
    }


    void getRoute(int junction[][], int src,int dest)
    {
        int dist[]=new int[V];
        String s=Integer.toString(src);
        String s1=Integer.toString(dest);
        Log.v("hjklmn",s+" "+s1);
        int prev[]=new int[V];
        boolean sptSet[]=new boolean[V];
        junc = new int[V];
        for (int i = 0; i < V; i++)
        {dist[i] = INT_MAX; sptSet[i] = false;prev[i]=-1; junc[i]=-1;}
        dist[src] = 0;
        for (int count = 0; count < V-1; count++)
        {
            int u = minDistance(dist, sptSet);
            sptSet[u] = true;

            for (int v = 0; v < V; v++)
                if (!sptSet[v] && junction[u][v]!=0 && dist[u] != INT_MAX
                        && dist[u]+junction[u][v] < dist[v])
                { dist[v] = dist[u] + junction[u][v];prev[v]=u;}
        }
        printSolution(dist, dest);
        printPath(dest,prev);
        str.append("\n Total distance : "+dist[dest]);
    }


}