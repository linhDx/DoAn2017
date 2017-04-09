package com.linhdx.footballfeed.Network;

import com.linhdx.footballfeed.AppConstant;
import com.linhdx.footballfeed.AppObjectNetWork.NextMatchNetWorkWrapper;

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
 * Created by shine on 08/04/2017.
 */

public interface DataService {
    OkHttpClient httpClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder ongoing = chain.request().newBuilder();
                    ongoing.addHeader("X-Auth-Token","6af33516dbdb4d598e587d4d4931d434");
                    return chain.proceed(ongoing.build());
                }
            })
            .build();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(AppConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build();
    //http://api.football-data.org/v1/competitions/426/fixtures?timeFrame=n1
    @GET(AppConstant.PL_N_MATCH)
    Call<NextMatchNetWorkWrapper> getNextMatchStatusesPL(@Path("id") int groupID);
}
