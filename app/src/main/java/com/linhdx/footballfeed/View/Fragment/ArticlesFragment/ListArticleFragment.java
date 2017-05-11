package com.linhdx.footballfeed.View.Fragment.ArticlesFragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.linhdx.footballfeed.AppConstant;
import com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork.Article_DanTri;
import com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork.RssFeed_DanTri;
import com.linhdx.footballfeed.View.Activity.MainActivity;
import com.linhdx.footballfeed.adapter.CustomListViewListArticle;
import com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork.Article_BDC;
import com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork.RssFeed_BDC;
import com.linhdx.footballfeed.NetworkAPI.RssService;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.entity.Article;
import com.linhdx.footballfeed.utils.ArticleUtils;
import com.linhdx.footballfeed.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import altplus.amazing.view.widget.AmazingRecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shine on 19/04/2017.
 */
// hien thi cac bai bao cho giai dau
public class ListArticleFragment extends Fragment {
    private List<Article> list, showList;
    private int count;
    private AmazingRecyclerView lv;
    private CustomListViewListArticle mAdapter;
    private String league;
    private boolean isDT;

    public ListArticleFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getString("league") != null) {
            league = bundle.getString("league");
            Toast.makeText(getActivity(), league, Toast.LENGTH_LONG).show();
        } else {
            league = "error";
        }
        return inflater.inflate(R.layout.list_article_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = (AmazingRecyclerView) view.findViewById(R.id.lv_list_article);
        lv.setLayoutManager(new LinearLayoutManager(getActivity()));

        count = 1;
        list = new ArrayList<>();
        showList = new ArrayList<>();

        mAdapter = new CustomListViewListArticle(getMainActivity(), showList);
        lv.setAdapter(mAdapter);
        initData();
        initListener();
    }

    private void initData() {
        isDT = true;
        if (league.compareTo("error") != 0) {
            switch (league) {
                case "1":
                    new getListArtcle().execute(AppConstant.LIST_ARTICLE_PL);
                    break;
                case "2":
                    new getListArtcle().execute(AppConstant.LIST_ARTICLE_PD);
                    break;
                case "3":
                    new getListArtcle().execute(AppConstant.LIST_ARTICLE_BL);
                    break;
                case "4":
                    new getListArtcle().execute(AppConstant.LIST_ARTICLE_SA);
                    break;
            }
        }
    }

    private void initListener() {
        lv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showList.clear();
                showListArticle(list, 1);
                lv.refreshList();
            }
        });
        lv.setOnLoadMoreListener(new AmazingRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (count < 4) {
                    count = count + 1;
                }
                mAdapter.notifyItemChanged(list.size() - 1);
                lv.recyclerViewActual.smoothScrollToPosition(list.size());
                showListArticle(list, count);
            }
        });
        mAdapter.setOnItemClickListener(new CustomListViewListArticle.OnItemClickListener() {
            @Override
            public void onClickListener(int position) {
                // chuyen trang xem
                Fragment a = PageArticleFragment.newInstance(list.get(position).getWebName(), list.get(position).getLink());
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.add(R.id.article_container, a).commit();
            }
        });
    }

    public class getListArtcle extends AsyncTask<String, Void, Void> {
        ProgressDialog pd;

        @Override
        protected Void doInBackground(String... params) {
            String fb247, fbdt, fb24h, fbbdc;
            fb247 = params[0];
            fbdt = params[1];
            fb24h = params[2];
            fbbdc = params[3];

            if (fbdt.compareTo("error") != 0) {
                getListArticle_DT(fbdt);
            } else {
                isDT = false;
            }
            getListArticle_247(fb247);
            getListArticle_24h_1(fb24h);
            getListArticle_BDC(fbbdc);

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
        }
    }

    public void getListArticle_BDC(String league) {
        RssService rssService = RssService.retrofit_BDC.create(RssService.class);
        Call<RssFeed_BDC> call = rssService.getXml_BDC(league);
        call.enqueue(new Callback<RssFeed_BDC>() {
            @Override
            public void onResponse(Call<RssFeed_BDC> call, Response<RssFeed_BDC> response) {
                for (Article_BDC item : response.body().getArticleBDCList()
                        ) {
                    Article a = new Article(item.getLink(), item.getTitle(),
                            item.getDescription(), item.getImage(), Utils.StringToDate_BDC(item.getPubDate()), "BDC");
                    list.add(a);
                }
                if (isDT) {
                    if (list.size() == 75) {
                        sortList(list);
                        showListArticle(list, count);
                    }
                } else {
                    if (list.size() == 65) {
                        sortList(list);
                        showListArticle(list, count);
                    }
                }
            }

            @Override
            public void onFailure(Call<RssFeed_BDC> call, Throwable t) {
            }
        });
    }

    public void getListArticle_24h_1(String league) {
        RssService rssService = RssService.retrofit_bongda24h.create(RssService.class);
        Call<RssFeed_DanTri> call = rssService.getXml_24h(league);
        call.enqueue(new Callback<RssFeed_DanTri>() {
            @Override
            public void onResponse(Call<RssFeed_DanTri> call, Response<RssFeed_DanTri> response) {
                for (Article_DanTri item : response.body().getArticleDTList()
                        ) {
                    Article a = new Article(item.getLink(), item.getTitle(),
                            item.getDescription(), "", Utils.StringToDate_24h(item.getPubDate()), "24h");
                    list.add(a);
                }
                if (isDT) {
                    if (list.size() == 75) {
                        sortList(list);
                        showListArticle(list, count);
                    }
                } else {
                    if (list.size() == 65) {
                        sortList(list);
                        showListArticle(list, count);
                    }
                }
            }

            @Override
            public void onFailure(Call<RssFeed_DanTri> call, Throwable t) {
            }
        });
    }

    public void getListArticle_247(String league) {
        RssService rssService = RssService.retrofit_thethao247.create(RssService.class);
        Call<RssFeed_BDC> call = rssService.getXml_247(league);
        call.enqueue(new Callback<RssFeed_BDC>() {
            @Override
            public void onResponse(Call<RssFeed_BDC> call, Response<RssFeed_BDC> response) {
                for (Article_BDC item : response.body().getArticleBDCList()
                        ) {
                    Article a = new Article(item.getLink(), item.getTitle(),
                            item.getDescription(), item.getImage(), Utils.StringToDate(item.getPubDate()), "247");
                    list.add(a);
                }
                if (isDT) {
                    if (list.size() == 75) {
                        sortList(list);
                        showListArticle(list, count);
                    }
                } else {
                    if (list.size() == 65) {
                        sortList(list);
                        showListArticle(list, count);
                    }
                }
            }

            @Override
            public void onFailure(Call<RssFeed_BDC> call, Throwable t) {
            }
        });
    }

    public void getListArticle_DT(String league) {
        RssService rssService = RssService.retrofit_dantri.create(RssService.class);
        Call<RssFeed_DanTri> call = rssService.getXml_DT(league);
        call.enqueue(new Callback<RssFeed_DanTri>() {
            @Override
            public void onResponse(Call<RssFeed_DanTri> call, Response<RssFeed_DanTri> response) {
                for (Article_DanTri item : response.body().getArticleDTList()
                        ) {
                    Article a = new Article(item.getLink(), item.getTitle(),
                            item.getDescription(), Utils.getImgLink(item.getDescription()), Utils.StringToDate(item.getPubDate()), "DT");
                    list.add(a);
                }
                if (isDT) {
                    if (list.size() == 75) {
                        sortList(list);
                        showListArticle(list, count);
                    }
                } else {
                    if (list.size() == 65) {
                        sortList(list);
                        showListArticle(list, count);
                    }
                }
            }

            @Override
            public void onFailure(Call<RssFeed_DanTri> call, Throwable t) {
            }
        });
    }

    public void sortList(List<Article> lt) {
        Collections.sort(lt, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                return o1.getPubDate().compareTo(o2.getPubDate());
            }
        });
        Collections.reverse(lt);
    }

    public void showListArticle(List<Article> list, int count) {
        switch (count) {
            case 1:
                showList.clear();
                for (int i = 0; i < 20; i++) {
                    showList.add(list.get(i));
                }
                mAdapter.notifyDataSetChanged();
                lv.refreshList();
                break;
            case 2:
                showList.clear();
                for (int i = 0; i < 40; i++) {
                    showList.add(list.get(i));
                }
                mAdapter.notifyDataSetChanged();
                lv.refreshList();
                break;
            case 3:
                showList.clear();
                for (int i = 0; i < 60; i++) {
                    showList.add(list.get(i));
                }
                mAdapter.notifyDataSetChanged();
                lv.refreshList();
                break;
            case 4:
                showList.clear();
                for (int i = 0; i < list.size(); i++) {
                    showList.add(list.get(i));
                }
                mAdapter.notifyDataSetChanged();
                lv.refreshList();
                break;
        }
    }

    protected MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

}
