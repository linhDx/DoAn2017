package com.linhdx.footballfeed.View.Fragment.MatchResultFragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.linhdx.footballfeed.AppConstant;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork.MatchResultNetworkWrapper;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork.NextMatchNetWorkStatus;
import com.linhdx.footballfeed.NetworkAPI.DataService;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.View.Activity.MainActivity;
import com.linhdx.footballfeed.View.CustomView.TabInfor;
import com.linhdx.footballfeed.adapter.CustomListViewCompetitionsAdapter;
import com.linhdx.footballfeed.adapter.CustomListViewMatchResultAdapter;
import com.linhdx.footballfeed.entity.Competitions;
import com.linhdx.footballfeed.entity.TeamStatus;
import com.linhdx.footballfeed.utils.SharedPreferencesUtil;
import com.linhdx.footballfeed.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shine on 18/05/2017.
 */

public class MatchResultFragment extends Fragment {
    TabInfor tabInfor_PL, tabInfor_PD, tabInfor_SA, tabInfor_BL;
    DataService dataService;
    private static List<Competitions> listnextMatch_PL, listnextMatch_PD, listnextMatch_BL, listnextMatch_SA;
    ProgressDialog pd;
    public MatchResultFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.match_result_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(listnextMatch_PL == null){
            listnextMatch_PL = new ArrayList<>();
            listnextMatch_PD = new ArrayList<>();
            listnextMatch_BL = new ArrayList<>();
            listnextMatch_SA = new ArrayList<>();
        }

        tabInfor_PL = (TabInfor) view.findViewById(R.id.tabs_PL);
        tabInfor_BL = (TabInfor) view.findViewById(R.id.tabs_BL);
        tabInfor_PD = (TabInfor) view.findViewById(R.id.tabs_PD);
        tabInfor_SA = (TabInfor) view.findViewById(R.id.tabs_SA);

        dataService = DataService.retrofit.create(DataService.class);

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
        Call<MatchResultNetworkWrapper> call = dataService.getMatchResult(426);
        call.enqueue(new Callback<MatchResultNetworkWrapper>() {
            @Override
            public void onResponse(Call<MatchResultNetworkWrapper> call, Response<MatchResultNetworkWrapper> response) {
                int count = 0;
                if (response.body() != null) {
                    count = response.body().getCount();
                    if (count > 0) {
                        tabInfor_PL.setVisibility(View.VISIBLE);
                        for (NextMatchNetWorkStatus item : response.body().getMatchResult()
                                ) {
                            Competitions nm = new Competitions(Utils.convertTime(item.getDate())[0],
                                    Utils.convertTime(item.getDate())[1],
                                    item.getHomeTeamName(),
                                    item.getAwayTeamName(),
                                    item.getResult().getGoalsHomeTeam(),
                                    item.getResult().getGoalsAwayTeam(),
                                    item.getStatus(), new TeamStatus());
                            listnextMatch_PL.add(nm);
                        }
                        Collections.reverse(listnextMatch_PL);
                        CustomListViewMatchResultAdapter adapter = new CustomListViewMatchResultAdapter(getActivity(), listnextMatch_PL);
                        adapter.setOnItemClickListener(new CustomListViewMatchResultAdapter.OnItemClickListener() {
                            @Override
                            public void onClickListener(int position) {

                                SharedPreferencesUtil.setBooleanPreference(getActivity(), AppConstant.SP_BOOLEAN_MATCH_VIDEO, true);
                                SharedPreferencesUtil.setStringPreference(getActivity(), AppConstant.SP_MATCH_VIDEO, Utils.getmatchInfor(listnextMatch_PL.get(position)) + "-1");
                                getMainActivity().changePager(4);
                            }
                        });
                        tabInfor_PL.getmTabListView().setAdapter(adapter);
                        Utils.setListViewHeightBasedOnItems(tabInfor_PL.getmTabListView());
                    } else {
                        Log.d("AAA", "khong co tran nao 1");
                    }
                }
            }

            @Override
            public void onFailure(Call<MatchResultNetworkWrapper> call, Throwable t) {
                new getData_PL().execute();
                new getData_PD().execute();
                new getData_BL().execute();
                new getData_SA().execute();
            }
        });
        Call<MatchResultNetworkWrapper> call_2 = dataService.getMatchResult(436);
        call_2.enqueue(new Callback<MatchResultNetworkWrapper>() {
            @Override
            public void onResponse(Call<MatchResultNetworkWrapper> call, Response<MatchResultNetworkWrapper> response) {
                int count = 0;
                if (response.body() != null) {
                    count = response.body().getCount();
                    if (count > 0) {
                        tabInfor_PD.setVisibility(View.VISIBLE);
                        tabInfor_PD.setmTabTitle("Premier Division");
                        for (NextMatchNetWorkStatus item : response.body().getMatchResult()
                                ) {
                            Competitions nm = new Competitions(Utils.convertTime(item.getDate())[0],
                                    Utils.convertTime(item.getDate())[1],
                                    item.getHomeTeamName(),
                                    item.getAwayTeamName(),
                                    item.getResult().getGoalsHomeTeam(),
                                    item.getResult().getGoalsAwayTeam(),
                                    item.getStatus(), new TeamStatus());
                            listnextMatch_PD.add(nm);
                        }
                        CustomListViewMatchResultAdapter adapter = new CustomListViewMatchResultAdapter(getActivity(), listnextMatch_PD);
                        adapter.setOnItemClickListener(new CustomListViewMatchResultAdapter.OnItemClickListener() {
                            @Override
                            public void onClickListener(int position) {
//                            Toast.makeText(getActivity(), "HAHAHAHA" + position, Toast.LENGTH_SHORT).show();
                                SharedPreferencesUtil.setBooleanPreference(getActivity(), AppConstant.SP_BOOLEAN_MATCH_VIDEO, true);
                                SharedPreferencesUtil.setStringPreference(getActivity(), AppConstant.SP_MATCH_VIDEO, Utils.getmatchInfor(listnextMatch_PD.get(position)) + "-2");
                                getMainActivity().changePager(4);
                            }
                        });
                        tabInfor_PD.getmTabListView().setAdapter(adapter);
                        Utils.setListViewHeightBasedOnItems(tabInfor_PD.getmTabListView());
                    }
                } else {
                    Log.d("AAA", "khong co tran nao 2");
                }
            }

            @Override
            public void onFailure(Call<MatchResultNetworkWrapper> call, Throwable t) {

            }
        });

