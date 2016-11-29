package com.example.abair.useretrofit2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        MyApp myApp = (MyApp) getApplicationContext();//使用 全域變數 來傳要新增的資料    getText() 回傳的是 Editable 不是 String
        myApp.StudentInformation.cName = ((EditText)findViewById(R.id.textViewDataName)).getText().toString();
        myApp.StudentInformation.cSex = ((EditText)findViewById(R.id.textViewDataGender)).getText().toString();
        myApp.StudentInformation.cBirthday = ((EditText)findViewById(R.id.textViewDataBirth)).getText().toString();
        myApp.StudentInformation.cEmail = ((EditText)findViewById(R.id.textViewDataEmail)).getText().toString();
        myApp.StudentInformation.cPhone = ((EditText)findViewById(R.id.textViewDataPhone)).getText().toString();
        myApp.StudentInformation.cAddr = ((EditText)findViewById(R.id.textViewDataAddress)).getText().toString();

        Intent result = getIntent();//使用 Intent 來傳要新增的資料
        result.putExtra("cName", myApp.StudentInformation.cName);
        result.putExtra("cSex", myApp.StudentInformation.cSex);
        result.putExtra("cBirthday", myApp.StudentInformation.cBirthday);
        result.putExtra("cEmail", myApp.StudentInformation.cEmail);
        result.putExtra("cPhone", myApp.StudentInformation.cPhone);
        result.putExtra("cAddr", myApp.StudentInformation.cAddr);

        setResult(Activity.RESULT_OK, result);
        finish();
    }

}
