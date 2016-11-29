package com.example.abair.useretrofit2;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
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

//    @POST("api/api_add_post.php")
//    Call<ResponseBody> add(@Body Repo repo);
//
//    @POST("api/api_add_post.php")
//    Call<ResponseBody> addByPlainText(@Body RequestBody body);

    @FormUrlEncoded
    @POST("api/api_add_post.php")
    Call<ResponseBody> addByFormPost(@Field("cName") String cName,
                                     @Field("cSex") String cSex,
                                     @Field("cBirthday") String cBirthday,
                                     @Field("cEmail") String cEmail,
                                     @Field("cPhone") String cPhone,
                                     @Field("cAddr") String cAddr);

}