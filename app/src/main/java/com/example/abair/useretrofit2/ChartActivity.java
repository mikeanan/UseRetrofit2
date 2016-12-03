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
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;

import java.text.DecimalFormat;
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
    private ThreadRetrofit2 threadRetrofit2;

    private SensorManager sensorManager = null;
    private Sensor accSensor = null;
    private Sensor magSensor = null;
    private float[] mGravity = null;
    private float[] mGeomagnetic = null;

    private  int HISTORY_SIZE = 100;

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

        aprLevelsPlot.setDomainStepValue(3);
        aprLevelsPlot.setLinesPerRangeLabel(3);

        aprLevelsPlot.setDomainLabel("");
        aprLevelsPlot.getDomainTitle().pack();
        aprLevelsPlot.setRangeLabel("角度");
        aprLevelsPlot.getRangeTitle().pack();
        aprLevelsPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).setFormat(new DecimalFormat("#"));
        aprLevelsPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).getPaint().setColor(Color.TRANSPARENT);

        aprLevelsPlot.setRangeBoundaries(-180, 359, BoundaryMode.FIXED);
        aprLevelsPlot.setDomainBoundaries(-1, 1, BoundaryMode.FIXED);

        BarRenderer barRenderer = aprLevelsPlot.getRenderer(BarRenderer.class);
        if(barRenderer != null) {
            barRenderer.setBarWidth(100);
            barRenderer.setStyle(BarRenderer.Style.SIDE_BY_SIDE);
        }

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if (accSensor == null || magSensor == null) {
            sensorManager.unregisterListener(this);
            finish();
        }

        sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magSensor, SensorManager.SENSOR_DELAY_UI);


        aprHistoryPlot = (XYPlot) findViewById(R.id.aprHistoryPlot);

        aHtSeries = new SimpleXYSeries("A");
        aHtSeries.useImplicitXVals();
        pHtSeries = new SimpleXYSeries("P");
        pHtSeries.useImplicitXVals();
        rHtSeries = new SimpleXYSeries("R");
        rHtSeries.useImplicitXVals();

        for(int i = 0; i< HISTORY_SIZE; ++i){
            aHtSeries.addLast(null, 0);
            pHtSeries.addLast(null, 0);
            rHtSeries.addLast(null, 0);
        }

        aprHistoryPlot.setRangeBoundaries(-180, 359, BoundaryMode.FIXED);
        aprHistoryPlot.setDomainBoundaries(0, HISTORY_SIZE, BoundaryMode.FIXED);

        aprHistoryPlot.addSeries(aHtSeries, new LineAndPointFormatter(Color.rgb(0, 0, 200), null, null, null));
        aprHistoryPlot.addSeries(pHtSeries, new LineAndPointFormatter(Color.rgb(0, 200, 0), null, null, null));
        aprHistoryPlot.addSeries(rHtSeries, new LineAndPointFormatter(Color.rgb(200, 0, 0), null, null, null));

        aprHistoryPlot.setDomainStepValue(HISTORY_SIZE/100);
        aprHistoryPlot.setLinesPerRangeLabel(3);
        aprHistoryPlot.setDomainLabel("取樣");
        aprHistoryPlot.getDomainTitle().pack();
        aprHistoryPlot.setRangeLabel("角度");
        aprHistoryPlot.getRangeTitle().pack();
        aprHistoryPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).setFormat(new DecimalFormat("#"));
        aprHistoryPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new DecimalFormat("#"));

        redrawer = new Redrawer(Arrays.asList(new Plot[]{aprLevelsPlot, aprHistoryPlot}), 3, false);
        threadRetrofit2 = new ThreadRetrofit2(this, 10, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        redrawer.start();
        threadRetrofit2.start();
    }

    @Override
    protected void onPause() {
        threadRetrofit2.pause();
        redrawer.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        threadRetrofit2.finish();
        redrawer.finish();
        super.onDestroy();
    }

    @Override
    public synchronized void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = sensorEvent.values;

        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = sensorEvent.values;

        if (mGravity != null && mGeomagnetic != null) {
//            aLvSeries.setModel( Arrays.asList( new Number[]{mGeomagnetic[0]}),
//                                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY);
//            pLvSeries.setModel( Arrays.asList( new Number[]{mGravity[0]}),
//                                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY);
//            rLvSeries.setModel( Arrays.asList( new Number[]{mGravity[1]}),
//                                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY);

//            if(aHtSeries.size() > HISTORY_SIZE - 1){
//                aHtSeries.removeFirst();
//                pHtSeries.removeFirst();
//                rHtSeries.removeFirst();
//            }
//
//            aHtSeries.addLast(null, mGeomagnetic[0]);
//            pHtSeries.addLast(null, mGravity[0]);
//            rHtSeries.addLast(null, mGravity[1]);

            System.out.print(mGeomagnetic[0]);
        }
    }

    public void setEmulatedData(int a, int p, int r){
        aLvSeries.setModel( Arrays.asList( new Number[]{a}),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY);
        pLvSeries.setModel( Arrays.asList( new Number[]{p}),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY);
        rLvSeries.setModel( Arrays.asList( new Number[]{r}),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY);

        if(aHtSeries.size() > HISTORY_SIZE - 1){
            aHtSeries.removeFirst();
            pHtSeries.removeFirst();
            rHtSeries.removeFirst();
        }

        aHtSeries.addLast(null, a);
        pHtSeries.addLast(null, p);
        rHtSeries.addLast(null, r);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
