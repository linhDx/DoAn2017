package com.linhdx.footballfeed.View.Fragment.ArticlesFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.linhdx.footballfeed.adapter.CustomListViewListArticle;
import com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork.Article_BDC;
import com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork.RssFeed_BDC;
import com.linhdx.footballfeed.NetworkAPI.RssService;
import com.linhdx.footballfeed.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shine on 19/04/2017.
 */
// hien thi cac bai bao cho giai dau
public class ListArticleFragment extends Fragment {
    List<Article_BDC> list;
    ListView lv;
    RssService rssService;
    String league;
    public ListArticleFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if( bundle!= null && bundle.getString("league")!= null){
            league = bundle.getString("league");
            Toast.makeText(getActivity(), league, Toast.LENGTH_LONG).show();
        }
        return inflater.inflate(R.layout.list_article_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv =(ListView)view.findViewById(R.id.lv_list_article);
        rssService = RssService.retrofit_BDC.create(RssService.class);
        getListArticle(league);
        initListener();
    }

    private void getListArticle(String league){
        Call<RssFeed_BDC> call = rssService.getXml_BDC(league);
        call.enqueue(new Callback<RssFeed_BDC>() {
            @Override
            public void onResponse(Call<RssFeed_BDC> call, Response<RssFeed_BDC> response) {
                list = new ArrayList<Article_BDC>();
                for (Article_BDC item: response.body().getArticleBDCList()
                     ) {
                    list.add(item);
                }
                lv.setAdapter(new CustomListViewListArticle(getActivity(), list));
                Log.d("AAAA+size", list.size()+"");
            }

            @Override
            public void onFailure(Call<RssFeed_BDC> call, Throwable t) {
                Log.d("AAAA+size", "tach");
            }
        });
    }

    private void initListener(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeListArticleFragment(list.get(position).getLink());
            }
        });
    }
    public void changeListArticleFragment(String url){
        PageArticleFragment fragment = new PageArticleFragment();
        Bundle args = new Bundle();
        args.putString("link", url);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.article_container, fragment).addToBackStack(null).commit();
    }
}
