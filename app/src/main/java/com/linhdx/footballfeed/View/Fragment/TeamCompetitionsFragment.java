package com.linhdx.footballfeed.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.linhdx.footballfeed.Adapter.CustomListViewCompetitionsAdapter;
import com.linhdx.footballfeed.Adapter.CustomListViewMatchDayAdapter;
import com.linhdx.footballfeed.AppObjectNetWork.NextMatchNetWorkStatus;
import com.linhdx.footballfeed.AppObjectNetWork.NextMatchNetWorkWrapper;
import com.linhdx.footballfeed.Entity.NextMatchStatus;
import com.linhdx.footballfeed.Entity.TeamStatus;
import com.linhdx.footballfeed.Network.DataService;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shine on 14/04/2017.
 */

public class TeamCompetitionsFragment extends Fragment {
    List<NextMatchStatus> list;
    DataService dataService;
    TextView tvName;
    TeamStatus teamStatus;
    ListView lv;
    public TeamCompetitionsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getString("teamStatus") != null) {
            Gson gson = new Gson();
            teamStatus = gson.fromJson(bundle.getString("teamStatus"), TeamStatus.class);
        }
        return inflater.inflate(R.layout.team_competitions_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        dataService = DataService.retrofit.create(DataService.class);
        lv = (ListView)view.findViewById(R.id.lv_team_competitions);
        tvName = (TextView)view.findViewById(R.id.tv_team_competitions_title);
        if(teamStatus!= null){
            tvName.setText(teamStatus.getName());
        }

        OnRequestCompetitions(Integer.parseInt(Utils.getTeamId(teamStatus.getPlayers())));
    }

    private void OnRequestCompetitions(int id){
        Call<NextMatchNetWorkWrapper> call = dataService.getCount(id);
        call.enqueue(new Callback<NextMatchNetWorkWrapper>() {
            @Override
            public void onResponse(Call<NextMatchNetWorkWrapper> call, Response<NextMatchNetWorkWrapper> response) {
                int count= 0;
                count = response.body().getCount();
                if(count >0){
                    for (NextMatchNetWorkStatus item: response.body().getNextMatchStatusesPL()
                            ) {
                        NextMatchStatus nm = new NextMatchStatus(Utils.convertTime(item.getDate())[0],
                                Utils.convertTime(item.getDate())[1],
                                item.getHomeTeamName(),
                                item.getAwayTeamName(),
                                item.getResult().getGoalsHomeTeam(),
                                item.getResult().getGoalsAwayTeam(),
                                item.getStatus());
                        list.add(nm);
                    }
                    CustomListViewCompetitionsAdapter adapter = new CustomListViewCompetitionsAdapter(getActivity(),list);
                    lv.setAdapter(adapter);
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
