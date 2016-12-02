package com.example.abair.useretrofit2;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.androidplot.Plot;
import com.androidplot.util.Redrawer;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;

import java.util.Arrays;

public class ChartActivity extends AppCompatActivity implements SensorEventListener {

    private XYPlot aprLevelsPlot = null;

    private SimpleXYSeries aLvSeries;
    private SimpleXYSeries pLvSeries;
    private SimpleXYSeries rLvSeries;
    
    private XYPlot aprHistoryPlot = null;

    private SimpleXYSeries aHtSeries;
    private SimpleXYSeries pHtSeries;
    private SimpleXYSeries rHtSeries;

    private Redrawer redrawer;
    private SensorManager sensorManager = null;
    private Sensor sensorOrientation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        aprLevelsPlot = (XYPlot) findViewById(R.id.aprLevelsPlot);

        aLvSeries = new SimpleXYSeries("A");
        pLvSeries = new SimpleXYSeries("P");
        rLvSeries = new SimpleXYSeries("R");

        aprLevelsPlot.addSeries(aLvSeries, new BarFormatter(Color.rgb(0, 0, 200), Color.rgb(0, 0, 0)));
        aprLevelsPlot.addSeries(pLvSeries, new BarFormatter(Color.rgb(0, 200, 0), Color.rgb(0, 0, 0)));
        aprLevelsPlot.addSeries(rLvSeries, new BarFormatter(Color.rgb(200, 0, 0), Color.rgb(0, 0, 0)));

        aprLevelsPlot.setRangeBoundaries(-180, 359, BoundaryMode.FIXED);
        aprLevelsPlot.setDomainBoundaries(-1, 1, BoundaryMode.FIXED);

        BarRenderer barRenderer = aprLevelsPlot.getRenderer(BarRenderer.class);
        if(barRenderer != null) {
            barRenderer.setBarWidth(100);
            barRenderer.setStyle(BarRenderer.Style.SIDE_BY_SIDE);
        }

        sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        for(Sensor sensor : sensorManager.getSensorList(Sensor.TYPE_ORIENTATION)) {
            if(sensor.getType() == Sensor.TYPE_ORIENTATION){
                sensorOrientation = sensor;
            }
        }

        if(sensorOrientation == null){
            sensorManager.unregisterListener(this);
            finish();
        }

        sensorManager.registerListener(this, sensorOrientation, SensorManager.SENSOR_DELAY_UI);

        aprHistoryPlot = (XYPlot) findViewById(R.id.aprHistoryPlot);

        aHtSeries = new SimpleXYSeries("A");
        aHtSeries.useImplicitXVals();
        pHtSeries = new SimpleXYSeries("P");
        pHtSeries.useImplicitXVals();
        rHtSeries = new SimpleXYSeries("R");
        rHtSeries.useImplicitXVals();

        aprHistoryPlot.addSeries(aHtSeries, new LineAndPointFormatter(Color.rgb(0, 0, 200), null, null, null));
        aprHistoryPlot.addSeries(pHtSeries, new LineAndPointFormatter(Color.rgb(0, 200, 0), null, null, null));
        aprHistoryPlot.addSeries(rHtSeries, new LineAndPointFormatter(Color.rgb(200, 0, 0), null, null, null));

        redrawer = new Redrawer(Arrays.asList(new Plot[]{aprLevelsPlot, aprHistoryPlot}), 3, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        redrawer.start();
    }

    @Override
    protected void onPause() {
        redrawer.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        redrawer.finish();
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        aLvSeries.setModel( Arrays.asList( new Number[]{sensorEvent.values[0]}),
                            SimpleXYSeries.ArrayFormat.Y_VALS_ONLY);
        pLvSeries.setModel( Arrays.asList( new Number[]{sensorEvent.values[1]}),
                            SimpleXYSeries.ArrayFormat.Y_VALS_ONLY);
        rLvSeries.setModel( Arrays.asList( new Number[]{sensorEvent.values[2]}),
                            SimpleXYSeries.ArrayFormat.Y_VALS_ONLY);

        aHtSeries.addFirst(null, sensorEvent.values[0]);
        pHtSeries.addFirst(null, sensorEvent.values[1]);
        rHtSeries.addFirst(null, sensorEvent.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
