package com.linhdx.footballfeed.View.Fragment;

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

import com.google.gson.Gson;
import com.linhdx.footballfeed.Adapter.CustomListViewTeamAdapter;
import com.linhdx.footballfeed.AppObjectNetWork.TeamNetWorkStatus;
import com.linhdx.footballfeed.AppObjectNetWork.TeamNetWorkWrapper;
import com.linhdx.footballfeed.Entity.NextMatchStatus;
import com.linhdx.footballfeed.Entity.TeamStatus;
import com.linhdx.footballfeed.Network.DataService;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.View.CustomView.TabInfor;
import com.linhdx.footballfeed.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shine on 11/04/2017.
 */

public class ListTeamFragment extends Fragment {
    TabInfor tabInfor_PL, tabInfor_PD, tabInfor_SA, tabInfor_BL, tabInfor_LO;
    DataService dataService;
    List<TeamStatus> listnextMatch_PL,listnextMatch_PD,listnextMatch_BL,listnextMatch_SA, listnextMatch_LO;
    public ListTeamFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_list_team_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listnextMatch_BL = new ArrayList<>();
        listnextMatch_PD = new ArrayList<>();
        listnextMatch_PL = new ArrayList<>();
        listnextMatch_SA = new ArrayList<>();
        listnextMatch_LO = new ArrayList<>();

        tabInfor_PL = (TabInfor)view.findViewById(R.id.tabs_PL);
        tabInfor_BL = (TabInfor)view.findViewById(R.id.tabs_BL);
        tabInfor_PD = (TabInfor)view.findViewById(R.id.tabs_PD);
        tabInfor_SA = (TabInfor)view.findViewById(R.id.tabs_SA);
        tabInfor_LO = (TabInfor)view.findViewById(R.id.tabs_LO);

