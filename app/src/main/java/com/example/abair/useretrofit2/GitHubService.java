package com.example.abair.useretrofit2;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by abair on 2016/11/18.
 */
public interface GitHubService {
//    @GET("users/{user}/repos")
//    Call<List<Repo>> listRepos(@Path("user") String user);

    @GET("api/readData.php")
    Call<List<Repo>> listRepos();

    @GET("api/api_delete.php")
    Call<ResponseBody> delete(@Query("cID") String cID);

}