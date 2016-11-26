package com.example.abair.useretrofit2;

import android.app.Application;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by abair on 2016/11/18.
 */

public class MyApp extends Application {
    List<Repo> result;
    GitHubService service;
    Call<List<Repo>> repos;
    Call<ResponseBody> delete;
    Call<ResponseBody> addByFormPost;
}
