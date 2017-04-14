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

import com.linhdx.footballfeed.Adapter.CustomListViewLeagueTableAdapter;
import com.linhdx.footballfeed.AppObjectNetWork.LeagueTableNetWorkWrapper;
import com.linhdx.footballfeed.AppObjectNetWork.LeagueTableTeamNetWorkStatus;
import com.linhdx.footballfeed.Entity.LeagueTableTeamStatus;
import com.linhdx.footballfeed.Network.DataService;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shine on 10/04/2017.
 */

public class ShowLeagueFragment extends Fragment {
    DataService dataService;
    List<LeagueTableTeamStatus> listTeam;
    TextView mLeagueName;
    ListView lvLeagueTable;

    public ShowLeagueFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_league_table_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLeagueName = (TextView) view.findViewById(R.id.tv_league_table_title);
        lvLeagueTable= (ListView)view.findViewById(R.id.lv_league_table);
        listTeam = new ArrayList<>();
        dataService = DataService.retrofit.create(DataService.class);
        Bundle id = this.getArguments();
        if(id != null){
            onRequestLeagueTable(id.getInt("id"));
        }
    }

    public void onRequestLeagueTable(int id) {
        Call<LeagueTableNetWorkWrapper> call = dataService.getTableTeamStatuses(id);
        call.enqueue(new Callback<LeagueTableNetWorkWrapper>() {
            @Override
            public void onResponse(Call<LeagueTableNetWorkWrapper> call, Response<LeagueTableNetWorkWrapper> response) {
                mLeagueName.setText(response.body().getLeagueName());
                for (LeagueTableTeamNetWorkStatus item : response.body().getTableTeamStatuses()
                        ) {
                    LeagueTableTeamStatus lt = new LeagueTableTeamStatus(item.getPosition(), item.getTeamName(), item.getCrestURI(), item.getPlayedGames()
                            , item.getPoints(), item.getGoalDifference());
                    listTeam.add(lt);
                }
                CustomListViewLeagueTableAdapter adapter = new CustomListViewLeagueTableAdapter(listTeam, getActivity());
                lvLeagueTable.setAdapter(adapter);
//
            }

            @Override
            public void onFailure(Call<LeagueTableNetWorkWrapper> call, Throwable t) {

            }
        });
    }

}
