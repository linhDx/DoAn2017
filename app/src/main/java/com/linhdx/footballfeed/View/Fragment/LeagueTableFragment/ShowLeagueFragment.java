package com.linhdx.footballfeed.View.Fragment.LeagueTableFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.linhdx.footballfeed.adapter.CustomListViewLeagueTableAdapter;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork.LeagueTableNetWorkWrapper;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork.LeagueTableTeamNetWorkStatus;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataPreventive.DataWrapper;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataPreventive.Standing;
import com.linhdx.footballfeed.NetworkAPI.DataPreventiveService;
import com.linhdx.footballfeed.entity.LeagueTableTeamStatus;
import com.linhdx.footballfeed.NetworkAPI.DataService;
import com.linhdx.footballfeed.R;

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
    ImageView mBack;
    DataPreventiveService dataPreventiveService;
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
        mBack = (ImageView)view.findViewById(R.id.im_league_table_back) ;
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getFragmentManager().getBackStackEntryCount() >0){
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();
                }
            }
        });
        listTeam = new ArrayList<>();
        dataService = DataService.retrofit.create(DataService.class);
        dataPreventiveService = DataPreventiveService.retrofit.create(DataPreventiveService.class);
        Bundle id = this.getArguments();
        if(id != null){
            onRequestLeagueTable(id.getInt("id"));
            switch (id.getInt("id")){
                case 426:
                    mLeagueName.setText("Premier League");
                    break;
                case 430:
                    mLeagueName.setText("Buldesliga");
                    break;
                case 436:
                    mLeagueName.setText("Premier Division");
                    break;
                case 438:
                    mLeagueName.setText("Serie A");
                    break;
                default:break;
            }
        }
    }

    public void onRequestLeagueTable(final int id_league) {
        Call<LeagueTableNetWorkWrapper> call = dataService.getTableTeamStatuses(id_league);
        call.enqueue(new Callback<LeagueTableNetWorkWrapper>() {
            @Override
            public void onResponse(Call<LeagueTableNetWorkWrapper> call, Response<LeagueTableNetWorkWrapper> response) {

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
                switch (id_league){
                    case 426:
                       requestPreventive("premier-league");
                        break;
                    case 430:
                        requestPreventive("bundesliga");
                        break;
                    case 436:
                        requestPreventive("liga");
                        break;
                    case 438:
                        requestPreventive("serie-a");
                        break;
                    default:break;
                }
            }
        });
    }

    public void requestPreventive(String league_slug){
        Call<DataWrapper> call = dataPreventiveService.getData(league_slug);
        call.enqueue(new Callback<DataWrapper>() {
            @Override
            public void onResponse(Call<DataWrapper> call, Response<DataWrapper> response) {
                for (Standing item: response.body().getData().getStandings()
                     ) {
                    LeagueTableTeamStatus lt= new LeagueTableTeamStatus(item.getPosition(), item.getTeam(),
                            "", item.getOverall().getMatchesPlayed(), item.getOverall().getPoints()
                    , item.getOverall().getGoalDifference());
                    listTeam.add(lt);
                }
                CustomListViewLeagueTableAdapter adapter = new CustomListViewLeagueTableAdapter(listTeam, getActivity());
                lvLeagueTable.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<DataWrapper> call, Throwable t) {

            }
        });
    }
}
