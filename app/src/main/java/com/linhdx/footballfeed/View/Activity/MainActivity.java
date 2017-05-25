package com.linhdx.footballfeed.View.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.linhdx.footballfeed.adapter.MyPagerAdapter;
import com.linhdx.footballfeed.AppConstant;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork.PlayerNetWorkStatus;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork.TeamNetWorkStatus;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork.TeamNetWorkWrapper;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork.TeamPlayerNetWorkWrapper;
import com.linhdx.footballfeed.NetworkAPI.DataService;
import com.linhdx.footballfeed.NetworkAPI.RssService;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.adapter.SearchPlayerAdapter;
import com.linhdx.footballfeed.entity.Article;
import com.linhdx.footballfeed.entity.TeamPlayer;
import com.linhdx.footballfeed.entity.TeamStatus;
import com.linhdx.footballfeed.utils.SharedPreferencesUtil;
import com.linhdx.footballfeed.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import linhdx.amazing.view.activity.AmazingBaseActivity;
import retrofit2.Call;

public class MainActivity extends AmazingBaseActivity implements SearchView.OnQueryTextListener {
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
    private Stack<Integer> stackkk = new Stack<>();
    private SearchView searchView;
    private MenuItem searchMenuItem;
    private List<TeamPlayer> listPlayer;
    private RelativeLayout rl_pager;
    private ListView lv;
    private SearchPlayerAdapter searchAdapter;

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
        lv = (ListView) findViewById(R.id.lv_list_player);
        rl_pager = (RelativeLayout) findViewById(R.id.rl_pager);

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
                new getListPlayerAndTeam().execute();
            }
        }
        Fresco.initialize(this);
    }

    private void initPlayerList() {
        listPlayer = TeamPlayer.listAll(TeamPlayer.class);
        searchAdapter = new SearchPlayerAdapter(MainActivity.this, listPlayer);
        lv.setAdapter(searchAdapter);
        lv.setTextFilterEnabled(false);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position <= listPlayer.size()) {
                    handelListItemClick((TeamPlayer) searchAdapter.getItem(position));
                }
            }
        });
    }

    @Override
    protected void initListeners() {
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                if (stackkk.empty()) {
                    stackkk.push(0);
                }
                if (stackkk.contains(position)) {
                    stackkk.remove(stackkk.indexOf(position));
                    stackkk.push(position);
                } else {
                    stackkk.push(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search Player");
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                rl_pager.setVisibility(View.VISIBLE);
                lv.setVisibility(View.GONE);
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPlayerList();
                rl_pager.setVisibility(View.GONE);
                lv.setVisibility(View.VISIBLE);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_contact:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
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
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            Log.d("AAAA", "boolean:" + SharedPreferencesUtil.getBooleanPreference(MainActivity.this, AppConstant.SP_BACK, false));
            if (!SharedPreferencesUtil.getBooleanPreference(MainActivity.this, AppConstant.SP_BACK, false)) {
                getSupportFragmentManager().popBackStack();
            } else {
                SharedPreferencesUtil.setBooleanPreference(MainActivity.this, AppConstant.SP_BACK, false);
                stackkk.pop();
                changePager(stackkk.lastElement());

            }

        } else if (stackkk.size() > 1) {
            SharedPreferencesUtil.setBooleanPreference(MainActivity.this, AppConstant.SP_BACK, false);
            stackkk.pop();
            changePager(stackkk.lastElement());
        } else if(!searchView.isIconified()){
            searchView.setIconified(true);
            rl_pager.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchAdapter.getFilter().filter(newText);
        return false;
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
                            item.getCrestUrl(), 1);
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
                            item.getCrestUrl(), 2);
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
                            item.getCrestUrl(), 3);
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
                            item.getCrestUrl(), 4);
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
                                player.getMarketValue(), item, "");
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

    public void changePager(int item) {
        pager.setCurrentItem(item, true);
    }

    private void handelListItemClick(TeamPlayer player) {
        // close search view if its visible
        if (searchView.isShown()) {
            searchMenuItem.collapseActionView();
            searchView.setQuery("", false);
        }

        // pass selected user and sensor to share activity
        Intent intent = new Intent(this, PlayerActivity.class);
        Gson gson = new Gson();
        String j = gson.toJson(player);
        intent.putExtra("player", j);
        this.startActivity(intent);
    }


}