        Call<MatchResultNetworkWrapper> call_3 = dataService.getMatchResult(430);
        call_3.enqueue(new Callback<MatchResultNetworkWrapper>() {
            @Override
            public void onResponse(Call<MatchResultNetworkWrapper> call, Response<MatchResultNetworkWrapper> response) {
                int count = 0;
                if (response.body() != null) {
                    count = response.body().getCount();
                    if (count > 0) {
                        tabInfor_BL.setVisibility(View.VISIBLE);
                        tabInfor_BL.setmTabTitle("Buldesliga");
                        for (NextMatchNetWorkStatus item : response.body().getMatchResult()
                                ) {
                            Competitions nm = new Competitions(Utils.convertTime(item.getDate())[0],
                                    Utils.convertTime(item.getDate())[1],
                                    item.getHomeTeamName(),
                                    item.getAwayTeamName(),
                                    item.getResult().getGoalsHomeTeam(),
                                    item.getResult().getGoalsAwayTeam(),
                                    item.getStatus(), new TeamStatus());
                            listnextMatch_BL.add(nm);
                        }
                        Collections.reverse(listnextMatch_BL);
                        CustomListViewMatchResultAdapter adapter = new CustomListViewMatchResultAdapter(getActivity(), listnextMatch_BL);
                        adapter.setOnItemClickListener(new CustomListViewMatchResultAdapter.OnItemClickListener() {
                            @Override
                            public void onClickListener(int position) {
//                            Toast.makeText(getActivity(), "HAHAHAHA" + position, Toast.LENGTH_SHORT).show();
                                SharedPreferencesUtil.setBooleanPreference(getActivity(), AppConstant.SP_BOOLEAN_MATCH_VIDEO, true);
                                SharedPreferencesUtil.setStringPreference(getActivity(), AppConstant.SP_MATCH_VIDEO, Utils.getmatchInfor(listnextMatch_BL.get(position)) + "-3");
                                getMainActivity().changePager(4);
                            }
                        });
                        tabInfor_BL.getmTabListView().setAdapter(adapter);
                        Utils.setListViewHeightBasedOnItems(tabInfor_BL.getmTabListView());
                    }
                }
                else {
                    Log.d("AAA", "khong co tran nao 3");
                }
            }

            @Override
            public void onFailure(Call<MatchResultNetworkWrapper> call, Throwable t) {

            }
        });

