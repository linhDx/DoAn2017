package com.linhdx.footballfeed.utils;

import android.util.Log;

import com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork.Article_BDC;
import com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork.Article_DanTri;
import com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork.RssFeed_BDC;
import com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork.RssFeed_DanTri;
import com.linhdx.footballfeed.NetworkAPI.RssService;
import com.linhdx.footballfeed.entity.Article;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shine on 29/04/2017.
 */

public class ArticleUtils {


    public static List<Article> getListArticle_BDC(String league){
        final List<Article> list = new ArrayList<>();
        RssService rssService = RssService.retrofit_BDC.create(RssService.class);
        Call<RssFeed_BDC> call = rssService.getXml_BDC(league);
        call.enqueue(new Callback<RssFeed_BDC>() {
            @Override
            public void onResponse(Call<RssFeed_BDC> call, Response<RssFeed_BDC> response) {
                for (Article_BDC item: response.body().getArticleBDCList()
                        ) {
                        Article a = new Article(item.getLink(), item.getTitle(),
                                item.getDescription(), item.getImage(), item.getPubDate(),"BDC");
                    Log.d("AAAA", a.getTitlte());
                    list.add(a);
                }
            }

            @Override
            public void onFailure(Call<RssFeed_BDC> call, Throwable t) {
            }
        });
        return list;
    }
    public static List<Article> getListArticle_247(String league){
        final List<Article> list = new ArrayList<>();
        RssService rssService = RssService.retrofit_thethao247.create(RssService.class);
        Call<RssFeed_BDC> call = rssService.getXml_247(league);
        call.enqueue(new Callback<RssFeed_BDC>() {
            @Override
            public void onResponse(Call<RssFeed_BDC> call, Response<RssFeed_BDC> response) {
                for (Article_BDC item: response.body().getArticleBDCList()
                        ) {
                    Article a = new Article(item.getLink(), item.getTitle(),
                            item.getDescription(), item.getImage(), item.getPubDate(), "247");
                    Log.d("AAAA", a.getTitlte());
                    list.add(a);
                }
            }

            @Override
            public void onFailure(Call<RssFeed_BDC> call, Throwable t) {
            }
        });
        return list;
    }

    public static List<Article> getListArticle_DT(String league){
        final List<Article> list = new ArrayList<>();
        RssService rssService = RssService.retrofit_dantri.create(RssService.class);
        Call<RssFeed_DanTri> call = rssService.getXml_DT(league);
        call.enqueue(new Callback<RssFeed_DanTri>() {
            @Override
            public void onResponse(Call<RssFeed_DanTri> call, Response<RssFeed_DanTri> response) {
                for (Article_DanTri item: response.body().getArticleDTList()
                        ) {
                    Article a = new Article(item.getLink(), item.getTitle(),
                            item.getDescription(), Utils.getImgLink(item.getDescription()), item.getPubDate(),"DT");
                    Log.d("AAAA", a.getTitlte());
                    list.add(a);
                }
            }

            @Override
            public void onFailure(Call<RssFeed_DanTri> call, Throwable t) {
            }
        });
        return list;
    }
    public static List<Article> getListArticle_24h_1(String league){
        final List<Article> list = new ArrayList<>();
        RssService rssService = RssService.retrofit_bongda24h.create(RssService.class);
        Call<RssFeed_DanTri> call = rssService.getXml_24h(league);
        call.enqueue(new Callback<RssFeed_DanTri>() {
            @Override
            public void onResponse(Call<RssFeed_DanTri> call, Response<RssFeed_DanTri> response) {
                for (Article_DanTri item: response.body().getArticleDTList()
                        ) {
                    Article a = new Article(item.getLink(), item.getTitle(),
                            item.getDescription(), "", item.getPubDate(),"24h");
                    Log.d("AAAA", a.getTitlte());
                    list.add(a);
                }
            }

            @Override
            public void onFailure(Call<RssFeed_DanTri> call, Throwable t) {
            }
        });
        return list;
    }
}
