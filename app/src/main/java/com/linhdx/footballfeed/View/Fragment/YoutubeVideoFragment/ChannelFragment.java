package com.linhdx.footballfeed.View.Fragment.YoutubeVideoFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.linhdx.footballfeed.AppObjectNetWork.YoutubeWrap.YoutubeWrapper;
import com.linhdx.footballfeed.NetworkAPI.RetrofitApiInterface;
import com.linhdx.footballfeed.NetworkAPI.RetrofitBuilder;
import com.linhdx.footballfeed.NetworkAPI.WebServiceConfig;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.View.Activity.MainActivity;
import com.linhdx.footballfeed.View.Activity.YouTubePlayerActivity;
import com.linhdx.footballfeed.adapter.YoutubeVideoAdapter;
import com.linhdx.footballfeed.entity.YouTubeVideo;

import java.util.ArrayList;
import java.util.List;

import altplus.amazing.util.NetworkUtil;
import altplus.amazing.util.StringUtil;
import altplus.amazing.view.fragment.AmazingBaseFragment;
import altplus.amazing.view.widget.AmazingRecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/**
 * Created by shine on 06/05/2017.
 */

public class ChannelFragment extends AmazingBaseFragment<YouTubeVideo> {
    public static final int REQUEST_CODE_PLAY_VIDEO = 1;
    public String channelUrl;
    private AmazingRecyclerView mArcView;
    private YoutubeVideoAdapter mAdapter;
    private String mNextPageToken;
    private String channelName;
    private List<YouTubeVideo> listVideos;
    public RetrofitApiInterface retrofitApi;

    public static ChannelFragment newInstance(String channelName, String channelUrl) {
        ChannelFragment f = new ChannelFragment();
        Bundle args = new Bundle();
        args.putString(PARAM1, channelName);
        args.putString(PARAM2, channelUrl);
        f.setArguments(args);
        return f;
    }

    @Override
    public void refreshList(YouTubeVideo video) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_videos;
    }

    @Override
    protected void initViews(View view) {
        mArcView = (AmazingRecyclerView) view.findViewById(R.id.arcView);
        mArcView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData() {
        channelName = getArguments().getString(PARAM1);
        channelUrl = getArguments().getString(PARAM2);
        listVideos = new ArrayList<>();
        mAdapter = new YoutubeVideoAdapter(getMainActivity(), listVideos);
        mArcView.setAdapter(mAdapter);
        getListVideos(false);
    }

    @Override
    protected void initListeners() {
        mArcView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listVideos.clear();
                getListVideos(false);
                mArcView.refreshList();
            }
        });
        mArcView.setOnLoadMoreListener(new AmazingRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mAdapter.notifyItemChanged(listVideos.size() - 1);
                mArcView.recyclerViewActual.smoothScrollToPosition(listVideos.size());
                getListVideos(true);
            }
        });
        mAdapter.setOnItemClickListener(new YoutubeVideoAdapter.OnItemClickListener() {
            @Override
            public void onClickListener(int position) {
                playVideo(listVideos.get(position));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    private void getListVideos(final boolean isNext) {
        executeRetrofitApi(channelUrl, 20, isNext && !StringUtil.isEmpty(mNextPageToken) ? mNextPageToken : "");
    }

    private void executeRetrofitApi(final String url, int maxresult, final String nextPageToken) {
        retrofitApi = RetrofitBuilder.getRetrofit(WebServiceConfig.BASE_URL, null, 5000, 5000).create(RetrofitApiInterface.class);
            retrofitApi.getListVideos(url, maxresult, nextPageToken)
                    .enqueue(new Callback<YoutubeWrapper>() {
                        @Override
                        public void onResponse(Call<YoutubeWrapper> call, Response<YoutubeWrapper> response) {
                            mNextPageToken = response.body().nextPageToken;
                            for (YoutubeWrapper.Items item :
                                    response.body().items) {
                                YouTubeVideo youTubeVideo = new YouTubeVideo(item.id.videoId,
                                        item.snippet.title,
                                        item.snippet.thumbnails.medium.url,
                                        item.snippet.description,
                                        item.snippet.publishedAt,
                                        item.snippet.channelTitle,
                                        false);
                                listVideos.add(youTubeVideo);
                            }
                            mAdapter.notifyDataSetChanged();
                            mArcView.refreshList();
                        }

                        @Override
                        public void onFailure(Call<YoutubeWrapper> call, Throwable t) {
                            showToast(R.string.noNetwork);
                            mArcView.refreshList();
                        }
                    });
    }

    protected void showToast(int idString) {
        try {
            Toast.makeText(getActivity(), idString, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
    }

    protected void playVideo(YouTubeVideo video) {
        Intent intent = new Intent(getActivity(), YouTubePlayerActivity.class);
        intent.putExtra("youtube_video", video);
        getActivity().startActivityForResult(intent, REQUEST_CODE_PLAY_VIDEO);
    }
}
