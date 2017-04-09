package com.linhdx.footballfeed.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.linhdx.footballfeed.Adapter.CustomListViewMatchDayAdapter;
import com.linhdx.footballfeed.AppObjectNetWork.NextMatchNetWorkStatus;
import com.linhdx.footballfeed.AppObjectNetWork.NextMatchNetWorkWrapper;
import com.linhdx.footballfeed.Entity.NextMatchStatus;
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
 * Created by shine on 07/04/2017.
 */

public class MatchDayFragment extends Fragment {
    TabInfor tabInfor_PL, tabInfor_PD, tabInfor_SA, tabInfor_BL;
    DataService dataService;
    List<NextMatchStatus> listnextMatch_PL,listnextMatch_PD,listnextMatch_BL,listnextMatch_SA;
    public MatchDayFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.matchday_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listnextMatch_BL = new ArrayList<>();
        listnextMatch_PD = new ArrayList<>();
        listnextMatch_PL = new ArrayList<>();
        listnextMatch_SA = new ArrayList<>();

        tabInfor_PL = (TabInfor)view.findViewById(R.id.tabs_PL);
        tabInfor_BL = (TabInfor)view.findViewById(R.id.tabs_BL);
        tabInfor_PD = (TabInfor)view.findViewById(R.id.tabs_PD);
        tabInfor_SA = (TabInfor)view.findViewById(R.id.tabs_SA);

