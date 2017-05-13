package com.linhdx.footballfeed.View.Fragment.ArticlesFragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.linhdx.footballfeed.AppConstant;
import com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork.Article_DanTri;
import com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork.RssFeed_DanTri;
import com.linhdx.footballfeed.View.Activity.MainActivity;
import com.linhdx.footballfeed.adapter.CustomListViewListArticle;
import com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork.Article_BDC;
import com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork.RssFeed_BDC;
import com.linhdx.footballfeed.NetworkAPI.RssService;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.entity.Article;
import com.linhdx.footballfeed.entity.TeamStatus;
import com.linhdx.footballfeed.utils.ArticleUtils;
import com.linhdx.footballfeed.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import altplus.amazing.view.widget.AmazingRecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shine on 19/04/2017.
 */
// hien thi cac bai bao cho giai dau
public class ListArticleFragment extends Fragment {
    private List<Article> list, showList, listClub;
    private int count = 1;
    private AmazingRecyclerView lv;
    private CustomListViewListArticle mAdapter;
    private String league;
    private boolean isDT = true;
    private Spinner mSpinner;
    private ImageView imgMenu;
    private LinearLayout mFilter, mLLSpinner;
    private boolean isMenuOpen = false;
    private RadioGroup radioGroup;
    private List<TeamStatus> listTeam;
    private boolean isPubDate = true;

