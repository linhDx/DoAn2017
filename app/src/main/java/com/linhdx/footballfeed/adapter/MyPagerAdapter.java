package com.linhdx.footballfeed.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.linhdx.footballfeed.NetworkAPI.WebServiceConfig;
import com.linhdx.footballfeed.View.Fragment.ArticlesFragment.ArticlesFragment;
import com.linhdx.footballfeed.View.Fragment.LeagueTableFragment.LeagueTableFragment;
import com.linhdx.footballfeed.View.Fragment.MatchDayFragment.MatchDayFragment;
import com.linhdx.footballfeed.View.Fragment.MatchResultFragment.MatchResultFragment;
import com.linhdx.footballfeed.View.Fragment.SuperAwesomeCardFragment;
import com.linhdx.footballfeed.View.Fragment.TeamFragment.TeamFragment;
import com.linhdx.footballfeed.View.Fragment.YoutubeVideoFragment.ChannelFragment;

/**
 * Created by shine on 07/04/2017.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES = { "Match Result","Matches", "Teams", "LeagueTable","Video", "Articles"};

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment a ;
        switch (position){
            case 0:
                a= new MatchResultFragment();
                break;
            case 1:
                a= new MatchDayFragment();
                break;
            case 2:
                a = new TeamFragment();
                break;
            case 3:
                a = new LeagueTableFragment();
                break;
            case 4:
                a = ChannelFragment.newInstance("Video", WebServiceConfig.CHANNEL1);
                break;
            case 5:
                a= new ArticlesFragment();
                break;
            default:
                a=  SuperAwesomeCardFragment.newInstance(position);
                break;
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
