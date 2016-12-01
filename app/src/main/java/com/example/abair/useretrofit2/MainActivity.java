package com.example.abair.useretrofit2;

import android.graphics.DashPathEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.CatmullRomInterpolator;
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

        LineAndPointFormatter series1Format = new LineAndPointFormatter(this,
                                                                        R.xml.androidplot_format_series_1);//改用 xml 設定顯示格

        //取得設定好的格式的畫筆，然後使用 android 內建效果設定虛線效
        series1Format.getLinePaint().setPathEffect( new DashPathEffect(new float[]{PixelUtils.dpToPix(10),//套件內建工具，確保不同解析度，看起來顯示效果相同
                                                                                    PixelUtils.dpToPix(5)},
                                                                        0));

        //使用套件內建的方式，讓圖形看起來比較平滑   為圖形資料產生插入值
        series1Format.setInterpolationParams( new CatmullRomInterpolator.Params(10,//每個線段插入的點數，可調整
                                                                                CatmullRomInterpolator.Type.Centripetal));

        plot.addSeries(series1,series1Format);//將資料及顯示格式加入圖表
    }
}
