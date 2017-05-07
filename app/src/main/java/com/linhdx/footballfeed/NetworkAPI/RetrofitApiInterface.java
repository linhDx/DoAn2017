package com.linhdx.footballfeed.NetworkAPI;

import com.linhdx.footballfeed.AppObjectNetWork.YoutubeWrap.YoutubeWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by shine on 06/05/2017.
 */

public interface RetrofitApiInterface {
    @GET("search?key="+WebServiceConfig.API_KEY+WebServiceConfig.API_CONFIG)
    Call<YoutubeWrapper> getListVideos(@Query("channelId") String channelUrl, @Query("maxResults") int maxResult, @Query("pageToken") String nextPageToken);
    @GET("search?key="+WebServiceConfig.API_KEY+WebServiceConfig.API_RELATE_CONFIG)
    Call<YoutubeWrapper> getListRelateVideos(@Query("relatedToVideoId") String relatedToVideoId);
}
