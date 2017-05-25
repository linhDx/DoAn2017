package com.linhdx.footballfeed.View.Fragment.YoutubeVideoFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.linhdx.footballfeed.AppConstant;
import com.linhdx.footballfeed.AppObjectNetWork.YoutubeWrap.YoutubeWrapper;
import com.linhdx.footballfeed.NetworkAPI.RetrofitApiInterface;
import com.linhdx.footballfeed.NetworkAPI.RetrofitBuilder;
import com.linhdx.footballfeed.NetworkAPI.WebServiceConfig;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.View.Activity.MainActivity;
import com.linhdx.footballfeed.View.Activity.YouTubePlayerActivity;
import com.linhdx.footballfeed.adapter.YoutubeVideoAdapter;
import com.linhdx.footballfeed.entity.TeamStatus;
import com.linhdx.footballfeed.entity.YouTubeVideo;
import com.linhdx.footballfeed.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import linhdx.amazing.util.StringUtil;
import linhdx.amazing.view.fragment.AmazingBaseFragment;
import linhdx.amazing.view.widget.AmazingRecyclerView;
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
    private List<YouTubeVideo> listVideos, listPreventions;
    public RetrofitApiInterface retrofitApi;
    private String matchVideo;
    private boolean isMatchVideo = false;
    private boolean isClubVideo = false;
    private String matchInfor[];
    private String clubVideo;

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
        listPreventions = new ArrayList<>();
        mAdapter = new YoutubeVideoAdapter(getMainActivity(), listVideos);
        mArcView.setAdapter(mAdapter);
        matchVideo = SharedPreferencesUtil.getStringPreference(getActivity(), AppConstant.SP_MATCH_VIDEO);
        clubVideo = SharedPreferencesUtil.getStringPreference(getActivity(), AppConstant.SP_CLUB_VIDEO);
        isClubVideo = SharedPreferencesUtil.getBooleanPreference(getActivity(), AppConstant.SP_BOOLEAN_CLUB_VIDEO, false);
        if (matchVideo != null && matchVideo.compareTo("none") != 0) {
            isMatchVideo = true;
            matchInfor = matchVideo.split("-");
            getListVideos(false, 30);
        } else if (clubVideo != null && clubVideo.compareTo("none") != 0) {
            List<TeamStatus> list;
            for (int i = 1; i < 5; i++) {
                list = TeamStatus.findWithQuery(TeamStatus.class, "Select * from TEAM_STATUS where FAMOUS = ?", String.valueOf(i));
                for (TeamStatus t : list
                        ) {
                    if (t.getName().compareTo(clubVideo) == 0) {
                        clubVideo = t.getShortName();
                        break;
                    }
                }
            }
            getListVideos(false, 50);
        } else {
            getListVideos(false, 20);
        }
    }

    @Override
    protected void initListeners() {
        mArcView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isClubVideo = false;
                listVideos.clear();
                getListVideos(false, 20);
                mArcView.refreshList();
            }
        });
        mArcView.setOnLoadMoreListener(new AmazingRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!isClubVideo) {
                    mAdapter.notifyItemChanged(listVideos.size() - 1);
                    mArcView.recyclerViewActual.smoothScrollToPosition(listVideos.size());
                    getListVideos(true, 20);
                }
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
        if (SharedPreferencesUtil.getBooleanPreference(getActivity(), AppConstant.SP_BOOLEAN_MATCH_VIDEO, false)) {
            SharedPreferencesUtil.setBooleanPreference(getActivity(), AppConstant.SP_BOOLEAN_MATCH_VIDEO, true);
            listVideos.clear();
            for (YouTubeVideo item : listPreventions
                    ) {
                listVideos.add(item);
            }
            mAdapter.notifyDataSetChanged();
            mArcView.refreshList();
        }
    }

    protected MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    private void getListVideos(final boolean isNext, int max) {
        executeRetrofitApi(channelUrl, max, isNext && !StringUtil.isEmpty(mNextPageToken) ? mNextPageToken : "");
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
                            if (youTubeVideo.getTitle().contains("2017")) {
                                listPreventions.add(youTubeVideo);
                            }
                            if (isMatchVideo) {
                                if (youTubeVideo.getTitle().contains(matchInfor[0])) {
                                    TeamStatus team = checkMatch(matchInfor[4], matchInfor[2], matchInfor[3]);
                                    if (team.getShortName() != null) {
                                        for (String name : team.getShortName().split("-")
                                                ) {
                                            if (youTubeVideo.getTitle().contains(name)) {
                                                listVideos.add(youTubeVideo);
                                                break;
                                            }
                                        }
                                    }
                                } else if (youTubeVideo.getTitle().contains(matchInfor[1])) {
                                    TeamStatus team = checkMatch(matchInfor[4], matchInfor[2], matchInfor[3]);
                                    if (team.getShortName() != null) {
                                        for (String name : team.getShortName().split("-")
                                                ) {
                                            if (youTubeVideo.getTitle().contains(name)) {
                                                listVideos.add(youTubeVideo);
                                                break;
                                            }
                                        }
                                    }
                                }
                            } else if (isClubVideo) {
                                for (String name : clubVideo.split("-")
                                        ) {
                                    if (youTubeVideo.getTitle().contains(name)) {
                                        listVideos.add(youTubeVideo);
                                    }
                                }
                            } else if(youTubeVideo.getTitle().contains("2017")){
                                listVideos.add(youTubeVideo);
                            }
                        }
                        if (listVideos.size() == 0) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("No video for this, click close to continue")
                                    .setCancelable(false)
                                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            // show it
                            alertDialog.show();
                            SharedPreferencesUtil.setStringPreference(getActivity(), AppConstant.SP_MATCH_VIDEO, "none");
                            for (YouTubeVideo item : listPreventions
                                    ) {
                                listVideos.add(item);
                            }
                        } else {
                            if (isMatchVideo) {
                                SharedPreferencesUtil.setStringPreference(getActivity(), AppConstant.SP_MATCH_VIDEO, "none");
                                playVideo(listVideos.get(0));
                            }
                        }
                        isMatchVideo = false;
                        if (SharedPreferencesUtil.getBooleanPreference(getActivity(), AppConstant.SP_BOOLEAN_CLUB_VIDEO, false)) {
                            SharedPreferencesUtil.setBooleanPreference(getActivity(), AppConstant.SP_BOOLEAN_CLUB_VIDEO, false);
//                            String teamName = SharedPreferencesUtil.getStringPreference(getActivity(), AppConstant.SP_CLUB_VIDEO);
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

    private TeamStatus checkMatch(String league, String name1, String name2) {
        TeamStatus result = new TeamStatus();
        List<TeamStatus> list;
        switch (league) {
            case "1":
                list = TeamStatus.findWithQuery(TeamStatus.class, "Select * from TEAM_STATUS where FAMOUS = ?", "1");
                break;
            case "2":
                list = TeamStatus.findWithQuery(TeamStatus.class, "Select * from TEAM_STATUS where FAMOUS = ?", "2");
                break;
            case "3":
                list = TeamStatus.findWithQuery(TeamStatus.class, "Select * from TEAM_STATUS where FAMOUS = ?", "3");
                break;
            case "4":
                list = TeamStatus.findWithQuery(TeamStatus.class, "Select * from TEAM_STATUS where FAMOUS = ?", "4");
                break;
            default:
                list = TeamStatus.listAll(TeamStatus.class);
        }

        for (TeamStatus item : list
                ) {
            String a[] = item.getShortName().split("-");
            for (String name: a
                 ) {
                if (name.compareTo(name1) == 0) {
                    result = item;
                    break;
                } else if (name.compareTo(name2) == 0) {
                    result = item;
                    break;
                }
            }

        }
        return result;
    }


}
