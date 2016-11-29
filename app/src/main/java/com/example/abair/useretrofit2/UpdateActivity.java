package com.example.abair.useretrofit2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();//取得點選項目的位置
        int position;
        position = (int) intent.getExtras().getSerializable("position");

        MyApp myApp = (MyApp) getApplicationContext();//取得要顯示在更新頁面的資料
        Repo repo = myApp.result.get(position);
        myApp.StudentInformation.cID = repo.cID;//需要設定 cID 否則無法執行修改，因為預設值 = 0

        EditText cName = (EditText) findViewById(R.id.textViewDataName);
        cName.setText(repo.cName);
        EditText cSex = (EditText) findViewById(R.id.textViewDataGender);
        cSex.setText(repo.cSex);
        EditText cBirth = (EditText) findViewById(R.id.textViewDataBirth);
        cBirth.setText(repo.cBirthday);
        EditText cEmail = (EditText) findViewById(R.id.textViewDataEmail);
        cEmail.setText(repo.cEmail);
        EditText cPhone = (EditText) findViewById(R.id.textViewDataPhone);
        cPhone.setText(repo.cPhone);
        EditText cAddr = (EditText) findViewById(R.id.textViewDataAddress);
        cAddr.setText(repo.cAddr);
    }

    public void cancel(View view)
    {
        Intent result = getIntent();
        setResult(Activity.RESULT_CANCELED, result);
        finish();
    }

    public void update(View view)
    {
        MyApp myApp = (MyApp) getApplicationContext();//使用 全域變數 來傳要修改的資料    getText() 回傳的是 Editable 不是 String
        myApp.StudentInformation.cName = ((EditText)findViewById(R.id.textViewDataName)).getText().toString();
        myApp.StudentInformation.cSex = ((EditText)findViewById(R.id.textViewDataGender)).getText().toString();
        myApp.StudentInformation.cBirthday = ((EditText)findViewById(R.id.textViewDataBirth)).getText().toString();
        myApp.StudentInformation.cEmail = ((EditText)findViewById(R.id.textViewDataEmail)).getText().toString();
        myApp.StudentInformation.cPhone = ((EditText)findViewById(R.id.textViewDataPhone)).getText().toString();
        myApp.StudentInformation.cAddr = ((EditText)findViewById(R.id.textViewDataAddress)).getText().toString();

        Intent result = getIntent();
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}
