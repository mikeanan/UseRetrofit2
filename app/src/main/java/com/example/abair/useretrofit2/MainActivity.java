package com.example.abair.useretrofit2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        String[] data = { "Test1", "Test2", "Test3" };
        int layoutID = android.R.layout.simple_list_item_1;
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, layoutID, data);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, layoutID);
        ListView item_list = (ListView) findViewById(R.id.item_list);
        item_list.setAdapter(adapter);

        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Toast.makeText(MainActivity.this, adapter.getItem(position), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
//                intent.putExtra("test", "hello");// key-value
                intent.putExtra("cName", adapter.getItem(position));
//                startActivity(intent);
                startActivityForResult(intent,0);
            }
        };
        item_list.setOnItemClickListener(onItemClickListener);

        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
                .baseUrl("http://192.168.137.53:8081/PHP_Project_forXAMPP/11-14_projectForAll/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
//        Call<List<Repo>> repos = service.listRepos("octocat");
        Call<List<Repo>> repos = service.listRepos();


        //非同步呼叫
        repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                List<Repo> result = response.body();
//                System.out.println(result.get(0).name);
//                System.out.println(result.get(1).name);

                Iterator it = result.iterator();
                while(it.hasNext())
//                    adapter.add(((Repo) it.next()).name);
                    adapter.add(((Repo) it.next()).cName);
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

        if(resultCode == Activity.RESULT_CANCELED)
        {
            System.out.println("Cancel");
        }


        if(resultCode == Activity.RESULT_OK)
        {
            System.out.println("Delete");
        }
    }
}