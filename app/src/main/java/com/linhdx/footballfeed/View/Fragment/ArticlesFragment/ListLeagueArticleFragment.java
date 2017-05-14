package com.linhdx.footballfeed.View.Fragment.ArticlesFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.linhdx.footballfeed.adapter.CustomListViewListLeagueArticle;
import com.linhdx.footballfeed.AppConstant;
import com.linhdx.footballfeed.entity.LeagueNameRss;
import com.linhdx.footballfeed.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shine on 19/04/2017.
 */

public class ListLeagueArticleFragment extends Fragment {
    ListView listView;
    List<LeagueNameRss> list;
    Integer[] imgid = {
            R.drawable.logo_epl_1,
            R.drawable.laliga_logo_1,
            R.drawable.buldesliga_logo_1,
            R.drawable.serie_a_logo_1,
    };
    public ListLeagueArticleFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_league_article_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView)view.findViewById(R.id.lv_list_league_article);
        creatList();
        initListener();
    }

    private void creatList(){
        list = new ArrayList<>();
        for(int i =0; i< 4; i++){
            list.add(new LeagueNameRss(AppConstant.LIST_LEAGUE_RSS[i], AppConstant.LIST_LEAGUE_VALUE_RSS[i]));
        }
        listView.setAdapter(new CustomListViewListLeagueArticle(list, getActivity(), imgid));
    }

    private void initListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeListArticleFragment(list.get(position).getValue());
            }
        });
    }

    public void changeListArticleFragment(String league){
        ListArticleFragment fragment = new ListArticleFragment();
        Bundle args = new Bundle();
        args.putString("league", league);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.article_container, fragment).addToBackStack(null).commit();
    }
}
