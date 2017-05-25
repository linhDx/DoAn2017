package com.linhdx.footballfeed.View.Fragment.LeagueTableFragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.linhdx.footballfeed.AppConstant;
import com.linhdx.footballfeed.adapter.CustomListViewLeagueTableAdapter;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork.LeagueTableNetWorkWrapper;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataNetWork.LeagueTableTeamNetWorkStatus;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataPreventive.DataWrapper;
import com.linhdx.footballfeed.AppObjectNetWork.FootBallDataPreventive.Standing;
import com.linhdx.footballfeed.entity.LeagueTableTeamStatus;
import com.linhdx.footballfeed.NetworkAPI.DataService;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shine on 10/04/2017.
 */

public class ShowLeagueFragment extends Fragment {
    DataService dataService;
    List<LeagueTableTeamStatus> listTeam;
    TextView mLeagueName;
    ListView lvLeagueTable;
    ImageView mBack;

    public ShowLeagueFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_league_table_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLeagueName = (TextView) view.findViewById(R.id.tv_league_table_title);
        lvLeagueTable= (ListView)view.findViewById(R.id.lv_league_table);
        mBack = (ImageView)view.findViewById(R.id.im_league_table_back) ;
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getFragmentManager().getBackStackEntryCount() >0){
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();
                }
            }
        });
        listTeam = new ArrayList<>();
        dataService = DataService.retrofit.create(DataService.class);
        Bundle id = this.getArguments();
        if(id != null){
            onRequestLeagueTable(id.getInt("id"));

            switch (id.getInt("id")){
                case 426:
                    mLeagueName.setText("Premier League");
//                    new getData().execute(AppConstant.URL_BXH_ANH);
                    break;
                case 430:
                    mLeagueName.setText("Buldesliga");
//                    new getData().execute(AppConstant.URL_BXH_DUC);
                    break;
                case 436:
                    mLeagueName.setText("Premier Division");
//                    new getData().execute(AppConstant.URL_BXH_TBN);
                    break;
                case 438:
                    mLeagueName.setText("Serie A");
//                    new getData().execute(AppConstant.URL_BXH_Y);
                    break;
                default:break;
            }
        }
    }

    public void onRequestLeagueTable(final int id_league) {
        Call<LeagueTableNetWorkWrapper> call = dataService.getTableTeamStatuses(id_league);
        call.enqueue(new Callback<LeagueTableNetWorkWrapper>() {
            @Override
            public void onResponse(Call<LeagueTableNetWorkWrapper> call, Response<LeagueTableNetWorkWrapper> response) {

                for (LeagueTableTeamNetWorkStatus item : response.body().getTableTeamStatuses()
                        ) {
                    LeagueTableTeamStatus lt = new LeagueTableTeamStatus(item.getPosition(), item.getTeamName(), item.getCrestURI(), item.getPlayedGames()
                            , item.getPoints(), item.getGoalDifference());
                    listTeam.add(lt);
                }
                CustomListViewLeagueTableAdapter adapter = new CustomListViewLeagueTableAdapter(listTeam, getActivity());
                lvLeagueTable.setAdapter(adapter);
//
            }

            @Override
            public void onFailure(Call<LeagueTableNetWorkWrapper> call, Throwable t) {
                switch (id_league){
                    case 426:
                        new getData().execute(AppConstant.URL_BXH_ANH);
                        break;
                    case 430:
                        new getData().execute(AppConstant.URL_BXH_DUC);
                        break;
                    case 436:
                        new getData().execute(AppConstant.URL_BXH_TBN);
                        break;
                    case 438:
                        new getData().execute(AppConstant.URL_BXH_Y);
                        break;
                    default:break;
                }
            }
        });
    }


    public class getData extends AsyncTask<String, Void, Void> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.show();

        }
        @Override
        protected Void doInBackground(String... params) {
            String url;
            url = params[0];
            try {
                Document doc = Jsoup.connect(url).get();
//
                for (Element table : doc.select("div#zone_type_view_total table.table-bordered")) {
                    for (Element row : table.select("tr")) {
                        Elements tds = row.select("td");
//                        Log.d("AAAA", tds.size()+"A");
                        if (tds.size() > 6) {
                            String infor = tds.get(0).text() + "-" + tds.get(1).text() +"-"+ tds.get(2).text() +"-" + tds.get(3).text() +tds.get(4).text();
                            Log.d("AAAA", infor);
//
                            LeagueTableTeamStatus lt= new LeagueTableTeamStatus(Integer.parseInt(tds.get(0).text()), tds.get(1).text(),
                                    "", Integer.parseInt(tds.get(2).text()), Integer.parseInt(tds.get(9).text())
                                    , Utils.getGoalDiff(tds.get(8).text()));
                            listTeam.add(lt);
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



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            showData();
            pd.dismiss();
        }

        private void showData(){
            CustomListViewLeagueTableAdapter adapter = new CustomListViewLeagueTableAdapter(listTeam, getActivity());
            lvLeagueTable.setAdapter(adapter);
        }
    }
}
