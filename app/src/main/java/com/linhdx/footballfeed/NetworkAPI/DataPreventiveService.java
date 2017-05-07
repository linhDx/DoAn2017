package com.linhdx.footballfeed.NetworkAPI;

import com.linhdx.footballfeed.AppConstant;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataPreventive.DataWrapper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by shine on 26/04/2017.
 */

public interface DataPreventiveService {
    OkHttpClient httpClient = new OkHttpClient.Builder()
            .build();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(AppConstant.PRE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build();

    //http://soccer.sportsopendata.net/v1/leagues/{league_slug}/seasons/16-17/standings
    // lay bang xep hang mua nay
    @GET(AppConstant.PRE_LEAGUE_TABLE)
    Call<DataWrapper> getData(@Path("league_slug") String league_slug);
}
