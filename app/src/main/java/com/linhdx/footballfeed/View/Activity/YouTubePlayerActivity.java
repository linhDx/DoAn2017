package com.linhdx.footballfeed.View.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.linhdx.footballfeed.AppObjectNetWork.YoutubeWrap.YoutubeWrapper;
import com.linhdx.footballfeed.NetworkAPI.RetrofitApiInterface;
import com.linhdx.footballfeed.NetworkAPI.RetrofitBuilder;
import com.linhdx.footballfeed.NetworkAPI.WebServiceConfig;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.adapter.YoutubeVideoAdapter;
import com.linhdx.footballfeed.entity.YouTubeVideo;

import java.util.ArrayList;
import java.util.List;

import linhdx.amazing.view.widget.AmazingRecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shine on 06/05/2017.
 */

public class YouTubePlayerActivity extends YouTubeFailureRecoveryActivity implements YouTubePlayer.OnFullscreenListener {
    @SuppressLint("InlinedApi")
    private static final int PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9 ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            : ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;

    private YouTubePlayerView playerView;
    private ImageView mIvFavorite;
    private TextView mTvVideoName;
    private TextView mTvDescription;
    private View mLayoutBottom, mProgressBar;
    private AmazingRecyclerView mArcView;
    private YoutubeVideoAdapter mAdapter;
    private List<YouTubeVideo> mListVideos;

    private YouTubePlayer mPlayer;

    private YouTubeVideo mCurrentVideo;

    private boolean fullscreen;

    private RetrofitApiInterface retrofitApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);

        initData();
        initUI();
        initControl();
        getRelatedVideos();
    }
    private void initData() {
        Bundle bundle = getIntent().getExtras();
        mCurrentVideo = bundle.getParcelable("youtube_video");
        //Init Retrofit2
        retrofitApi = RetrofitBuilder.getRetrofit(WebServiceConfig.BASE_URL, null, 5000, 5000).create(RetrofitApiInterface.class);
    }

    private void initUI() {
        mIvFavorite = (ImageView) findViewById(R.id.ivFavorite);
        playerView = (YouTubePlayerView) findViewById(R.id.player);
        playerView.initialize("AIzaSyCboqBRA3mJ-BHBCaA7GgIUmoLjV-w3PWE", this);
        mTvVideoName = (TextView) findViewById(R.id.tvVideoName);
        mTvDescription = (TextView) findViewById(R.id.tvDescription);
        mLayoutBottom = findViewById(R.id.layoutBottom);
        mProgressBar = findViewById(R.id.progressBar);
        mArcView = (AmazingRecyclerView) findViewById(R.id.arcView);
    }

    private void initControl() {
        mTvVideoName.setText(mCurrentVideo.getTitle());
        mTvDescription.setText("Football Feed");
        mListVideos = new ArrayList<YouTubeVideo>();
        mAdapter = new YoutubeVideoAdapter(this, mListVideos);
        mAdapter.setOnItemClickListener(new YoutubeVideoAdapter.OnItemClickListener() {
            @Override
            public void onClickListener(int position) {
                mCurrentVideo = mListVideos.get(position);
                mPlayer.loadVideo(mCurrentVideo.getVideoId());
                mTvVideoName.setText(mCurrentVideo.getTitle());
                mTvDescription.setText(mCurrentVideo.getDescription());
                getRelatedVideos();
            }
        });
        mArcView.setLayoutManager(new LinearLayoutManager(this));
        mArcView.setAdapter(mAdapter);
        mArcView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRelatedVideos();
                mArcView.refreshList();
            }
        });
    }

    private void getRelatedVideos() {
            retrofitApi.getListRelateVideos(mCurrentVideo.getVideoId())
                    .enqueue(new Callback<YoutubeWrapper>() {
                        @Override
                        public void onResponse(Call<YoutubeWrapper> call, Response<YoutubeWrapper> response) {
//                            mNextPageToken = response.body().nextPageToken;
                            mListVideos.clear();
                            for (YoutubeWrapper.Items item :
                                    response.body().items) {
                                YouTubeVideo youTubeVideo = new YouTubeVideo(item.id.videoId,
                                        item.snippet.title,
                                        item.snippet.thumbnails.medium.url,
                                        item.snippet.description,
                                        item.snippet.publishedAt,
                                        item.snippet.channelTitle,
                                        false);
                                mListVideos.add(youTubeVideo);
//                                checkFavorite(youTubeVideo);
                            }
                            mAdapter.notifyDataSetChanged();
                            mArcView.refreshList();
                            mProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<YoutubeWrapper> call, Throwable t) {
                            showToast(R.string.noNetwork);
                            mArcView.refreshList();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    });
    }

    private void showToast(int idString) {
        Toast.makeText(this, idString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("refresh", true);
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onFullscreen(boolean isFullscreen) {
        fullscreen = isFullscreen;
        doLayout();
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return playerView;
    }

    private void doLayout() {
        LinearLayout.LayoutParams playerParams = (LinearLayout.LayoutParams) playerView.getLayoutParams();
        if (fullscreen || (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)) {
            playerParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
            playerParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
        } else {
            playerParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        player.setOnFullscreenListener(this);
        mPlayer = player;
        if (!wasRestored) {
            player.loadVideo(mCurrentVideo.getVideoId());
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        doLayout();
    }
}
