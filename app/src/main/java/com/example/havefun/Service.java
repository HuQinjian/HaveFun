package com.example.havefun;

import java.util.Map;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @Project: HaveFun
 * @Author: hqj
 * @Date: 2023/3/13 15:51
 * @Introduce:
 **/
public interface Service {

    @GET("api/activity")
    Flowable<boreBean> getActivity(@QueryMap Map<String,Object> queryMap);

    @FormUrlEncoded
    @POST("translate")
    Flowable<TranslateBean> getTranslateData(@Field("doctype") String doctype,@Field("type") String type,@Field("i") String i);
}
