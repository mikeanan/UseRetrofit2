package com.example.abair.useretrofit2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;

public class DeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Intent intent = getIntent();
        int position;
        position = (int) intent.getExtras().getSerializable("position");

        MyApp myApp = (MyApp) getApplicationContext();
        Iterator it = myApp.result.iterator();
        int counter = 0;

        while(it.hasNext()){
            Repo repo = (Repo)it.next();
            if(counter == position){
                TextView textViewcName = (TextView) findViewById(R.id.textViewDataName);
                textViewcName.setText(repo.cName);
                TextView textViewcSex = (TextView) findViewById(R.id.textViewDataGender);
                textViewcSex.setText(repo.cSex);
                TextView textViewcBirth = (TextView) findViewById(R.id.textViewDataBirth);
                textViewcBirth.setText(repo.cBirthday);
                TextView textViewcEmail = (TextView) findViewById(R.id.textViewDataEmail);
                textViewcEmail.setText(repo.cEmail);
                TextView textViewcPhone = (TextView) findViewById(R.id.textViewDataPhone);
                textViewcPhone.setText(repo.cPhone);
                TextView textViewcAddr = (TextView) findViewById(R.id.textViewDataAddress);
                textViewcAddr.setText(repo.cAddr);
                break;
            }
            ++counter;
        }

        Repo repo = myApp.result.get(position);
        TextView textViewcName = (TextView) findViewById(R.id.textViewDataName);
        textViewcName.setText(repo.cName);
        TextView textViewcSex = (TextView) findViewById(R.id.textViewDataGender);
        textViewcSex.setText(repo.cSex);
        TextView textViewcBirth = (TextView) findViewById(R.id.textViewDataBirth);
        textViewcBirth.setText(repo.cBirthday);
        TextView textViewcEmail = (TextView) findViewById(R.id.textViewDataEmail);
        textViewcEmail.setText(repo.cEmail);
        TextView textViewcPhone = (TextView) findViewById(R.id.textViewDataPhone);
        textViewcPhone.setText(repo.cPhone);
        TextView textViewcAddr = (TextView) findViewById(R.id.textViewDataAddress);
        textViewcAddr.setText(repo.cAddr);

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
