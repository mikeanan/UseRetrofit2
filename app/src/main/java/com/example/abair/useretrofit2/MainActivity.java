package com.example.abair.useretrofit2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);

        Call<List<Repo>> repos = service.listRepos("octocat");

        //非同步呼叫
        repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                List<Repo> result = response.body();
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}

interface GitHubService {
    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);
}

class Repo {
    int id;
    String name;
}