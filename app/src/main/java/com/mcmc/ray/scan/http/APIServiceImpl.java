package com.mcmc.ray.scan.http;

import com.mcmc.ray.scan.util.RetrofitUtil;

public class APIServiceImpl {
    private APIServiceImpl() {

    }
    public static APIService getInstance() {
        return createAPIService.apiService;
    }

    /**
     * Retrofit生成接口对象.
     */
    private static class createAPIService {
        //Retrofit会根据传入的接口类.生成实例对象.
        private static final APIService apiService = RetrofitUtil.getRetrofit().create(APIService.class);
    }
}
