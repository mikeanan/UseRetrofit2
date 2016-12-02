package com.example.abair.useretrofit2;

import android.content.Context;
import android.util.Log;

public class ThreadRetrofit2 implements Runnable {

    private static final int ONE_SECOND_MS = 1000;
    private static final String TAG = "ThreadRetrofit2";
    private long sleepTime;
    private boolean keepRunning;
    private boolean keepAlive;
    private Thread thread;

    public ThreadRetrofit2(Context context, float maxRefreshRate, boolean startImmediately) {
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
                    //要執行的程式碼，請放在這
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
