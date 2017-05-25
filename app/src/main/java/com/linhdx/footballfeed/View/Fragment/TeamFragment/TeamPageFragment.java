package com.linhdx.footballfeed.View.Fragment.TeamFragment;


import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
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

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.google.gson.Gson;
import com.linhdx.footballfeed.AppConstant;
import com.linhdx.footballfeed.View.Activity.MainActivity;
import com.linhdx.footballfeed.View.Fragment.ArticlesFragment.ListArticleFragment;
import com.linhdx.footballfeed.entity.TeamStatus;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.glide.SvgDecoder;
import com.linhdx.footballfeed.glide.SvgDrawableTranscoder;
import com.linhdx.footballfeed.glide.SvgSoftwareLayerSetter;
import com.linhdx.footballfeed.utils.SharedPreferencesUtil;

import java.io.InputStream;

/**
 * Created by shine on 11/04/2017.
 */

public class TeamPageFragment extends Fragment {
    TextView teamNameTitle, teamName, shortTeamName;
    ImageView imBack, imIconTeam, imFavorite;
    RelativeLayout rlTeamPlayer, rlTeamCompetitions, rlTeamArticle, rlTeamVideo;
    TeamStatus teamStatus;
    boolean isFavorite  =false;
    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;
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
        imIconTeam = (ImageView)view.findViewById(R.id.im_team_page_icon);
        imBack = (ImageView)view.findViewById(R.id.im_team_page_back);
        imFavorite = (ImageView)view.findViewById(R.id.im_team_favorite);
        rlTeamPlayer =(RelativeLayout)view.findViewById(R.id.rl_view_team_player);
        rlTeamCompetitions =(RelativeLayout)view.findViewById(R.id.rl_view_team_competitions);
        rlTeamArticle =(RelativeLayout)view.findViewById(R.id.rl_view_team_article);
        rlTeamVideo =(RelativeLayout)view.findViewById(R.id.rl_view_team_video);

        requestBuilder = Glide.with(getActivity())
                .using(Glide.buildStreamModelLoader(Uri.class, getActivity()), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.ic_ball_place)
                .error(R.drawable.img_not_found)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());

        init(teamStatus);
        initListener(teamStatus);
    }

    private void init(TeamStatus teamStatus) {
        teamNameTitle.setText(teamStatus.getName());
        teamName.setText(teamStatus.getName());
        String name = SharedPreferencesUtil.getStringPreference(getActivity(), AppConstant.SP_MY_FAVORITE_CLUB);
        if(name != null && name.compareTo(teamStatus.getName())==0){
            imFavorite.setImageResource(R.drawable.ic_star);
            isFavorite =true;
        }

        shortTeamName.setText(teamStatus.getShortName().split("-")[0]);
        String urlImage = teamStatus.getImage();
        if (urlImage.contains(".svg")) {
            loadNet(urlImage, imIconTeam);
        } else {
            Glide.with(getActivity()).load(urlImage).into(imIconTeam);
        }

    }

    private void initListener(final TeamStatus teamStatus){
        imFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavorite = !isFavorite;
                if(isFavorite){
                    SharedPreferencesUtil.setStringPreference(getActivity(), AppConstant.SP_MY_FAVORITE_CLUB,teamStatus.getName());
                    imFavorite.setImageResource(R.drawable.ic_star);
                }else {
                    SharedPreferencesUtil.setStringPreference(getActivity(), AppConstant.SP_MY_FAVORITE_CLUB,"null");
                    imFavorite.setImageResource(R.drawable.ic_star_black_white);
                }
            }
        });

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

        rlTeamArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeArticleFragment(teamStatus);
            }
        });

        rlTeamVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeVideoFragment(teamStatus);
            }
        });

        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getFragmentManager().getBackStackEntryCount() >0){
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();
                }
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

    private void changeArticleFragment(TeamStatus teamStatus){
        SharedPreferencesUtil.setStringPreference(getActivity(), AppConstant.SP_CLUB_ARTICLE,teamStatus.getName());
        SharedPreferencesUtil.setBooleanPreference(getActivity(), AppConstant.SP_BOOLEAN_CLUB_ARTICLE, true);
        SharedPreferencesUtil.setBooleanPreference(getActivity(), AppConstant.SP_BACK, true);
        getMainActivity().changePager(5);
    }

    private void changeVideoFragment(TeamStatus teamStatus){
        SharedPreferencesUtil.setStringPreference(getActivity(), AppConstant.SP_CLUB_VIDEO, teamStatus.getName());
        SharedPreferencesUtil.setBooleanPreference(getActivity(), AppConstant.SP_BOOLEAN_CLUB_VIDEO, true);
        SharedPreferencesUtil.setBooleanPreference(getActivity(), AppConstant.SP_BACK, true);
        getMainActivity().changePager(4);
    }

    private void loadNet(String url, ImageView imageViewNet) {
        Uri uri = Uri.parse(url);
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(uri)
                .into(imageViewNet);
    }

    protected MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }
}
