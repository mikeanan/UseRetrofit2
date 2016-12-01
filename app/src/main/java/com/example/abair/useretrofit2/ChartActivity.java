package com.example.abair.useretrofit2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.androidplot.util.Redrawer;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;

public class ChartActivity extends AppCompatActivity {

    private XYPlot aprLevelsPlot = null;

    private SimpleXYSeries aLvSeries;
    private SimpleXYSeries pLvSeries;
    private SimpleXYSeries rLvSeries;

    private Redrawer redrawer;

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

        BarRenderer barRenderer = aprLevelsPlot.getRenderer(BarRenderer.class);
        if(barRenderer != null) {
            barRenderer.setBarWidth(100);
            barRenderer.setStyle(BarRenderer.Style.SIDE_BY_SIDE);
        }

        redrawer = new Redrawer(aprLevelsPlot, 3, false);
    }
}
