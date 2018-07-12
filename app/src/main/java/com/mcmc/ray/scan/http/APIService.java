package com.mcmc.ray.scan.http;

import com.mcmc.ray.scan.beans.UserBean;

import rx.Observable;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("login/login")
    Observable<BaseResponse<UserBean>> login(@Body RequestBody info);
}