        Call<MatchResultNetworkWrapper> call_4 = dataService.getMatchResult(438);
        call_4.enqueue(new Callback<MatchResultNetworkWrapper>() {
            @Override
            public void onResponse(Call<MatchResultNetworkWrapper> call, Response<MatchResultNetworkWrapper> response) {
                int count = 0;
                if (response.body() != null) {
                    count = response.body().getCount();
                    if (count > 0) {
                        tabInfor_SA.setVisibility(View.VISIBLE);
                        tabInfor_SA.setmTabTitle("Serie A");
                        for (NextMatchNetWorkStatus item : response.body().getMatchResult()
                                ) {
                            Competitions nm = new Competitions(Utils.convertTime(item.getDate())[0],
                                    Utils.convertTime(item.getDate())[1],
                                    item.getHomeTeamName(),
                                    item.getAwayTeamName(),
                                    item.getResult().getGoalsHomeTeam(),
                                    item.getResult().getGoalsAwayTeam(),
                                    item.getStatus(), new TeamStatus());
                            listnextMatch_SA.add(nm);
                        }
                        Collections.reverse(listnextMatch_SA);
                        CustomListViewMatchResultAdapter adapter = new CustomListViewMatchResultAdapter(getActivity(), listnextMatch_SA);
                        adapter.setOnItemClickListener(new CustomListViewMatchResultAdapter.OnItemClickListener() {
                            @Override
                            public void onClickListener(int position) {
//                            Toast.makeText(getActivity(), "HAHAHAHA" + position, Toast.LENGTH_SHORT).show();
                                SharedPreferencesUtil.setBooleanPreference(getActivity(), AppConstant.SP_BOOLEAN_MATCH_VIDEO, true);
                                SharedPreferencesUtil.setStringPreference(getActivity(), AppConstant.SP_MATCH_VIDEO, Utils.getmatchInfor(listnextMatch_SA.get(position)) + "-4");
                                getMainActivity().changePager(4);
                            }
                        });
                        tabInfor_SA.getmTabListView().setAdapter(adapter);
                        Utils.setListViewHeightBasedOnItems(tabInfor_SA.getmTabListView());
                    }
                } else {
                    Log.d("AAA", "khong co tran nao 4");
                }
            }

            @Override
            public void onFailure(Call<MatchResultNetworkWrapper> call, Throwable t) {

            }
        });
    }

    protected MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    public class getData_PL extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String date = "";
                Document doc = Jsoup.connect("http://www.24h.com.vn/ket-qua-bong-da/ket-qua-bong-da-ngoai-hang-anh-c140a397633.html").get();
                for (Element table : doc.select("table.tbl-ds-hot")) {
                    for (Element row : table.select("tr")) {
                        Elements tds = row.select("td");
                        Log.d("AAAA", tds.size() + "A");
                        if (tds.size() == 1 && tds.text().contains("Ngày")) {
                            date = tds.text().replaceFirst("Ngày ", "");
                        }
                        if (tds.size() > 3) {
                            String infor = date + "-" + tds.get(0).text() + "-" + tds.get(1).text() + "-" + tds.get(2).text().split(" - ")[0] + "-" + tds.get(2).text().split(" - ")[1];
                            Log.d("AAAA", infor);
                            Competitions nm = new Competitions(Utils.convertDate(date), "", tds.get(1).text(), tds.get(3).text(), tds.get(2).text().split(" - ")[0],
                                    tds.get(2).text().split(" - ")[1], "FINISHED");
                            listnextMatch_PL.add(nm);
                        }
                    }
                }
                Collections.reverse(listnextMatch_PL);
                /// process here
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showData();
                        pd.dismiss();
                    }
                });

            } catch (IOException e) {
                Toast.makeText(getActivity(), "Network has problem!", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        private void showData() {
            if (listnextMatch_PL.size() > 0) {
                tabInfor_PL.setVisibility(View.VISIBLE);
                tabInfor_PL.setmTabTitle("Premier League");

                CustomListViewMatchResultAdapter adapter = new CustomListViewMatchResultAdapter(getActivity(), listnextMatch_PL);
                tabInfor_PL.getmTabListView().setAdapter(adapter);
                Utils.setListViewHeightBasedOnItems(tabInfor_PL.getmTabListView());
                adapter.setOnItemClickListener(new CustomListViewMatchResultAdapter.OnItemClickListener() {
                    @Override
                    public void onClickListener(int position) {
//                            Toast.makeText(getActivity(), "HAHAHAHA" + position, Toast.LENGTH_SHORT).show();
                        SharedPreferencesUtil.setBooleanPreference(getActivity(), AppConstant.SP_BOOLEAN_MATCH_VIDEO, true);
                        SharedPreferencesUtil.setStringPreference(getActivity(), AppConstant.SP_MATCH_VIDEO, Utils.getmatchInfor(listnextMatch_PL.get(position)) + "-1");
                        getMainActivity().changePager(4);
                    }
                });
            }
        }
    }

    public class getData_PD extends AsyncTask<Void, Void, Void> {
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
                String date = "";
                Document doc = Jsoup.connect("http://www.24h.com.vn/ket-qua-bong-da/ket-qua-thi-dau-bong-da-tay-ban-nha-c140a398196.html").get();
                for (Element table : doc.select("table.tbl-ds-hot")) {
                    for (Element row : table.select("tr")) {
                        Elements tds = row.select("td");
                        Log.d("AAAA", tds.size() + "A");
                        if (tds.size() == 1 && tds.text().contains("Ngày")) {
                            date = tds.text().replaceFirst("Ngày ", "");
                        }
                        if (tds.size() > 3) {
                            String infor = date + "-" + tds.get(0).text() + "-" + tds.get(1).text() + "-" + tds.get(2).text().split(" - ")[0] + "-" + tds.get(2).text().split(" - ")[1];
                            Log.d("AAAA", infor);
                            Competitions nm = new Competitions(Utils.convertDate(date), "", tds.get(1).text(), tds.get(3).text(), tds.get(2).text().split(" - ")[0],
                                    tds.get(2).text().split(" - ")[1], "FINISHED");
                            listnextMatch_PD.add(nm);
                        }
                    }
                }
                Collections.reverse(listnextMatch_PD);
                /// process here
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showData();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void showData() {
            if (listnextMatch_PD.size() > 0) {
                tabInfor_PD.setVisibility(View.VISIBLE);
                tabInfor_PD.setmTabTitle("Premier League");

                CustomListViewMatchResultAdapter adapter = new CustomListViewMatchResultAdapter(getActivity(), listnextMatch_PD);
                tabInfor_PD.getmTabListView().setAdapter(adapter);
                Utils.setListViewHeightBasedOnItems(tabInfor_PD.getmTabListView());
                adapter.setOnItemClickListener(new CustomListViewMatchResultAdapter.OnItemClickListener() {
                    @Override
                    public void onClickListener(int position) {
//                            Toast.makeText(getActivity(), "HAHAHAHA" + position, Toast.LENGTH_SHORT).show();
                        SharedPreferencesUtil.setBooleanPreference(getActivity(), AppConstant.SP_BOOLEAN_MATCH_VIDEO, true);
                        SharedPreferencesUtil.setStringPreference(getActivity(), AppConstant.SP_MATCH_VIDEO, Utils.getmatchInfor(listnextMatch_PD.get(position)) + "-2");
                        getMainActivity().changePager(4);
                    }
                });
            }
        }
    }

    public class getData_BL extends AsyncTask<Void, Void, Void> {
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
                String date = "";
                Document doc = Jsoup.connect("http://www.24h.com.vn/ket-qua-bong-da/ket-qua-thi-dau-bong-da-duc-c140a396039.html").get();
                for (Element table : doc.select("table.tbl-ds-hot")) {
                    for (Element row : table.select("tr")) {
                        Elements tds = row.select("td");
                        Log.d("AAAA", tds.size() + "A");
                        if (tds.size() == 1 && tds.text().contains("Ngày")) {
                            date = tds.text().replaceFirst("Ngày ", "");
                        }
                        if (tds.size() > 3) {
                            String infor = date + "-" + tds.get(0).text() + "-" + tds.get(1).text() + "-" + tds.get(2).text().split(" - ")[0] + "-" + tds.get(2).text().split(" - ")[1];
                            Log.d("AAAA", infor);
                            Competitions nm = new Competitions(Utils.convertDate(date), "", tds.get(1).text(), tds.get(3).text(), tds.get(2).text().split(" - ")[0],
                                    tds.get(2).text().split(" - ")[1], "FINISHED");
                            listnextMatch_BL.add(nm);
                        }
                    }
                }
                Collections.reverse(listnextMatch_BL);
                /// process here
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showData();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void showData() {
            if (listnextMatch_BL.size() > 0) {
                tabInfor_BL.setVisibility(View.VISIBLE);
                tabInfor_BL.setmTabTitle("Premier League");

                CustomListViewMatchResultAdapter adapter = new CustomListViewMatchResultAdapter(getActivity(), listnextMatch_BL);
                tabInfor_BL.getmTabListView().setAdapter(adapter);
                Utils.setListViewHeightBasedOnItems(tabInfor_BL.getmTabListView());
                adapter.setOnItemClickListener(new CustomListViewMatchResultAdapter.OnItemClickListener() {
                    @Override
                    public void onClickListener(int position) {
//                            Toast.makeText(getActivity(), "HAHAHAHA" + position, Toast.LENGTH_SHORT).show();
                        SharedPreferencesUtil.setBooleanPreference(getActivity(), AppConstant.SP_BOOLEAN_MATCH_VIDEO, true);
                        SharedPreferencesUtil.setStringPreference(getActivity(), AppConstant.SP_MATCH_VIDEO, Utils.getmatchInfor(listnextMatch_BL.get(position)) + "-3");
                        getMainActivity().changePager(4);
                    }
                });
            }
        }
    }

    public class getData_SA extends AsyncTask<Void, Void, Void> {
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
                String date = "";
                Document doc = Jsoup.connect("http://www.24h.com.vn/ket-qua-bong-da/ket-qua-thi-dau-bong-da-y-c140a395045.html").get();
                for (Element table : doc.select("table.tbl-ds-hot")) {
                    for (Element row : table.select("tr")) {
                        Elements tds = row.select("td");
                        Log.d("AAAA", tds.size() + "A");
                        if (tds.size() == 1 && tds.text().contains("Ngày")) {
                            date = tds.text().replaceFirst("Ngày ", "");
                        }
                        if (tds.size() > 3) {
                            String infor = date + "-" + tds.get(0).text() + "-" + tds.get(1).text() + "-" + tds.get(2).text().split(" - ")[0] + "-" + tds.get(2).text().split(" - ")[1];
                            Log.d("AAAA", infor);
                            Competitions nm = new Competitions(Utils.convertDate(date), "", tds.get(1).text(), tds.get(3).text(), tds.get(2).text().split(" - ")[0],
                                    tds.get(2).text().split(" - ")[1], "FINISHED");
                            listnextMatch_SA.add(nm);
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
            }
            return null;
        }

        private void showData() {
            if (listnextMatch_SA.size() > 0) {
                tabInfor_SA.setVisibility(View.VISIBLE);
                tabInfor_SA.setmTabTitle("Premier League");

                CustomListViewMatchResultAdapter adapter = new CustomListViewMatchResultAdapter(getActivity(), listnextMatch_SA);
                tabInfor_SA.getmTabListView().setAdapter(adapter);
                Utils.setListViewHeightBasedOnItems(tabInfor_SA.getmTabListView());
                adapter.setOnItemClickListener(new CustomListViewMatchResultAdapter.OnItemClickListener() {
                    @Override
                    public void onClickListener(int position) {
//                            Toast.makeText(getActivity(), "HAHAHAHA" + position, Toast.LENGTH_SHORT).show();
                        SharedPreferencesUtil.setBooleanPreference(getActivity(), AppConstant.SP_BOOLEAN_MATCH_VIDEO, true);
                        SharedPreferencesUtil.setStringPreference(getActivity(), AppConstant.SP_MATCH_VIDEO, Utils.getmatchInfor(listnextMatch_SA.get(position)) + "-2");
                        getMainActivity().changePager(4);
                    }
                });
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void onBack(){
        if(listnextMatch_PL.size() >0) {
            tabInfor_PL.setVisibility(View.VISIBLE);
            CustomListViewMatchResultAdapter adapter = new CustomListViewMatchResultAdapter(getActivity(), listnextMatch_PL);
            adapter.setOnItemClickListener(new CustomListViewMatchResultAdapter.OnItemClickListener() {
                @Override
                public void onClickListener(int position) {

                    SharedPreferencesUtil.setBooleanPreference(getActivity(), AppConstant.SP_BOOLEAN_MATCH_VIDEO, true);
                    SharedPreferencesUtil.setStringPreference(getActivity(), AppConstant.SP_MATCH_VIDEO, Utils.getmatchInfor(listnextMatch_PL.get(position)) + "-1");
                    getMainActivity().changePager(4);
                }
            });
            tabInfor_PL.getmTabListView().setAdapter(adapter);
            Utils.setListViewHeightBasedOnItems(tabInfor_PL.getmTabListView());
        }
        if(listnextMatch_PD.size() >0) {
            tabInfor_PD.setVisibility(View.VISIBLE);
            CustomListViewMatchResultAdapter adapter = new CustomListViewMatchResultAdapter(getActivity(), listnextMatch_PD);
            adapter.setOnItemClickListener(new CustomListViewMatchResultAdapter.OnItemClickListener() {
                @Override
                public void onClickListener(int position) {

                    SharedPreferencesUtil.setBooleanPreference(getActivity(), AppConstant.SP_BOOLEAN_MATCH_VIDEO, true);
                    SharedPreferencesUtil.setStringPreference(getActivity(), AppConstant.SP_MATCH_VIDEO, Utils.getmatchInfor(listnextMatch_PD.get(position)) + "-2");
                    getMainActivity().changePager(4);
                }
            });
            tabInfor_PD.getmTabListView().setAdapter(adapter);
            Utils.setListViewHeightBasedOnItems(tabInfor_PD.getmTabListView());
        }
        if(listnextMatch_BL.size() >0) {
            tabInfor_BL.setVisibility(View.VISIBLE);
            CustomListViewMatchResultAdapter adapter = new CustomListViewMatchResultAdapter(getActivity(), listnextMatch_BL);
            adapter.setOnItemClickListener(new CustomListViewMatchResultAdapter.OnItemClickListener() {
                @Override
                public void onClickListener(int position) {

                    SharedPreferencesUtil.setBooleanPreference(getActivity(), AppConstant.SP_BOOLEAN_MATCH_VIDEO, true);
                    SharedPreferencesUtil.setStringPreference(getActivity(), AppConstant.SP_MATCH_VIDEO, Utils.getmatchInfor(listnextMatch_BL.get(position)) + "-1");
                    getMainActivity().changePager(4);
                }
            });
            tabInfor_BL.getmTabListView().setAdapter(adapter);
            Utils.setListViewHeightBasedOnItems(tabInfor_BL.getmTabListView());
        }
        if(listnextMatch_SA.size() >0) {
            tabInfor_SA.setVisibility(View.VISIBLE);
            CustomListViewMatchResultAdapter adapter = new CustomListViewMatchResultAdapter(getActivity(), listnextMatch_SA);
            adapter.setOnItemClickListener(new CustomListViewMatchResultAdapter.OnItemClickListener() {
                @Override
                public void onClickListener(int position) {

                    SharedPreferencesUtil.setBooleanPreference(getActivity(), AppConstant.SP_BOOLEAN_MATCH_VIDEO, true);
                    SharedPreferencesUtil.setStringPreference(getActivity(), AppConstant.SP_MATCH_VIDEO, Utils.getmatchInfor(listnextMatch_SA.get(position)) + "-1");
                    getMainActivity().changePager(4);
                }
            });
            tabInfor_SA.getmTabListView().setAdapter(adapter);
            Utils.setListViewHeightBasedOnItems(tabInfor_SA.getmTabListView());
        }

    }
}
