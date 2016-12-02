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
    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

    @GET("demo/read/latest/{nRec}/")
    Call<List<demoData>> readLatest(@Path("nRec") int nRec);

    @GET("ws/Data/REWXQA/?$orderby=SiteName&$skip=0&$top=1&format=json")
    Call<List<Repo>> readOpenData();

    @GET("api/readData.php")
    Call<List<Repo>> read();

    @GET("api/api_delete.php")
    Call<ResponseBody> delete(@Query("cID") String cID);

    @FormUrlEncoded
    @POST("api/api_delete_post.php")
    Call<ResponseBody> deleteByPost(@Field("cID") String cID);

//    @POST("api/api_add_post.php")
//    Call<ResponseBody> add(@Body Repo repo);
//
//    @POST("api/api_add_post.php")
//    Call<ResponseBody> addByPlainText(@Body RequestBody body);


    @GET("api/api_add_get.php")
    Call<ResponseBody> addByGet( @Field("cName") String cName,
                                 @Field("cSex") String cSex,
                                 @Field("cBirthday") String cBirthday,
                                 @Field("cEmail") String cEmail,
                                 @Field("cPhone") String cPhone,
                                 @Field("cAddr") String cAddr);

    @FormUrlEncoded
    @POST("api/api_add_post.php")
    Call<ResponseBody> addByFormPost(@Query("cName") String cName,
                                     @Query("cSex") String cSex,
                                     @Query("cBirthday") String cBirthday,
                                     @Query("cEmail") String cEmail,
                                     @Query("cPhone") String cPhone,
                                     @Query("cAddr") String cAddr);

    @GET("api/api_update_get.php")
    Call<ResponseBody> updateByGet( @Query("cID") int cID,
                                    @Query("cName") String cName,
                                    @Query("cSex") String cSex,
                                    @Query("cBirthday") String cBirthday,
                                    @Query("cEmail") String cEmail,
                                    @Query("cPhone") String cPhone,
                                    @Query("cAddr") String cAddr);


    @FormUrlEncoded
    @POST("api/api_update_post.php")
    Call<ResponseBody> updateByFormPost( @Field("cID") int cID,
                                         @Field("cName") String cName,
                                         @Field("cSex") String cSex,
                                         @Field("cBirthday") String cBirthday,
                                         @Field("cEmail") String cEmail,
                                         @Field("cPhone") String cPhone,
                                         @Field("cAddr") String cAddr);

}