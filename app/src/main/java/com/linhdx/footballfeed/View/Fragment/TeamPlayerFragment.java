package com.linhdx.footballfeed.View.Fragment;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.linhdx.footballfeed.Adapter.CustomListViewTeamPlayerAdapter;
import com.linhdx.footballfeed.AppObjectNetWork.PlayerNetWorkStatus;
import com.linhdx.footballfeed.AppObjectNetWork.TeamPlayerNetWorkWrapper;
import com.linhdx.footballfeed.Entity.Player;
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

public class TeamPlayerFragment extends Fragment {
    TextView tvTeamName;
    ListView lv;
    ImageView imBack;
    TeamStatus teamStatus;
    DataService dataService;
    List<Player> list;

    public TeamPlayerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getString("teamStatus") != null) {
            Gson gson = new Gson();
            teamStatus = gson.fromJson(bundle.getString("teamStatus"), TeamStatus.class);
        }
        return inflater.inflate(R.layout.team_player_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTeamName = (TextView) view.findViewById(R.id.tv_team_player_title);
        if (teamStatus != null) {
            tvTeamName.setText(teamStatus.getName());
        }
        imBack = (ImageView) view.findViewById(R.id.im_team_player_back);
        lv = (ListView) view.findViewById(R.id.lv_team_players);
        dataService = DataService.retrofit.create(DataService.class);
        list = new ArrayList<>();
        onRequestPlayer(Integer.parseInt(Utils.getTeamId(teamStatus.getPlayers())));
    }

    private void onRequestPlayer(final int id) {
        Call<TeamPlayerNetWorkWrapper> call = dataService.getPlayers(id);
        call.enqueue(new Callback<TeamPlayerNetWorkWrapper>() {
            @Override
            public void onResponse(Call<TeamPlayerNetWorkWrapper> call, Response<TeamPlayerNetWorkWrapper> response) {
                for (PlayerNetWorkStatus item : response.body().getPlayers()) {

                    Player player = new Player(item.getName(), item.getPosition(), item.getJerseyNumber(), item.getDateOfBirth(),
                            item.getNationality(), item.getContractUntil(),
                            item.getMarketValue());
                    list.add(player);
                }

                CustomListViewTeamPlayerAdapter adapter = new CustomListViewTeamPlayerAdapter(list, getActivity());
                lv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<TeamPlayerNetWorkWrapper> call, Throwable t) {

            }
        });
    }
}
