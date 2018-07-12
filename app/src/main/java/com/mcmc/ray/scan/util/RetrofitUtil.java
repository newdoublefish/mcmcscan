package com.mcmc.ray.scan.util;

import com.mcmc.ray.scan.application.Constants;
import com.mcmc.ray.scan.application.MyApplication;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    /**
     * 服务器地址
     */
    //private static final String API_HOST ="你的BaseUrl";
    private RetrofitUtil() {

    }
    public static Retrofit getRetrofit() {
        return Instanace.retrofit;
    }
    //静态内部类,保证单例并在调用getRetrofit方法的时候才去创建.
    private static class Instanace {
        private static  Retrofit retrofit = getInstanace();
        private static Retrofit getInstanace() {
            retrofit = new Retrofit.Builder()
                    .client(MyApplication.defaultOkHttpClient())
                    .baseUrl(Constants.DEMO_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit;
        }
    }
}
