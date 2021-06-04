package com.hizkeel.retrofit4;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {



    @FormUrlEncoded // annotation used in POST type requests
    @POST("/v2/android/agent.php")
        // API's endpoints
    Call<LoginResponse> registration(@Field("command") String agent_login, @Field("username") String email, @Field("password") String password);


    @POST("/v2/android/agent.php")
    Call<LoginResponse> login(  @Body User user);

    // In registration method @Field used to set the keys and String data type is representing its a string type value and callback is used to get the response from api and it will set it in our POJO class
}
