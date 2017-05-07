package com.linhdx.footballfeed.NetworkAPI;

import com.linhdx.footballfeed.AppConstant;
import com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork.RssFeed_BDC;
import com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork.RssFeed_DanTri;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by shine on 18/04/2017.
 */

public interface RssService {
    OkHttpClient httpClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder ongoing = chain.request().newBuilder();
                    return chain.proceed(ongoing.build());
                }
            })
            .build();
    Retrofit retrofit_BDC = new Retrofit.Builder()
            .baseUrl(AppConstant.BASE_URL_BONGDACOM_RSS)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(httpClient)
            .build();
    Retrofit retrofit_dantri = new Retrofit.Builder()
            .baseUrl(AppConstant.BASE_URL_DANTRI_RSS)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(httpClient)
            .build();
    Retrofit retrofit_thethao247 = new Retrofit.Builder()
            .baseUrl(AppConstant.BASE_URL_THETHAO247_RSS)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(httpClient)
            .build();
    Retrofit retrofit_bongda24h = new Retrofit.Builder()
            .baseUrl(AppConstant.BASE_URL_BONGDA24H_RSS)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(httpClient)
            .build();

    @GET(AppConstant.RSS_BONGDACOM_ARTICLE)
    Call<RssFeed_BDC> getXml_BDC(@Path("id_league") String league);

    @GET(AppConstant.RSS_THETHAO247_ARTICLE)
    Call<RssFeed_BDC> getXml_247(@Path("id_league") String league);

    @GET(AppConstant.RSS_DANTRI_ARTICLE)
    Call<RssFeed_DanTri> getXml_DT(@Path("id_league") String league);

    @GET(AppConstant.RSS_BONGDA24H_ARTICLE)
    Call<RssFeed_DanTri> getXml_24h(@Path("id_league") String league);
}
