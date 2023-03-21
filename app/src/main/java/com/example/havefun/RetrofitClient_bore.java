package com.example.havefun;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Project: HaveFun
 * @Author: hqj
 * @Date: 2023/3/13 15:35
 * @Introduce: 活动的网络请求类
 **/
public class RetrofitClient_bore {
    private static volatile RetrofitClient_bore mInstance;
    private static final String BASE_URL = "https://www.boredapi.com/";
    private Retrofit retrofit;

    //Retrofit设置单例
    public static RetrofitClient_bore getInstance(){
        if (mInstance==null){
            synchronized (RetrofitClient_bore.class){
                if (mInstance==null){
                    mInstance = new RetrofitClient_bore();
                }
            }
        }
        return mInstance;
    }

    //设置连接网络
    private Retrofit getRetrofit(){
        if (retrofit==null){
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public <T> T getService(Class<T> cls){
        return getRetrofit().create(cls);
    }

}
