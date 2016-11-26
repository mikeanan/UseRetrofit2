package com.example.abair.useretrofit2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Iterator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String[] data = { "Test1", "Test2", "Test3" };
        int layoutID = android.R.layout.simple_list_item_1;
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, layoutID, data);
        adapter = new ArrayAdapter<String>(this, layoutID);
        ListView item_list = (ListView) findViewById(R.id.item_list);
        item_list.setAdapter(adapter);

        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Toast.makeText(MainActivity.this, adapter.getItem(position), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                intent.putExtra("position", position);
//                startActivity(intent);
                startActivityForResult(intent, 0);
            }
        };
        item_list.setOnItemClickListener(onItemClickListener);

        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
                .baseUrl("http://192.168.137.53:8081/PHP_Project_forXAMPP/11-14_projectForAll/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApp myApp = (MyApp) getApplicationContext();
        myApp.service = retrofit.create(GitHubService.class);

//        Call<List<Repo>> repos = service.listRepos("octocat");
        Call<List<Repo>> repos = service.listRepos();
        myApp.repos = myApp.service.listRepos();


        //非同步呼叫
        myApp.repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                MyApp myApp = (MyApp) getApplicationContext();
                myApp.result = response.body();
//                System.out.println(result.get(0).name);
//                System.out.println(result.get(1).name);

                Iterator it = myApp.result.iterator();
                while(it.hasNext()) {
//                    adapter.add(((Repo) it.next()).name);
                    adapter.add(((Repo) it.next()).cName);
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("onActivityResult");

        if(requestCode == 0) {
            if(resultCode == Activity.RESULT_CANCELED)
            {
                System.out.println("Cancel");
            }


            if(resultCode == Activity.RESULT_OK)
            {
                System.out.println("Delete");
//            myApp.repos = service.listRepos();

//            Intent intent = getIntent();
                final int position = (int) data.getExtras().getSerializable("position");

                MyApp myApp = (MyApp) getApplicationContext();
//            String tmp = String.valueOf(myApp.result.get(position).cID);
                myApp.delete = myApp.service.delete(String.valueOf(myApp.result.get(position).cID));
                myApp.delete.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        System.out.println("delete OK");
                        updateListView();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("delete fail...");
                    }
                });
            }
        }

        if( requestCode == 1 && resultCode == Activity.RESULT_OK)
        {
            MyApp myApp = (MyApp) getApplicationContext();
            Repo repo = new Repo();
            repo.cName = "mike";
            repo.cAddr = "地球";
            repo.cBirthday = "1974-04-03";
            repo.cEmail = "chennanbang@gamil.com";
            repo.cPhone = "0933596597";
            repo.cSex = "male";
//            myApp.add = myApp.service.add(repo);
//            myApp.add.enqueue(new Callback<ResponseBody>() { // JSON Type
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    System.out.println("Add OK");
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    System.out.println("Add fail...");
//
//                }
//            });

//            String tmp = "'cName'='mike', 'cAddr'='', 'cBirthday'='', 'cEmail'='', 'cPhone'='', 'cSex'=''";
//
//            RequestBody body = RequestBody.create(MediaType.parse("text/plain"), tmp);
//            myApp.addByPlainText = myApp.service.addByPlainText(body);
//            myApp.addByPlainText.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    System.out.println("Add by plain text OK");
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    System.out.println("Add by plain text fail...");
//                }
//            });

            myApp.addByFormPost = myApp.service.addByFormPost(repo.cName,repo.cSex,repo.cBirthday,repo.cEmail,repo.cPhone,repo.cAddr);
            myApp.addByFormPost.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    System.out.println("Add by form post OK");
                    updateListView();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    System.out.println("Add by form post OK");
                }
            });
        }
    }

    public void add(View view) {
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivityForResult(intent, 1);
    }

    public void updateListView() {
        MyApp myApp = (MyApp) getApplicationContext();
        Call<List<Repo>> reposClone = myApp.repos.clone();
        reposClone.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                MyApp myApp = (MyApp) getApplicationContext();
                myApp.result = response.body();

                Iterator it = myApp.result.iterator();
                adapter.clear();
                while(it.hasNext()) {
                    adapter.add(((Repo) it.next()).cName);
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}