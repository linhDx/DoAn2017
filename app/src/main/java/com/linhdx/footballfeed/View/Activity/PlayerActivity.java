package com.linhdx.footballfeed.View.Activity;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.entity.TeamPlayer;
import com.linhdx.footballfeed.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;

import linhdx.amazing.view.activity.AmazingBaseActivity;

/**
 * Created by shine on 23/05/2017.
 */

public class PlayerActivity extends AmazingBaseActivity {
    TeamPlayer mPlayer;
    ImageView imgPlayer;
    TextView name, position, age, height, weight, value, wage, club, position_club, overall, jersey_number, joined, contrac;
    String player_image, player_name;
    String[] table1, table2;
    String url;
    LinearLayout ll_infor;
    TextView tv_no_infor;
    private Drawable oldBackground = null;
    private int currentColor = 0xFFC74B46;
    private final Handler handler = new Handler();
    @Override
    protected int getLayoutId() {
        return R.layout.player_activity;
    }

    @Override
    protected void initViews() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        changeColor(currentColor);

        ll_infor = (LinearLayout)findViewById(R.id.ll_infor);
        tv_no_infor = (TextView)findViewById(R.id.tv_no_infor);
        imgPlayer = (ImageView) findViewById(R.id.img_player);
        name = (TextView) findViewById(R.id.tv_player_name);
        position = (TextView) findViewById(R.id.tv_player_position);
        age = (TextView) findViewById(R.id.tv_player_age);
        height = (TextView) findViewById(R.id.tv_player_height);
        weight = (TextView) findViewById(R.id.tv_player_weight);
        value = (TextView) findViewById(R.id.tv_player_value);
        wage = (TextView) findViewById(R.id.tv_player_wage);
        club = (TextView) findViewById(R.id.tv_player_club);
        overall = (TextView) findViewById(R.id.tv_player_overall);
        position_club = (TextView) findViewById(R.id.tv_player_club_position);
        jersey_number = (TextView) findViewById(R.id.tv_player_number);
        joined = (TextView) findViewById(R.id.tv_player_joined);
        contrac = (TextView) findViewById(R.id.tv_player_contract);
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getString("player") != null) {
            Gson gson = new Gson();
            mPlayer = gson.fromJson(bundle.getString("player"), TeamPlayer.class);
            Log.d("AAAA", mPlayer.getName());
        }
        if (mPlayer.getFifa_id() != null) {
            ll_infor.setVisibility(View.VISIBLE);
            tv_no_infor.setVisibility(View.GONE);
            url = "https://sofifa.com/player/" + mPlayer.getFifa_id();
            new getDataPlayer().execute();
        } else {
            tv_no_infor.setVisibility(View.VISIBLE);
            ll_infor.setVisibility(View.GONE);
        }

    }


    @Override
    protected void initListeners() {
    }

    public class getDataPlayer extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(url).get();
                Elements img = doc.select("div.player img");
                player_image = Utils.getImgLink_2(img.toString());
                Elements ul = doc.select("ul.mui-table-view.mui-table-view-striped.mui-table-view-condensed");
                Elements li = ul.select("li");

                for(int i = 0; i<4; i++){
                    Log.d("AAAA", li.get(i).text());
                    switch (i){
                        case 0:
                           player_name = li.get(i).text();
                            break;
                        case 1:
                            String a = li.get(i).text();
                            table1 = Utils.getTable1(a);
                            break;
                        case 2:
                            break;
                        case 3:
//                            String b = li.get(i).text();
//                            table2 = Utils.getTable2(b);
                            Elements p = li.get(i).select("p");
                            String b[] = new String[6];
                            for(int x=0; x <p.size(); x++){
                                b[x] = p.get(x).text();
                            }
                            table2 = Utils.getTable2(b);
                            break;
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showData();
                    }
                });
//
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(PlayerActivity.this);
            pd.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
        }
    }


    //position, age, height, weight, value, wage, club, position_club, overall, jersey_number, joined, contrac
    private void showData() {
        Glide.with(PlayerActivity.this).load(player_image).into(imgPlayer);
        name.setText(player_name);
        if(table1!= null) {
            position.setText(table1[0].toString());
            age.setText(table1[1].toString());
            height.setText(table1[2].toString());
            weight.setText(table1[3].toString());
            value.setText(table1[4].toString());
            wage.setText(table1[5].toString());
        }
        if(table2!= null) {
            club.setText(table2[0].toString());
            position_club.setText(table2[2].toString());
            overall.setText(table2[1].toString());
            jersey_number.setText(table2[3].toString());
            joined.setText(table2[4].toString());
            contrac.setText(table2[5].toString());
        }
    }

    private void changeColor(int newColor) {

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
}
