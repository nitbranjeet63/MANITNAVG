package com.mycalc.ranjeet.manitnavg;

import android.app.Activity;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DecimalFormat;


public class MapView extends Activity {
    private TouchImageView image;
    private DecimalFormat df;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        df = new DecimalFormat("#.##");
        image = (TouchImageView) findViewById(R.id.img);
        image.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {

            @Override
            public void onMove() {
                PointF point = image.getScrollPosition();
                RectF rect = image.getZoomedRect();
                float currentZoom = image.getCurrentZoom();
                boolean isZoomed = image.isZoomed();
				/*scrollPositionTextView.setText("x: " + df.format(point.x) + " y: " + df.format(point.y));
				zoomedRectTextView.setText("left: " + df.format(rect.left) + " top: " + df.format(rect.top)+ "\nright: " + df.format(rect.right) + " bottom: " + df.format(rect.bottom));
				currentZoomTextView.setText("getCurrentZoom(): " + currentZoom + " isZoomed(): " + isZoomed);*/
            }
        });
    }
}
