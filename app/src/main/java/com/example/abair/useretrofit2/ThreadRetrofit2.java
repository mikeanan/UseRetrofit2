package com.example.abair.useretrofit2;

import android.content.Context;
import android.util.Log;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThreadRetrofit2 implements Runnable {

    private static final int ONE_SECOND_MS = 1000;
    private static final String TAG = "ThreadRetrofit2";
    private long sleepTime;
    private boolean keepRunning;
    private boolean keepAlive;
    private Thread thread;
    private MyApp myApp;
    private Context context;
    private Random random;

    public ThreadRetrofit2(Context context, float maxRefreshRate, boolean startImmediately) {
        this.context = context;
        random = new Random();
        this.myApp = (MyApp) context.getApplicationContext();

        setMaxRefreshRate(maxRefreshRate);
        thread = new Thread(this);
        thread.start();
        if(startImmediately) {
            start();
        }
    }

    public synchronized void pause() {
        keepRunning = false;
        notify();
        Log.d(TAG, "ThreadRetrofit2 paused.");
    }

    public synchronized void start() {
        keepRunning = true;
        notify();
        Log.d(TAG, "ThreadRetrofit2 started.");
    }

    public synchronized void finish() {
        keepRunning = false;
        keepAlive = false;
        notify();
    }

    @Override
    public void run() {
        keepAlive = true;
        try {
            while(keepAlive) {
                if(keepRunning) {
//                    Call<List<demoData>> readLatestOneClone = myApp.readLatestOne.clone();
//                    readLatestOneClone.enqueue(new Callback<List<demoData>>() {
//                        @Override
//                        public void onResponse(Call<List<demoData>> call, Response<List<demoData>> response) {
//                            myApp.resultDemoData = response.body();
//
//                            if(myApp.resultDemoData == null){
//                                Log.d(TAG,"myApp.resultDemoData == null");
//                                return;
//                            }
//
//                            Iterator it = myApp.resultDemoData.iterator();
//                            while(it.hasNext()) {
//                                Log.d(TAG,((demoData) it.next()).SENSOR);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<demoData>> call, Throwable t)
//                        {
//                            t.printStackTrace();
//                        }
//                    });

                    ((ChartActivity)context).setEmulatedData(   random.nextInt(50)+280,
                                                                random.nextInt(60)-30,
                                                                random.nextInt(30)-150);


//                    Call<List<Repo>> clone = myApp.readOpenData.clone();
//                    clone.enqueue(new Callback<List<Repo>>() {
//                        @Override
//                        public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
//                            myApp.result = response.body();
//
//                            if(myApp.result == null){
//                                Log.d(TAG,"myApp.result == null");
//                                return;
//                            }
//
//                            Iterator it = myApp.result.iterator();
//                            while(it.hasNext()) {
//                                Log.d(TAG,((Repo) it.next()).name);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<Repo>> call, Throwable t)
//                        {
//                            t.printStackTrace();
//                        }
//                    });

                    synchronized (this) {
                        wait(sleepTime);
                    }
                } else {
                    // sleep until notified
                    synchronized (this) {
                        wait();
                    }
                }
            }
        } catch(InterruptedException e) {

        } finally {
            Log.d(TAG, "ThreadRetrofit2 thread exited.");
        }
    }

    public void setMaxRefreshRate(float refreshRate) {
        sleepTime = (long)(ONE_SECOND_MS / refreshRate);
        Log.d(TAG, "Set ThreadRetrofit2 refresh rate to " +
                refreshRate + "( " + sleepTime + " ms)");
    }
}
