package com.example.abair.useretrofit2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    public void cancel(View view)
    {
        Intent result = getIntent();
        setResult(Activity.RESULT_CANCELED, result);
        finish();
    }

    public void add(View view)
    {
        Intent result = getIntent();
        setResult(Activity.RESULT_OK, result);
        finish();
    }

}
