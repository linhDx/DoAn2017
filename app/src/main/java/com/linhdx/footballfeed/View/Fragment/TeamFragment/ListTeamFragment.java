package com.linhdx.footballfeed.View.Fragment.TeamFragment;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.linhdx.footballfeed.adapter.RecycleViewAdapter;
import com.linhdx.footballfeed.AppConstant;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork.TeamNetWorkStatus;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork.TeamNetWorkWrapper;
import com.linhdx.footballfeed.Controller.RecycleItemClickListener;
import com.linhdx.footballfeed.entity.TeamStatus;
import com.linhdx.footballfeed.NetworkAPI.DataService;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.utils.SharedPreferencesUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by shine on 11/04/2017.
 */

public class ListTeamFragment extends Fragment {
    DataService dataService;
    List<TeamStatus> listTeams;
    RecyclerView lv;
    LinearLayout linearLayout;
    RecyclerView.LayoutManager recylerViewLayoutManager;

    public ListTeamFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return inflater.inflate(R.layout.show_list_team_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listTeams = new ArrayList<>();

        lv = (RecyclerView) view.findViewById(R.id.tab_lv_teams);
        linearLayout = (LinearLayout)view.findViewById(R.id.ln_layout);
        recylerViewLayoutManager = new LinearLayoutManager(getActivity());
        lv.setLayoutManager(recylerViewLayoutManager);

        dataService = DataService.retrofit.create(DataService.class);
        OnRequestNextMatch();
    }

    private void OnRequestNextMatch() {
        if(TeamStatus.listAll(TeamStatus.class).size() ==0) {
            Call<TeamNetWorkWrapper> call = dataService.getTeamNetWorkStatuses(426);
            try {
                TeamNetWorkWrapper teamNetWorkWrapper = call.execute().body();
                for (TeamNetWorkStatus item : teamNetWorkWrapper.getTeamNetWorkStatuses()
                        ) {
                    TeamStatus a = new TeamStatus(item.getLinks().getFixtures().getHref(),
                            item.getLinks().getPlayers().getHrefTeamPlay(),
                            item.getName(), item.getShortName(), item.getSquadMarketValue(),
                            item.getCrestUrl());
                    listTeams.add(a);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Call<TeamNetWorkWrapper> call_1 = dataService.getTeamNetWorkStatuses(436);
            try {
                TeamNetWorkWrapper teamNetWorkWrapper = call_1.execute().body();
                for (TeamNetWorkStatus item : teamNetWorkWrapper.getTeamNetWorkStatuses()
                        ) {
                    TeamStatus a = new TeamStatus(item.getLinks().getFixtures().getHref(),
                            item.getLinks().getPlayers().getHrefTeamPlay(),
                            item.getName(), item.getShortName(), item.getSquadMarketValue(),
                            item.getCrestUrl());
                    listTeams.add(a);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Call<TeamNetWorkWrapper> call_3 = dataService.getTeamNetWorkStatuses(430);
            try {
                TeamNetWorkWrapper teamNetWorkWrapper = call_3.execute().body();
                for (TeamNetWorkStatus item : teamNetWorkWrapper.getTeamNetWorkStatuses()
                        ) {
                    TeamStatus a = new TeamStatus(item.getLinks().getFixtures().getHref(),
                            item.getLinks().getPlayers().getHrefTeamPlay(),
                            item.getName(), item.getShortName(), item.getSquadMarketValue(),
                            item.getCrestUrl());
                    listTeams.add(a);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Call<TeamNetWorkWrapper> call_2 = dataService.getTeamNetWorkStatuses(438);
            try {
                TeamNetWorkWrapper teamNetWorkWrapper = call_2.execute().body();
                for (TeamNetWorkStatus item : teamNetWorkWrapper.getTeamNetWorkStatuses()
                        ) {
                    TeamStatus a = new TeamStatus(item.getLinks().getFixtures().getHref(),
                            item.getLinks().getPlayers().getHrefTeamPlay(),
                            item.getName(), item.getShortName(), item.getSquadMarketValue(),
                            item.getCrestUrl());
                    listTeams.add(a);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
//        Call<TeamNetWorkWrapper> call_5 = dataService.getTeamNetWorkStatuses(434);
//        call_5.enqueue(new Callback<TeamNetWorkWrapper>() {
//            @Override
//            public void onResponse(Call<TeamNetWorkWrapper> call, Response<TeamNetWorkWrapper> response) {
//                for (TeamNetWorkStatus item : response.body().getTeamNetWorkStatuses()
//                        ) {
//                    TeamStatus a = new TeamStatus(item.getLinks().getFixtures().getHref(),
//                            item.getLinks().getPlayers().getHrefTeamPlay(),
//                            item.getName(), item.getShortName(), item.getSquadMarketValue(),
//                            item.getCrestUrl());
//
//                    listTeams.add(a);
//                }
////                tabInfor_LO.getmTabListView().setAdapter(new CustomListViewTeamAdapter(listnextMatch_LO, getActivity()));
////                Utils.setListViewHeightBasedOnItems(tabInfor_LO.getmTabListView());
////                Log.d("AAAA", listTeams.size()+"");
//            }
//
//            @Override
//            public void onFailure(Call<TeamNetWorkWrapper> call, Throwable t) {
//
//
//            }
//        });
            Call<TeamNetWorkWrapper> call_5 = dataService.getTeamNetWorkStatuses(434);
            try {
                TeamNetWorkWrapper teamNetWorkWrapper = call_5.execute().body();
                for (TeamNetWorkStatus item : teamNetWorkWrapper.getTeamNetWorkStatuses()
                        ) {
                    TeamStatus a = new TeamStatus(item.getLinks().getFixtures().getHref(),
                            item.getLinks().getPlayers().getHrefTeamPlay(),
                            item.getName(), item.getShortName(), item.getSquadMarketValue(),
                            item.getCrestUrl());
                    listTeams.add(a);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (SharedPreferencesUtil.getStringPreference(getActivity(), AppConstant.SP_TEAM_SAVED) == null) {
                for (TeamStatus a : listTeams
                        ) {
                    a.save();
                }
                SharedPreferencesUtil.setStringPreference(getActivity(), AppConstant.SP_TEAM_SAVED, "saved");
            }
            lv.setAdapter(new RecycleViewAdapter(listTeams, getActivity()));
        } else {
            listTeams = TeamStatus.listAll(TeamStatus.class);
            lv.setAdapter(new RecycleViewAdapter(listTeams, getActivity()));
        }

//        lv.set(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                changeTeamPageFragment(listTeams.get(position));
//            }
//        });
        lv.addOnItemTouchListener(new RecycleItemClickListener(getActivity(), new RecycleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                changeTeamPageFragment(listTeams.get(position));
            }
        }));
    }

    public void changeTeamPageFragment(TeamStatus teamStatus) {
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
