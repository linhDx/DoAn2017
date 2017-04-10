package com.linhdx.footballfeed.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.linhdx.footballfeed.R;

/**
 * Created by shine on 10/04/2017.
 */

public class ListLeagueFragment extends Fragment {
    RelativeLayout rlPL, rlPD, rlBL, rlSA, rlLO;
    public ListLeagueFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_list_league_layout,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initListener();
    }


    public void init(View view){
        rlPL = (RelativeLayout)view.findViewById(R.id.league_table_pl);
        rlPD = (RelativeLayout)view.findViewById(R.id.league_table_pd);
        rlBL = (RelativeLayout)view.findViewById(R.id.league_table_bl);
        rlSA = (RelativeLayout)view.findViewById(R.id.league_table_sa);
        rlLO = (RelativeLayout)view.findViewById(R.id.league_table_lo);
    }

    public void initListener(){
        rlPL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(426);
            }
        });
        rlPD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(436);
            }
        });
        rlBL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(430);
            }
        });
        rlSA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(438);
            }
        });
        rlLO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(434);
            }
        });


    }

    public void changeFragment(int id){
        try{
            Bundle bundle = new Bundle();
            bundle.putInt("id",id);
            ShowLeagueFragment showLeagueFragment = new ShowLeagueFragment();
            showLeagueFragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.league_table_container, showLeagueFragment).commit();
        } catch (ClassCastException CastEx){
            Toast.makeText(getActivity(), "error!", Toast.LENGTH_LONG).show();
        }
    }
}
