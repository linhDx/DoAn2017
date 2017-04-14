package com.linhdx.footballfeed.Network;

import com.linhdx.footballfeed.AppConstant;
import com.linhdx.footballfeed.AppObjectNetWork.LeagueTableNetWorkWrapper;
import com.linhdx.footballfeed.AppObjectNetWork.NextMatchNetWorkWrapper;
import com.linhdx.footballfeed.AppObjectNetWork.TeamNetWorkWrapper;
import com.linhdx.footballfeed.AppObjectNetWork.TeamPlayerNetWorkWrapper;

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

    //http://api.football-data.org/v1/competitions/426/leagueTable
    @GET(AppConstant.LEAGUETABLE)
    Call<LeagueTableNetWorkWrapper> getTableTeamStatuses(@Path("id") int groupID);

    @GET(AppConstant.TEAMS)
    Call<TeamNetWorkWrapper> getTeamNetWorkStatuses(@Path("id") int id);

    @GET(AppConstant.TEAM_PLAYER)
    Call<TeamPlayerNetWorkWrapper> getPlayers(@Path("id") int id);

    @GET(AppConstant.TEAM_COMPETITIONS)
    Call<NextMatchNetWorkWrapper> getCount(@Path("id") int groupID);
}
