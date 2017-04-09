package com.linhdx.footballfeed.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.linhdx.footballfeed.View.Fragment.MatchDayFragment;
import com.linhdx.footballfeed.View.Fragment.SuperAwesomeCardFragment;

/**
 * Created by shine on 07/04/2017.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES = { "Matches", "LeagueTable", "Teams", "Articles"};

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment a ;
        if(position==0){
            a= new MatchDayFragment();
        } else {
            a = SuperAwesomeCardFragment.newInstance(position);
        }
        return a;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

}
