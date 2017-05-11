package com.linhdx.footballfeed.View.Activity;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.linhdx.footballfeed.adapter.MyPagerAdapter;
import com.linhdx.footballfeed.AppConstant;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork.PlayerNetWorkStatus;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork.TeamNetWorkStatus;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork.TeamNetWorkWrapper;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork.TeamPlayerNetWorkWrapper;
import com.linhdx.footballfeed.NetworkAPI.DataService;
import com.linhdx.footballfeed.NetworkAPI.RssService;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.entity.Article;
import com.linhdx.footballfeed.entity.TeamPlayer;
import com.linhdx.footballfeed.entity.TeamStatus;
import com.linhdx.footballfeed.utils.ArticleUtils;
import com.linhdx.footballfeed.utils.SharedPreferencesUtil;
import com.linhdx.footballfeed.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import altplus.amazing.view.activity.AmazingBaseActivity;
import retrofit2.Call;

public class MainActivity extends AmazingBaseActivity {
    private final Handler handler = new Handler();
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;
    private Drawable oldBackground = null;
    private int currentColor = 0xFFC74B46;
    private DataService dataService;
    private RssService rssService_BDC, rssService_247;
    List<TeamStatus> listTeams;
    List<Article> listArticle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.soccer);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);
        changeColor(currentColor);
    }

    @Override
    protected void initData() {
        if (SharedPreferencesUtil.getStringPreference(MainActivity.this, AppConstant.SP_TEAM_SAVED) == null) {
            try {
                initDataBase();
            } catch (IOException e) {
            }
        }
//        new getListArtcle().execute();
        Fresco.initialize(this);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_contact:
                Toast.makeText(this, "aaaa", Toast.LENGTH_LONG).show();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    private void changeColor(int newColor) {

        tabs.setIndicatorColor(newColor);

//         change ActionBar color just if an ActionBar is available
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            Drawable colorDrawable = new ColorDrawable(newColor);
            Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
            LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});

            if (oldBackground == null) {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    ld.setCallback(drawableCallback);
                } else {
                    getSupportActionBar().setBackgroundDrawable(ld);
                }

            } else {

                TransitionDrawable td = new TransitionDrawable(new Drawable[]{oldBackground, ld});

                // workaround for broken ActionBarContainer drawable handling on
                // pre-API 17 builds
                // https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    td.setCallback(drawableCallback);
                } else {
                    getSupportActionBar().setBackgroundDrawable(td);
                }

                td.startTransition(200);

            }

            oldBackground = ld;

            // http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);

        }

        currentColor = newColor;

    }

    private Drawable.Callback drawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            getActionBar().setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            handler.postAtTime(what, when);
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
            handler.removeCallbacks(what);
        }
    };

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public class getListPlayerAndTeam extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            listTeams = new ArrayList<>();
            dataService = DataService.retrofit.create(DataService.class);

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

            Call<TeamNetWorkWrapper> call_5 = dataService.getTeamNetWorkStatuses(438);
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
            if (SharedPreferencesUtil.getStringPreference(MainActivity.this, AppConstant.SP_TEAM_SAVED) == null) {
                for (TeamStatus a : listTeams
                        ) {
                    a.save();
                }
                SharedPreferencesUtil.setStringPreference(MainActivity.this, AppConstant.SP_TEAM_SAVED, "saved");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            new getPlayer().execute();
            pd.dismiss();
        }
    }

    public class getPlayer extends AsyncTask<Void, Void, Void> {
        List<TeamPlayer> list;
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pd =new ProgressDialog(MainActivity.this);
//            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            list = new ArrayList<>();
            for (TeamStatus item : listTeams
                    ) {
                Log.d("AAAA", item.getName());
                Call<TeamPlayerNetWorkWrapper> call = dataService.getPlayers(Integer.parseInt(Utils.getTeamId(item.getPlayers())));
                try {
                    TeamPlayerNetWorkWrapper t = call.execute().body();
                    for (PlayerNetWorkStatus player : t.getPlayers()
                            ) {
                        TeamPlayer teamPlayer = new TeamPlayer(player.getName(), player.getPosition(), player.getJerseyNumber(), player.getDateOfBirth(),
                                player.getNationality(), player.getContractUntil(),
                                player.getMarketValue(), item);
                        list.add(teamPlayer);
                    }
                    List<TeamPlayer> check = TeamPlayer.find(TeamPlayer.class, "TEAM_STATUS=?", new String(String.valueOf(item.getId())));
                    if (check.size() == 0) {
                        for (TeamPlayer p : list
                                ) {
                            p.save();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            SharedPreferencesUtil.setStringPreference(MainActivity.this, AppConstant.SP_PLAYER_SAVED, "saved");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            pd.dismiss();
        }
    }


    private void initDataBase() throws IOException {
        String destPath = "/data/data/" + getPackageName()
                + "/databases/mydb.db";
        File f = new File(destPath);
        if (!f.exists()) {
            try {
                SQLiteDatabase checkDB = getApplicationContext().openOrCreateDatabase("mydb.db", getApplicationContext().MODE_PRIVATE, null);
                if (checkDB != null) {
                    InputStream in = getApplicationContext().getAssets().open("mydb.db");
                    OutputStream out = new FileOutputStream(destPath);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                    SharedPreferencesUtil.setStringPreference(MainActivity.this, AppConstant.SP_TEAM_SAVED, "saved");
                }
            } catch (IOException e) {

            }

        }
    }


}
