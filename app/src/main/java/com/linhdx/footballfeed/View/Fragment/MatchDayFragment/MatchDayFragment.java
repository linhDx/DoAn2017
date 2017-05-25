package com.linhdx.footballfeed.View.Fragment.MatchDayFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.linhdx.footballfeed.AppConstant;
import com.linhdx.footballfeed.View.Activity.MyReceiver;
import com.linhdx.footballfeed.adapter.CustomListViewMatchDayAdapter;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork.NextMatchNetWorkStatus;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork.NextMatchNetWorkWrapper;
import com.linhdx.footballfeed.adapter.CustomListViewMatchResultAdapter;
import com.linhdx.footballfeed.entity.NextMatchStatus;
import com.linhdx.footballfeed.NetworkAPI.DataService;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.View.CustomView.TabInfor;
import com.linhdx.footballfeed.utils.SharedPreferencesUtil;
import com.linhdx.footballfeed.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by shine on 07/04/2017.
 */

public class MatchDayFragment extends Fragment {
    TabInfor tabInfor_PL, tabInfor_PD, tabInfor_SA, tabInfor_BL;
    DataService dataService;
    private static List<NextMatchStatus> listnextMatch_PL, listnextMatch_PD, listnextMatch_BL, listnextMatch_SA;
    CustomListViewMatchDayAdapter adapter1, adapter2, adapter3, adapter4;

    public MatchDayFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.matchday_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(listnextMatch_PL == null) {
            listnextMatch_BL = new ArrayList<>();
            listnextMatch_PD = new ArrayList<>();
            listnextMatch_PL = new ArrayList<>();
            listnextMatch_SA = new ArrayList<>();
        }

        tabInfor_PL = (TabInfor) view.findViewById(R.id.tabs_PL);
        tabInfor_BL = (TabInfor) view.findViewById(R.id.tabs_BL);
        tabInfor_PD = (TabInfor) view.findViewById(R.id.tabs_PD);
        tabInfor_SA = (TabInfor) view.findViewById(R.id.tabs_SA);

