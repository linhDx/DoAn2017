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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.linhdx.footballfeed.Entity.TeamStatus;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by shine on 11/04/2017.
 */

public class TeamPageFragment extends Fragment {
    TextView teamNameTitle, teamName, shortTeamName, marketvalue;
    ImageView imBack, imIconTeam;
    RelativeLayout rlTeamPlayer, rlTeamCompetitions;
    TeamStatus teamStatus;
    public TeamPageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if( bundle!= null && bundle.getString("teamStatus")!= null){
            Gson gson = new Gson();
            teamStatus = gson.fromJson(bundle.getString("teamStatus"), TeamStatus.class);
        }
        return inflater.inflate(R.layout.team_page_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        teamNameTitle = (TextView)view.findViewById(R.id.tv_team_page_title);
        teamName = (TextView)view.findViewById(R.id.tv_team_page_name);
        shortTeamName = (TextView)view.findViewById(R.id.tv_team_page_short_name);
        marketvalue = (TextView)view.findViewById(R.id.tv_team_page_market_value);
        imIconTeam = (ImageView)view.findViewById(R.id.im_team_page_icon);
        imBack = (ImageView)view.findViewById(R.id.im_team_page_back);
        rlTeamPlayer =(RelativeLayout)view.findViewById(R.id.rl_view_team_player);
        rlTeamCompetitions =(RelativeLayout)view.findViewById(R.id.rl_view_team_competitions);

        init(teamStatus);
        initListener(teamStatus);
    }

    private void init(TeamStatus teamStatus) {
        teamNameTitle.setText(teamStatus.getName());
        teamName.setText(teamStatus.getName());
        shortTeamName.setText(teamStatus.getShortName());
        marketvalue.setText(teamStatus.getMerketValue());
        Picasso.with(getActivity()).load(teamStatus.getImage()).into(imIconTeam, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("AAAA", "load  duoc");
            }

            @Override
            public void onError() {
Log.e("AAAA", "load deo duoc");
            }
        });
    }

    private void initListener(final TeamStatus teamStatus){
        rlTeamPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayerFragment(teamStatus);
            }
        });

        rlTeamCompetitions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCompetitionsFragment(teamStatus);
            }
        });
    }

    private void changePlayerFragment(TeamStatus teamStatus){
        TeamPlayerFragment teamPlayerFragment = new TeamPlayerFragment();
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        String j = gson.toJson(teamStatus);
        bundle.putString("teamStatus", j);
        teamPlayerFragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.team_container, teamPlayerFragment).addToBackStack(null).commit();
    }

    private void changeCompetitionsFragment (TeamStatus teamStatus){
        TeamCompetitionsFragment teamPlayerFragment = new TeamCompetitionsFragment();
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        String j = gson.toJson(teamStatus);
        bundle.putString("teamStatus", j);
        teamPlayerFragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.team_container, teamPlayerFragment).addToBackStack(null).commit();
    }
}