    public ListArticleFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getString("league") != null) {
            league = bundle.getString("league");
        } else {
            league = "error";
        }
        return inflater.inflate(R.layout.list_article_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (AmazingRecyclerView) view.findViewById(R.id.lv_list_article);
        lv.setLayoutManager(new LinearLayoutManager(getActivity()));
        imgMenu = (ImageView) view.findViewById(R.id.img_view);
        mFilter = (LinearLayout) view.findViewById(R.id.ll_filter);
        mLLSpinner = (LinearLayout) view.findViewById(R.id.ll_spinner);
        mSpinner = (Spinner) view.findViewById(R.id.spn_club);
        radioGroup = (RadioGroup) view.findViewById(R.id.my_radio_group);

        list = new ArrayList<>();
        showList = new ArrayList<>();
        listClub = new ArrayList<>();

        mAdapter = new CustomListViewListArticle(getMainActivity(), showList);
        lv.setAdapter(mAdapter);
        initData();
        initListener();
    }

    private void initData() {

        if (league.compareTo("error") != 0) {
            switch (league) {
                case "1":
                    new getListArtcle().execute(AppConstant.LIST_ARTICLE_PL);
                    setupSpiner(1);
                    break;
                case "2":
                    new getListArtcle().execute(AppConstant.LIST_ARTICLE_PD);
                    setupSpiner(2);
                    break;
                case "3":
                    isDT = false;
                    new getListArtcle().execute(AppConstant.LIST_ARTICLE_BL);
                    setupSpiner(3);
                    break;
                case "4":
                    isDT = false;
                    new getListArtcle().execute(AppConstant.LIST_ARTICLE_SA);
                    setupSpiner(4);
                    break;
            }
        }
    }

    private void initListener() {
        lv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isPubDate) {
                    showList.clear();
                    showListArticle(list, 1);
                    lv.refreshList();
                } else {
                    lv.refreshList();
                }
            }
        });
        lv.setOnLoadMoreListener(new AmazingRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (isPubDate) {
                    if (count < 4) {
                        count = count + 1;
                    }
                    mAdapter.notifyItemChanged(list.size() - 1);
                    lv.recyclerViewActual.smoothScrollToPosition(list.size());
                    showListArticle(list, count);
                }
            }
        });
        mAdapter.setOnItemClickListener(new CustomListViewListArticle.OnItemClickListener() {
            @Override
            public void onClickListener(int position) {
                // chuyen trang xem
                Fragment a = PageArticleFragment.newInstance(showList.get(position).getWebName(), showList.get(position).getLink());
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.add(R.id.article_container, a).addToBackStack(null).commit();
            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected_val = mSpinner.getSelectedItem().toString();
                if (position != 0) {
                    new GetListArticleClub().execute(selected_val);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMenuOpen = !isMenuOpen;
                if (isMenuOpen) {
                    mFilter.setVisibility(View.VISIBLE);
                } else {
                    mFilter.setVisibility(View.GONE);
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_pubDate) {
                    isPubDate = true;
                    showListArticle(list, 1);
                    mLLSpinner.setVisibility(View.GONE);
                } else if (checkedId == R.id.rd_club) {
                    isPubDate = false;
                    mLLSpinner.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public class getListArtcle extends AsyncTask<String, Void, Void> {
        ProgressDialog pd;

        @Override
        protected Void doInBackground(String... params) {
            String fb247, fbdt, fb24h, fbbdc;
            fb247 = params[0];
            fbdt = params[1];
            fb24h = params[2];
            fbbdc = params[3];

            if (fbdt.compareTo("error") != 0) {
                getListArticle_DT(fbdt);
            } else {
                isDT = false;
            }
            getListArticle_247(fb247);
            getListArticle_24h_1(fb24h);
            getListArticle_BDC(fbbdc);

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
        }
    }

    public void getListArticle_BDC(String league) {
        RssService rssService = RssService.retrofit_BDC.create(RssService.class);
        Call<RssFeed_BDC> call = rssService.getXml_BDC(league);
        call.enqueue(new Callback<RssFeed_BDC>() {
            @Override
            public void onResponse(Call<RssFeed_BDC> call, Response<RssFeed_BDC> response) {
                for (Article_BDC item : response.body().getArticleBDCList()
                        ) {
                    Article a = new Article(item.getLink(), item.getTitle().trim(),
                            item.getDescription(), item.getImage(), Utils.StringToDate_BDC(item.getPubDate()), AppConstant.LIST_WEBNAME[0]);
                    list.add(a);
                    Log.d("AAAA", "A" + a.getTitlte());
                }
                if (isDT) {
                    if (list.size() == 75) {
                        sortList(list);
                        showListArticle(list, count);
                    }
                } else {
                    if (list.size() == 65) {
                        sortList(list);
                        showListArticle(list, count);
                    }
                }
            }

            @Override
            public void onFailure(Call<RssFeed_BDC> call, Throwable t) {
            }
        });
    }

    public void getListArticle_24h_1(String league) {
        RssService rssService = RssService.retrofit_bongda24h.create(RssService.class);
        Call<RssFeed_DanTri> call = rssService.getXml_24h(league);
        call.enqueue(new Callback<RssFeed_DanTri>() {
            @Override
            public void onResponse(Call<RssFeed_DanTri> call, Response<RssFeed_DanTri> response) {
                for (Article_DanTri item : response.body().getArticleDTList()
                        ) {
                    Article a = new Article(item.getLink(), item.getTitle(),
                            item.getDescription(), "", Utils.StringToDate_24h(item.getPubDate()), AppConstant.LIST_WEBNAME[1]);
                    list.add(a);
                }
                Log.d("AAAA", list.size() + "B");
                if (isDT) {
                    if (list.size() == 75) {
                        sortList(list);
                        showListArticle(list, count);
                    }
                } else {
                    if (list.size() == 65) {
                        sortList(list);
                        showListArticle(list, count);
                    }
                }
            }

            @Override
            public void onFailure(Call<RssFeed_DanTri> call, Throwable t) {
            }
        });
    }

    public void getListArticle_247(String league) {
        RssService rssService = RssService.retrofit_thethao247.create(RssService.class);
        Call<RssFeed_BDC> call = rssService.getXml_247(league);
        call.enqueue(new Callback<RssFeed_BDC>() {
            @Override
            public void onResponse(Call<RssFeed_BDC> call, Response<RssFeed_BDC> response) {
                for (Article_BDC item : response.body().getArticleBDCList()
                        ) {
                    Article a = new Article(item.getLink(), item.getTitle(),
                            item.getDescription(), item.getImage(), Utils.StringToDate(item.getPubDate()), AppConstant.LIST_WEBNAME[3]);
                    list.add(a);
                }
                Log.d("AAAA", list.size() + "C");
                if (isDT) {
                    if (list.size() == 75) {
                        sortList(list);
                        showListArticle(list, count);
                    }
                } else {
                    if (list.size() == 65) {
                        sortList(list);
                        showListArticle(list, count);
                    }
                }
            }

            @Override
            public void onFailure(Call<RssFeed_BDC> call, Throwable t) {
            }
        });
    }

    public void getListArticle_DT(String league) {
        RssService rssService = RssService.retrofit_dantri.create(RssService.class);
        Call<RssFeed_DanTri> call = rssService.getXml_DT(league);
        call.enqueue(new Callback<RssFeed_DanTri>() {
            @Override
            public void onResponse(Call<RssFeed_DanTri> call, Response<RssFeed_DanTri> response) {
                for (Article_DanTri item : response.body().getArticleDTList()
                        ) {
                    Article a = new Article(item.getLink(), item.getTitle(),
                            item.getDescription(), Utils.getImgLink(item.getDescription()), Utils.StringToDate(item.getPubDate()), AppConstant.LIST_WEBNAME[2]);
                    list.add(a);
                    Log.d("AAAA", item.getPubDate());
                }
                Log.d("AAAA", list.size() + "D");
                if (isDT) {
                    if (list.size() == 75) {
                        sortList(list);
                        showListArticle(list, count);
                    }
                } else {
                    if (list.size() == 65) {
                        sortList(list);
                        showListArticle(list, count);
                    }
                }
            }

            @Override
            public void onFailure(Call<RssFeed_DanTri> call, Throwable t) {
            }
        });
    }

    public void sortList(List<Article> lt) {
        Collections.sort(lt, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                if (o1.getPubDate() != null && o2.getPubDate() != null) {
                    return o1.getPubDate().compareTo(o2.getPubDate());
                } else return 2;
            }
        });
        Collections.reverse(lt);
    }

    public void showListArticle(List<Article> list, int count) {
        switch (count) {
            case 1:
                showList.clear();
                for (int i = 0; i < 20; i++) {
                    showList.add(list.get(i));
                }
                mAdapter.notifyDataSetChanged();
                lv.refreshList();
                break;
            case 2:
                showList.clear();
                for (int i = 0; i < 40; i++) {
                    showList.add(list.get(i));
                }
                mAdapter.notifyDataSetChanged();
                lv.refreshList();
                break;
            case 3:
                showList.clear();
                for (int i = 0; i < 60; i++) {
                    showList.add(list.get(i));
                }
                mAdapter.notifyDataSetChanged();
                lv.refreshList();
                break;
            case 4:
                showList.clear();
                for (int i = 0; i < list.size(); i++) {
                    showList.add(list.get(i));
                }
                mAdapter.notifyDataSetChanged();
                lv.refreshList();
                break;
            default:
                showList.clear();
                for (int i = 0; i < list.size(); i++) {
                    showList.add(list.get(i));
                }
                mAdapter.notifyDataSetChanged();
                lv.refreshList();
                break;
        }
    }

    protected MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    private void setupSpiner(int id) {
        ArrayAdapter<CharSequence> adapter;
        switch (id) {
            case 1:
                adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Premier_league, android.R.layout.simple_spinner_item);
                break;
            case 2:
                adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Premier_division, android.R.layout.simple_spinner_item);
                break;
            case 3:
                adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Buldesliga, android.R.layout.simple_spinner_item);
                break;
            case 4:
                adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Serie_A, android.R.layout.simple_spinner_item);
                break;
            default:
                adapter = ArrayAdapter.createFromResource(getActivity(), R.array.none, android.R.layout.simple_spinner_item);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(0);
    }

    public class GetListArticleClub extends AsyncTask<String, Void, Void> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            String club;
            club = params[0];
            List<TeamStatus> listTeam;
            switch (league) {
                case "1":
                    listTeam = TeamStatus.findWithQuery(TeamStatus.class, "Select * from TEAM_STATUS where FAMOUS = ?", "1");
                    break;
                case "2":
                    listTeam = TeamStatus.findWithQuery(TeamStatus.class, "Select * from TEAM_STATUS where FAMOUS = ?", "2");
                    break;
                case "3":
                    listTeam = TeamStatus.findWithQuery(TeamStatus.class, "Select * from TEAM_STATUS where FAMOUS = ?", "3");
                    break;
                case "4":
                    listTeam = TeamStatus.findWithQuery(TeamStatus.class, "Select * from TEAM_STATUS where FAMOUS = ?", "4");
                    break;
                default:
                    listTeam = TeamStatus.listAll(TeamStatus.class);
            }

            TeamStatus clubStar = getStarClub(listTeam, club);
            String shortName[] = clubStar.getShortName().split("-");
            showList.clear();
            for (Article item : list
                    ) {
                for (String name : shortName
                        ) {
                    if (item.getTitlte().toLowerCase().contains(name.toLowerCase())) {
                        showList.add(item);
                    }
                }
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                    lv.refreshList();
                    isMenuOpen = !isMenuOpen;
                    mFilter.setVisibility(View.GONE);
                }
            });


//            Log.d("AAAA", clubStar.getName() + "AAAA");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();

        }
    }

    private TeamStatus getStarClub(List<TeamStatus> listTS, String name) {
        TeamStatus club = new TeamStatus();
        for (TeamStatus t : listTS
                ) {
            if (t.getName().compareTo(name) == 0) {
                club = t;
//                Log.d("AAAA", club.getName());
                break;
            }
        }
        return club;
    }
}