        dataService = DataService.retrofit.create(DataService.class);
//        new getData().execute();
//
        if(listnextMatch_PL.size() == 0 && listnextMatch_PD.size() == 0 &&
                listnextMatch_BL.size() == 0 &&listnextMatch_SA.size() == 0) {
            OnRequestNextMatch();
        } else {
            onBack();
        }
//
    }

    private void OnRequestNextMatch() {
        Call<NextMatchNetWorkWrapper> call = dataService.getNextMatchStatusesPL(426);
        call.enqueue(new Callback<NextMatchNetWorkWrapper>() {
            @Override
            public void onResponse(Call<NextMatchNetWorkWrapper> call, Response<NextMatchNetWorkWrapper> response) {
                int count = 0;
                if(response.body()!= null) {
                    count = response.body().getCount();
                    if (count > 0) {
                        tabInfor_PL.setVisibility(View.VISIBLE);
                        for (NextMatchNetWorkStatus item : response.body().getNextMatchStatusesPL()
                                ) {
                            NextMatchStatus nm = new NextMatchStatus(Utils.convertTime(item.getDate())[0],
                                    Utils.convertTime(item.getDate())[1],
                                    item.getHomeTeamName(),
                                    item.getAwayTeamName(),
                                    item.getResult().getGoalsHomeTeam(),
                                    item.getResult().getGoalsAwayTeam(),
                                    item.getStatus());
                            listnextMatch_PL.add(nm);
                        }
                        adapter1 = new CustomListViewMatchDayAdapter(getActivity(), listnextMatch_PL, MatchDayFragment.this);
                        tabInfor_PL.getmTabListView().setAdapter(adapter1);
                        Utils.setListViewHeightBasedOnItems(tabInfor_PL.getmTabListView());
                    }
                } else {
                    Log.d("AAA", "khong co tran nao 1");
                }
            }

            @Override
            public void onFailure(Call<NextMatchNetWorkWrapper> call, Throwable t) {
                new getData().execute();
            }
        });
        Call<NextMatchNetWorkWrapper> call_2 = dataService.getNextMatchStatusesPL(436);
        call_2.enqueue(new Callback<NextMatchNetWorkWrapper>() {
            @Override
            public void onResponse(Call<NextMatchNetWorkWrapper> call, Response<NextMatchNetWorkWrapper> response) {
                int count = 0;
                if(response.body()!= null) {
                    count = response.body().getCount();
                    if (count > 0) {
                        tabInfor_PD.setVisibility(View.VISIBLE);
                        tabInfor_PD.setmTabTitle("Premier Division");
                        for (NextMatchNetWorkStatus item : response.body().getNextMatchStatusesPL()
                                ) {
                            NextMatchStatus nm = new NextMatchStatus(Utils.convertTime(item.getDate())[0],
                                    Utils.convertTime(item.getDate())[1],
                                    item.getHomeTeamName(),
                                    item.getAwayTeamName(),
                                    item.getResult().getGoalsHomeTeam(),
                                    item.getResult().getGoalsAwayTeam(),
                                    item.getStatus());
                            listnextMatch_PD.add(nm);
                        }
                        adapter2 = new CustomListViewMatchDayAdapter(getActivity(), listnextMatch_PD, MatchDayFragment.this);
                        tabInfor_PD.getmTabListView().setAdapter(adapter2);
                        Utils.setListViewHeightBasedOnItems(tabInfor_PD.getmTabListView());
                    }
                } else {
                    Log.d("AAA", "khong co tran nao 2");
                }
            }

            @Override
            public void onFailure(Call<NextMatchNetWorkWrapper> call, Throwable t) {

            }
        });

        Call<NextMatchNetWorkWrapper> call_3 = dataService.getNextMatchStatusesPL(430);
        call_3.enqueue(new Callback<NextMatchNetWorkWrapper>() {
            @Override
            public void onResponse(Call<NextMatchNetWorkWrapper> call, Response<NextMatchNetWorkWrapper> response) {
                int count = 0;
                if(response.body()!= null) {
                    count = response.body().getCount();
                    if (count > 0) {
                        tabInfor_BL.setVisibility(View.VISIBLE);
                        tabInfor_BL.setmTabTitle("Buldesliga");
                        for (NextMatchNetWorkStatus item : response.body().getNextMatchStatusesPL()
                                ) {
                            NextMatchStatus nm = new NextMatchStatus(Utils.convertTime(item.getDate())[0],
                                    Utils.convertTime(item.getDate())[1],
                                    item.getHomeTeamName(),
                                    item.getAwayTeamName(),
                                    item.getResult().getGoalsHomeTeam(),
                                    item.getResult().getGoalsAwayTeam(),
                                    item.getStatus());
                            listnextMatch_BL.add(nm);
                        }
                        adapter3 = new CustomListViewMatchDayAdapter(getActivity(), listnextMatch_BL, MatchDayFragment.this);
                        tabInfor_BL.getmTabListView().setAdapter(adapter3);
                        Utils.setListViewHeightBasedOnItems(tabInfor_BL.getmTabListView());
                    }
                }
                else {
                    Log.d("AAA", "khong co tran nao 3");
                }
            }

            @Override
            public void onFailure(Call<NextMatchNetWorkWrapper> call, Throwable t) {

            }
        });

        Call<NextMatchNetWorkWrapper> call_4 = dataService.getNextMatchStatusesPL(438);
        call_4.enqueue(new Callback<NextMatchNetWorkWrapper>() {
            @Override
            public void onResponse(Call<NextMatchNetWorkWrapper> call, Response<NextMatchNetWorkWrapper> response) {
                int count = 0;
                if(response.body()!= null) {
                    count = response.body().getCount();
                    if (count > 0) {
                        tabInfor_SA.setVisibility(View.VISIBLE);
                        tabInfor_SA.setmTabTitle("Serie A");
                        for (NextMatchNetWorkStatus item : response.body().getNextMatchStatusesPL()
                                ) {
                            NextMatchStatus nm = new NextMatchStatus(Utils.convertTime(item.getDate())[0],
                                    Utils.convertTime(item.getDate())[1],
                                    item.getHomeTeamName(),
                                    item.getAwayTeamName(),
                                    item.getResult().getGoalsHomeTeam(),
                                    item.getResult().getGoalsAwayTeam(),
                                    item.getStatus());
                            listnextMatch_SA.add(nm);
                        }
                        adapter4 = new CustomListViewMatchDayAdapter(getActivity(), listnextMatch_SA, MatchDayFragment.this);
                        tabInfor_SA.getmTabListView().setAdapter(adapter4);
                        Utils.setListViewHeightBasedOnItems(tabInfor_SA.getmTabListView());
                    }
                }
                 else {
                    OnRequestNextMatch();
                    Log.d("AAA", "khong co tran nao 4");
                }
            }

            @Override
            public void onFailure(Call<NextMatchNetWorkWrapper> call, Throwable t) {
                new getData().execute();
            }
        });
    }

    public class getData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect("http://www.24h.com.vn/lich-thi-dau-bong-da/lich-thi-dau-bong-da-hom-nay-c287a364371.html").get();
                String leagueName = "";
                for (Element table : doc.select("table.tbl-ds-hot")) {
                    for (Element row : table.select("tr")) {
                        Elements tds = row.select("td");
                        if (tds.size() == 1) {
                            leagueName = tds.get(0).text();
                        }
                        if (tds.size() > 5) {
                            String infor = tds.get(0).text() + "-" + tds.get(1).text() + "-" + tds.get(2).text() + "-" + tds.get(3).text();
                            String[] match = Utils.matchInforXml(infor);
                            NextMatchStatus nm = new NextMatchStatus(match[0], match[1], match[2], match[5], match[3], match[4], "");
                            switch (leagueName) {
                                case "Premier League":
                                    listnextMatch_PL.add(nm);
                                    break;
                                case "Primera División":
                                    listnextMatch_PD.add(nm);
                                    break;
                                case "Serie A":
                                    listnextMatch_SA.add(nm);
                                    break;
                                case "Bundesliga":
                                    listnextMatch_BL.add(nm);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }

                /// process here
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showData();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Network has a problem", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        private void showData() {
            if (listnextMatch_PL.size() > 0) {
                tabInfor_PL.setVisibility(View.VISIBLE);
                tabInfor_PL.setmTabTitle("Premier League");

                adapter1 = new CustomListViewMatchDayAdapter(getActivity(), listnextMatch_PL, MatchDayFragment.this);
                tabInfor_PL.getmTabListView().setAdapter(adapter1);
                Utils.setListViewHeightBasedOnItems(tabInfor_PL.getmTabListView());
            }
            if (listnextMatch_PD.size() > 0) {
                tabInfor_PD.setVisibility(View.VISIBLE);
                tabInfor_PD.setmTabTitle("Premier Division");

                adapter2 = new CustomListViewMatchDayAdapter(getActivity(), listnextMatch_PD, MatchDayFragment.this);
                tabInfor_PD.getmTabListView().setAdapter(adapter2);
                Utils.setListViewHeightBasedOnItems(tabInfor_PD.getmTabListView());
            }
            if (listnextMatch_BL.size() > 0) {
                tabInfor_BL.setVisibility(View.VISIBLE);
                tabInfor_BL.setmTabTitle("Buldesliga");
                adapter3 = new CustomListViewMatchDayAdapter(getActivity(), listnextMatch_BL, MatchDayFragment.this);
                tabInfor_BL.getmTabListView().setAdapter(adapter3);
                Utils.setListViewHeightBasedOnItems(tabInfor_BL.getmTabListView());
            }
            if (listnextMatch_SA.size() > 0) {
                tabInfor_SA.setVisibility(View.VISIBLE);
                tabInfor_SA.setmTabTitle("Serie A");
                adapter4 = new CustomListViewMatchDayAdapter(getActivity(), listnextMatch_SA, MatchDayFragment.this);
                tabInfor_SA.getmTabListView().setAdapter(adapter4);
                Utils.setListViewHeightBasedOnItems(tabInfor_SA.getmTabListView());
            }
        }
    }

    public void refeshData() {
        if (listnextMatch_PL.size() > 0) {
            adapter1.notifyDataSetChanged();
        }
        if (listnextMatch_PD.size() > 0) {
            adapter2.notifyDataSetChanged();
        }
        if (listnextMatch_BL.size() > 0) {
            adapter3.notifyDataSetChanged();
        }
        if (listnextMatch_SA.size() > 0) {
            adapter4.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        OnRequestNextMatch();
    }

    private void onBack(){
        if(listnextMatch_PL.size() >0) {
            tabInfor_PL.setVisibility(View.VISIBLE);
            tabInfor_PL.getmTabListView().setAdapter(adapter1);
            Utils.setListViewHeightBasedOnItems(tabInfor_PL.getmTabListView());
        }
        if(listnextMatch_PD.size() >0) {
            tabInfor_PD.setVisibility(View.VISIBLE);
            tabInfor_PD.getmTabListView().setAdapter(adapter2);
            Utils.setListViewHeightBasedOnItems(tabInfor_PD.getmTabListView());
        }
        if(listnextMatch_BL.size() >0) {
            tabInfor_BL.setVisibility(View.VISIBLE);

            tabInfor_BL.getmTabListView().setAdapter(adapter3);
            Utils.setListViewHeightBasedOnItems(tabInfor_BL.getmTabListView());
        }
        if(listnextMatch_SA.size() >0) {
            tabInfor_SA.setVisibility(View.VISIBLE);
            tabInfor_SA.getmTabListView().setAdapter(adapter4);
            Utils.setListViewHeightBasedOnItems(tabInfor_SA.getmTabListView());
        }

    }
}
