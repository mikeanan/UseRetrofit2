package com.example.abair.useretrofit2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private XYPlot plot;//拿來放統計圖表實體用的變數

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plot = (XYPlot) findViewById(R.id.plot);//取得統計圖表實體

        //宣告套件使用的資料，套件使用的是 Java 的 Number
        Number[] series1Numbers = {1, 2, 3, 2, 1, 3, 5, 3, 1, 4, 8};//項目數量會有影響，目前是 11 個

        //要將資料轉換成套件需要的格式
        XYSeries series1 = new SimpleXYSeries(  Arrays.asList(series1Numbers),//原來的資料要轉成 List
                                                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,//說明傳入的資料格式是只有 Y 的值
                                                "Series1");//設定資料要使用的名稱

        LineAndPointFormatter series1Format = new LineAndPointFormatter();//設定圖表顯示樣貌的格式，目前用預設值試試

        plot.addSeries(series1,series1Format);//將資料及顯示格式加入圖表
    }
}
