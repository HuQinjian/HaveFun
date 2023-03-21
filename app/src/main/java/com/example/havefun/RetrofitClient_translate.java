package com.example.havefun;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Project: HaveFun
 * @Author: hqj
 * @Date: 2023/3/14 11:11
 * @Introduce: 翻译接口请求类
 **/
public class RetrofitClient_translate {
    private static volatile RetrofitClient_translate mInstance;
    private final String BASE_URL = "http://fanyi.youdao.com/";
    private Retrofit retrofit;

    public static RetrofitClient_translate getmInstance(){
        if (mInstance==null){
            synchronized (RetrofitClient_translate.class){
                if (mInstance==null){
                    mInstance = new RetrofitClient_translate();
                }
            }
        }
        return mInstance;
    }

    private Retrofit getRetrofit(){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        retrofit = new Retrofit
                .Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        return retrofit;
    }

    public <T> T getService(Class<T> cls){
        return getRetrofit().create(cls);
    }
}