        dataService = DataService.retrofit.create(DataService.class);
        OnRequestNextMatch();
    }

    private void OnRequestNextMatch(){
        Call<TeamNetWorkWrapper> call = dataService.getTeamNetWorkStatuses(426);
        call.enqueue(new Callback<TeamNetWorkWrapper>() {
            @Override
            public void onResponse(Call<TeamNetWorkWrapper> call, Response<TeamNetWorkWrapper> response) {
                tabInfor_PL.setVisibility(View.VISIBLE);
                tabInfor_PL.setmTabTitle("Premier League");
                for (TeamNetWorkStatus item:  response.body().getTeamNetWorkStatuses()
                     ) {
                    TeamStatus a = new TeamStatus(item.getLinks().getFixtures().getHref(),
                            item.getLinks().getPlayers().getHrefTeamPlay(),
                            item.getName(), item.getShortName(), item.getSquadMarketValue(),
                            Utils.convertHttp(item.getCrestUrl()));

                    listnextMatch_PL.add(a);
                    Log.e("AAAA",Utils.convertHttp(item.getCrestUrl()));
                }
                tabInfor_PL.getmTabListView().setAdapter(new CustomListViewTeamAdapter(listnextMatch_PL, getActivity()));
                Utils.setListViewHeightBasedOnItems(tabInfor_PL.getmTabListView());
                tabInfor_PL.getmTabListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        changeTeamPageFragment(listnextMatch_PL.get(position));
                    }
                });
            }

            @Override
            public void onFailure(Call<TeamNetWorkWrapper> call, Throwable t) {


            }
        });

        Call<TeamNetWorkWrapper> call_1 = dataService.getTeamNetWorkStatuses(436);
        call_1.enqueue(new Callback<TeamNetWorkWrapper>() {
            @Override
            public void onResponse(Call<TeamNetWorkWrapper> call, Response<TeamNetWorkWrapper> response) {
                tabInfor_PD.setVisibility(View.VISIBLE);
                tabInfor_PD.setmTabTitle("Premier Division");
                for (TeamNetWorkStatus item:  response.body().getTeamNetWorkStatuses()
                        ) {
                    TeamStatus a = new TeamStatus(item.getLinks().getFixtures().getHref(),
                            item.getLinks().getPlayers().getHrefTeamPlay(),
                            item.getName(), item.getShortName(), item.getSquadMarketValue(),
                            item.getCrestUrl());

                    listnextMatch_PD.add(a);
                }
                tabInfor_PD.getmTabListView().setAdapter(new CustomListViewTeamAdapter(listnextMatch_PD, getActivity()));
                Utils.setListViewHeightBasedOnItems(tabInfor_PD.getmTabListView());
            }

            @Override
            public void onFailure(Call<TeamNetWorkWrapper> call, Throwable t) {


            }
        });
        Call<TeamNetWorkWrapper> call_3 = dataService.getTeamNetWorkStatuses(430);
        call_3.enqueue(new Callback<TeamNetWorkWrapper>() {
            @Override
            public void onResponse(Call<TeamNetWorkWrapper> call, Response<TeamNetWorkWrapper> response) {
                tabInfor_BL.setVisibility(View.VISIBLE);
                tabInfor_BL.setmTabTitle("Buldesliga");
                for (TeamNetWorkStatus item:  response.body().getTeamNetWorkStatuses()
                        ) {
                    TeamStatus a = new TeamStatus(item.getLinks().getFixtures().getHref(),
                            item.getLinks().getPlayers().getHrefTeamPlay(),
                            item.getName(), item.getShortName(), item.getSquadMarketValue(),
                            item.getCrestUrl());

                    listnextMatch_BL.add(a);
                }
                tabInfor_BL.getmTabListView().setAdapter(new CustomListViewTeamAdapter(listnextMatch_BL, getActivity()));
                Utils.setListViewHeightBasedOnItems(tabInfor_BL.getmTabListView());
            }

            @Override
            public void onFailure(Call<TeamNetWorkWrapper> call, Throwable t) {


            }
        });
        Call<TeamNetWorkWrapper> call_2 = dataService.getTeamNetWorkStatuses(438);
        call_2.enqueue(new Callback<TeamNetWorkWrapper>() {
            @Override
            public void onResponse(Call<TeamNetWorkWrapper> call, Response<TeamNetWorkWrapper> response) {
                tabInfor_SA.setVisibility(View.VISIBLE);
                tabInfor_SA.setmTabTitle("Serie A");
                for (TeamNetWorkStatus item:  response.body().getTeamNetWorkStatuses()
                        ) {
                    TeamStatus a = new TeamStatus(item.getLinks().getFixtures().getHref(),
                            item.getLinks().getPlayers().getHrefTeamPlay(),
                            item.getName(), item.getShortName(), item.getSquadMarketValue(),
                            item.getCrestUrl());

                    listnextMatch_SA.add(a);
                }
                tabInfor_SA.getmTabListView().setAdapter(new CustomListViewTeamAdapter(listnextMatch_SA, getActivity()));
                Utils.setListViewHeightBasedOnItems(tabInfor_SA.getmTabListView());
            }

            @Override
            public void onFailure(Call<TeamNetWorkWrapper> call, Throwable t) {


            }
        });
        Call<TeamNetWorkWrapper> call_5 = dataService.getTeamNetWorkStatuses(434);
        call_5.enqueue(new Callback<TeamNetWorkWrapper>() {
            @Override
            public void onResponse(Call<TeamNetWorkWrapper> call, Response<TeamNetWorkWrapper> response) {
                tabInfor_LO.setVisibility(View.VISIBLE);
                tabInfor_LO.setmTabTitle("League One");
                for (TeamNetWorkStatus item:  response.body().getTeamNetWorkStatuses()
                        ) {
                    TeamStatus a = new TeamStatus(item.getLinks().getFixtures().getHref(),
                            item.getLinks().getPlayers().getHrefTeamPlay(),
                            item.getName(), item.getShortName(), item.getSquadMarketValue(),
                            item.getCrestUrl());

                    listnextMatch_LO.add(a);
                }
                tabInfor_LO.getmTabListView().setAdapter(new CustomListViewTeamAdapter(listnextMatch_LO, getActivity()));
                Utils.setListViewHeightBasedOnItems(tabInfor_LO.getmTabListView());
            }

            @Override
            public void onFailure(Call<TeamNetWorkWrapper> call, Throwable t) {


            }
        });

    }

    public void changeTeamPageFragment(TeamStatus teamStatus){
        TeamPageFragment fragment = new TeamPageFragment();
        Bundle args = new Bundle();
        Gson gson = new Gson();
        String j = gson.toJson(teamStatus);
        args.putString("teamStatus", j);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.team_container, fragment).addToBackStack(null).commit();
    }
}