        dataService = DataService.retrofit.create(DataService.class);
        OnRequestNextMatch();
    }

    private void OnRequestNextMatch(){
        Call<NextMatchNetWorkWrapper> call = dataService.getNextMatchStatusesPL(426);
        call.enqueue(new Callback<NextMatchNetWorkWrapper>() {
            @Override
            public void onResponse(Call<NextMatchNetWorkWrapper> call, Response<NextMatchNetWorkWrapper> response) {
                int count= 0;
                count = response.body().getCount();
                if(count >0){
                    tabInfor_PL.setVisibility(View.VISIBLE);
                    for (NextMatchNetWorkStatus item: response.body().getNextMatchStatusesPL()
                         ) {
                        NextMatchStatus nm = new NextMatchStatus(Utils.convertTime(item.getDate())[0],
                                Utils.convertTime(item.getDate())[1],
                                item.getHomeTeamName(),
                                item.getAwayTeamName(),
                                item.getResult().getGoalsHomeTeam(),
                                item.getResult().getGoalsAwayTeam(),
                                item.getStatus());
                        listnextMatch_PL.add(nm);
                    }
                    CustomListViewMatchDayAdapter adapter = new CustomListViewMatchDayAdapter(getActivity(),listnextMatch_PL);
                    tabInfor_PL.getmTabListView().setAdapter(adapter);
                    Utils.setListViewHeightBasedOnItems(tabInfor_PL.getmTabListView());
                } else {
                    Log.d("AAA", "khong co tran nao");
                }
            }

            @Override
            public void onFailure(Call<NextMatchNetWorkWrapper> call, Throwable t) {

            }
        });
        Call<NextMatchNetWorkWrapper> call_2 = dataService.getNextMatchStatusesPL(436);
        call_2.enqueue(new Callback<NextMatchNetWorkWrapper>() {
            @Override
            public void onResponse(Call<NextMatchNetWorkWrapper> call, Response<NextMatchNetWorkWrapper> response) {
                int count= 0;
                count = response.body().getCount();
                if(count >0){
                    tabInfor_PD.setVisibility(View.VISIBLE);
                    tabInfor_PD.setmTabTitle("Premier Division");
                    for (NextMatchNetWorkStatus item: response.body().getNextMatchStatusesPL()
                            ) {
                        NextMatchStatus nm = new NextMatchStatus(Utils.convertTime(item.getDate())[0],
                                Utils.convertTime(item.getDate())[1],
                                item.getHomeTeamName(),
                                item.getAwayTeamName(),
                                item.getResult().getGoalsHomeTeam(),
                                item.getResult().getGoalsAwayTeam(),
                                item.getStatus());
                        listnextMatch_PD.add(nm);
                    }
                    CustomListViewMatchDayAdapter adapter = new CustomListViewMatchDayAdapter(getActivity(),listnextMatch_PD);
                    tabInfor_PD.getmTabListView().setAdapter(adapter);
                    Utils.setListViewHeightBasedOnItems(tabInfor_PD.getmTabListView());
                } else {
                    Log.d("AAA", "khong co tran nao");
                }
            }

            @Override
            public void onFailure(Call<NextMatchNetWorkWrapper> call, Throwable t) {

            }
        });

        Call<NextMatchNetWorkWrapper> call_3 = dataService.getNextMatchStatusesPL(430);
        call_3.enqueue(new Callback<NextMatchNetWorkWrapper>() {
            @Override
            public void onResponse(Call<NextMatchNetWorkWrapper> call, Response<NextMatchNetWorkWrapper> response) {
                int count= 0;
                count = response.body().getCount();
                if(count >0){
                    tabInfor_BL.setVisibility(View.VISIBLE);
                    tabInfor_BL.setmTabTitle("Buldesliga");
                    for (NextMatchNetWorkStatus item: response.body().getNextMatchStatusesPL()
                            ) {
                        NextMatchStatus nm = new NextMatchStatus(Utils.convertTime(item.getDate())[0],
                                Utils.convertTime(item.getDate())[1],
                                item.getHomeTeamName(),
                                item.getAwayTeamName(),
                                item.getResult().getGoalsHomeTeam(),
                                item.getResult().getGoalsAwayTeam(),
                                item.getStatus());
                        listnextMatch_BL.add(nm);
                    }
                    CustomListViewMatchDayAdapter adapter = new CustomListViewMatchDayAdapter(getActivity(),listnextMatch_BL);
                    tabInfor_BL.getmTabListView().setAdapter(adapter);
                    Utils.setListViewHeightBasedOnItems(tabInfor_BL.getmTabListView());
                } else {
                    Log.d("AAA", "khong co tran nao");
                }
            }

            @Override
            public void onFailure(Call<NextMatchNetWorkWrapper> call, Throwable t) {

            }
        });

        Call<NextMatchNetWorkWrapper> call_4 = dataService.getNextMatchStatusesPL(438);
        call_4.enqueue(new Callback<NextMatchNetWorkWrapper>() {
            @Override
            public void onResponse(Call<NextMatchNetWorkWrapper> call, Response<NextMatchNetWorkWrapper> response) {
                int count= 0;
                count = response.body().getCount();
                if(count >0){
                    tabInfor_SA.setVisibility(View.VISIBLE);
                    tabInfor_SA.setmTabTitle("Serie A");
                    for (NextMatchNetWorkStatus item: response.body().getNextMatchStatusesPL()
                            ) {
                        NextMatchStatus nm = new NextMatchStatus(Utils.convertTime(item.getDate())[0],
                                Utils.convertTime(item.getDate())[1],
                                item.getHomeTeamName(),
                                item.getAwayTeamName(),
                                item.getResult().getGoalsHomeTeam(),
                                item.getResult().getGoalsAwayTeam(),
                                item.getStatus());
                        listnextMatch_SA.add(nm);
                    }
                    CustomListViewMatchDayAdapter adapter = new CustomListViewMatchDayAdapter(getActivity(),listnextMatch_SA);
                    tabInfor_SA.getmTabListView().setAdapter(adapter);
                    Utils.setListViewHeightBasedOnItems(tabInfor_SA.getmTabListView());
                } else {
                    Log.d("AAA", "khong co tran nao");
                }
            }

            @Override
            public void onFailure(Call<NextMatchNetWorkWrapper> call, Throwable t) {

            }
        });
    }
}
