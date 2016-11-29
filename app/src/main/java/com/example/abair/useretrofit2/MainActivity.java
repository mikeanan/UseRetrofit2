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
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//需要 implement custom Adapter 的 button listener 的介面
public class MainActivity extends AppCompatActivity implements ItemAdapter.customButtonListener {

    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String[] data = { "Test1", "Test2", "Test3" };
//        int layoutID = android.R.layout.simple_list_item_1;
        int layoutID = R.layout.singleitem;
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, layoutID, data);
//        adapter = new ArrayAdapter<String>(this, layoutID);
        adapter = new ItemAdapter(this,layoutID);
        adapter.setCustomButtonListner(this);//將主頁面 context 傳入，做為 custom listerner 介面的多型來使用
        ListView item_list = (ListView) findViewById(R.id.item_list);
        item_list.setAdapter(adapter);

        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Toast.makeText(MainActivity.this, adapter.getItem(position), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                intent.putExtra("position", position);
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
//        myApp.repos = myApp.service.listRepos("octocat");
        myApp.repos = myApp.service.listRepos();


        //非同步呼叫
        myApp.repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                MyApp myApp = (MyApp) getApplicationContext();
                myApp.result = response.body();

                Iterator it = myApp.result.iterator();
                while(it.hasNext()) {
//                    adapter.add(((Repo) it.next()).name);
                    adapter.add(((Repo) it.next()).cName);
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t)
            {
                t.printStackTrace();
            }
        });

//        Repo repo = new Repo();//測試成功的修改的程式碼
//        repo.cID = 20;
//        repo.cName = "mike";
//        repo.cAddr = "地球";
//        repo.cBirthday = "1974-04-03";
//        repo.cEmail = "chennanbang@gamil.com";
//        repo.cPhone = "0933596597";
//        repo.cSex = "m";
//
//        myApp.updateByGet = myApp.service.updateByGet(  repo.cID,
//                                                        repo.cName,
//                                                        repo.cSex,
//                                                        repo.cBirthday,
//                                                        repo.cEmail,
//                                                        repo.cPhone,
//                                                        repo.cAddr);
//
//        myApp.updateByGet.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                System.out.println("Update by Get OK" + response.toString());
//                updateListView();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
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
                final int position = (int) data.getExtras().getSerializable("position");

                MyApp myApp = (MyApp) getApplicationContext();
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
            repo.cName = (String) data.getExtras().getSerializable("cName");//從 Intent 得到要新增的資料
            repo.cAddr = (String) data.getExtras().getSerializable("cAddr");
            repo.cBirthday = (String) data.getExtras().getSerializable("cBirthday");
            repo.cEmail = (String) data.getExtras().getSerializable("cEmail");
            repo.cPhone = (String) data.getExtras().getSerializable("cPhone");
            repo.cSex = (String) data.getExtras().getSerializable("cSex");
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

            myApp.addByFormPost = myApp.service.addByFormPost(
//                    repo.cName,
//                    repo.cSex,
//                    repo.cBirthday,
//                    repo.cEmail,
//                    repo.cPhone,
//                    repo.cAddr);
                    myApp.StudentInformation.cName,//從 全域變數 得到要新增的資料
                    myApp.StudentInformation.cSex,
                    myApp.StudentInformation.cBirthday,
                    myApp.StudentInformation.cEmail,
                    myApp.StudentInformation.cPhone,
                    myApp.StudentInformation.cAddr);

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

        if( requestCode == 2 && resultCode == Activity.RESULT_OK)//更新頁面回傳
        {
            MyApp myApp = (MyApp) getApplicationContext();//執行之前測試成功的修改的程式碼
            myApp.updateByGet = myApp.service.updateByGet(  myApp.StudentInformation.cID,
                                                            myApp.StudentInformation.cName,
                                                            myApp.StudentInformation.cSex,
                                                            myApp.StudentInformation.cBirthday,
                                                            myApp.StudentInformation.cEmail,
                                                            myApp.StudentInformation.cPhone,
                                                            myApp.StudentInformation.cAddr);

            myApp.updateByGet.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    System.out.println("Update by Get OK" + response.toString());
                    updateListView();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }

    public void add(View view) {
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivityForResult(intent, 1);
    }

    public void update(View view) {//原來設定要來執行 update 按鈕
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        startActivityForResult(intent, 2);
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


    @Override
    public void onButtonClickListner(int position, int id) {//interface 在這實現，用做判斷所按的項目及按鈕的地方
        String string;
        if( id == R.id.itemButtonDelete) {
            string = "Delete";
            Intent intent = new Intent(MainActivity.this, DeleteActivity.class);//執行之前的 delete 的幾行程式碼
            intent.putExtra("position", position);
            startActivityForResult(intent, 0);
        }
        else {
            string = "Update";
            Intent intent = new Intent(MainActivity.this, UpdateActivity.class);//執行 update 的程式碼
            intent.putExtra("position", position);
            startActivityForResult(intent, 2);
        }
        Toast.makeText(MainActivity.this, "Button click position:" + String.valueOf(position)+ string,
                Toast.LENGTH_SHORT).show();
    }
}