package com.example.abair.useretrofit2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Intent intent = getIntent();
        String cName = (String) intent.getExtras().getSerializable("cName");
        System.out.println(cName);

        TextView textViewcName = (TextView) findViewById(R.id.textViewDataName);
        textViewcName.setText(cName);

    }

    public void cancel(View view)
    {
        Intent result = getIntent();
        setResult(Activity.RESULT_CANCELED, result);
        finish();
    }

    public void delete(View view)
    {
        Intent result = getIntent();
        setResult(Activity.RESULT_OK, result);
        finish();
    }

}
