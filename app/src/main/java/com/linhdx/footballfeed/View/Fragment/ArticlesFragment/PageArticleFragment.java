package com.linhdx.footballfeed.View.Fragment.ArticlesFragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.linhdx.footballfeed.R;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;


/**
 * Created by shine on 19/04/2017.
 */

public class PageArticleFragment extends Fragment{
    XWalkView xWalkView;
    String url;
    String webName;
    String detail = "";
    public static final String PARA1 = "param1";
    public static final String PARA2 = "param2";


    private class  ResourceClient extends XWalkResourceClient {

        public ResourceClient(XWalkView view) {
            super(view);
        }

        @Override
        public void onLoadStarted(XWalkView view, String url) {
            super.onLoadStarted(view, url);
        }

        @Override
        public void onLoadFinished(XWalkView view, String url) {
            super.onLoadFinished(view, url);
        }
    }

    public PageArticleFragment() {
    }
    public static PageArticleFragment newInstance(String webName, String Url) {
        PageArticleFragment f = new PageArticleFragment();
        Bundle args = new Bundle();
        args.putString(PARA1, webName);
        args.putString(PARA2, Url);
        f.setArguments(args);
        return f;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        url = getArguments().getString(PARA2);
        webName = getArguments().getString(PARA1);
        return inflater.inflate(R.layout.article_page_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        xWalkView = (XWalkView) view.findViewById(R.id.xWalkView);
        xWalkView.setResourceClient(new ResourceClient(xWalkView));
        XWalkPreferences.setValue("enable-javascript", true);
        XWalkPreferences.setValue(XWalkPreferences.JAVASCRIPT_CAN_OPEN_WINDOW, true);
        XWalkPreferences.setValue(XWalkPreferences.SUPPORT_MULTIPLE_WINDOWS, true);

        switch (webName){
            case "BDC":
                new GetData_BDC().execute();
                break;
            case "24h":
                new GetData_24h().execute();
                break;
            case "DT":
                new GetData_DT().execute();
                break;
            case "247":
                new GetData_247().execute();
                break;
        }
    }

    public class GetData_BDC extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect(url).get();

                Elements title = doc.select("div.col630.fr h1");
                Elements date = doc.select("div.author");
                Elements description = doc.select("p[class=\"sapo_detail fontbold\"]");
                doc.select("table").remove();
                Elements main = doc.select("div.exp_content.news_details");
                main.select("div.new_relation_top2.pkg.mar_bottom0").remove();
                main.select("div.adv").remove();

                detail = "<h2 style = \" color: red \">" + title.text()
                        + "</h2>";
                detail += "<font size=\" 1.2em \" style = \" color: #005500 \"><em>"
                        + date.text() + "</em></font>";
                detail += "<p style = \" color: #999999 \"><b>" + "<font size=\" 4em \" >"
                        + description.text() + "</font></b></p>";
                detail += "<font size=\" 4em \" >"+  main.toString() + "</font>";

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pd.dismiss();
            xWalkView.loadDataWithBaseURL(
                    "",
                    "<style>img{display: inline;height: auto;max-width: 100%;}"
                            + " p {font-family:\"Tangerine\", \"Sans-serif\",  \"Serif\" font-size: 48px}"
                            + " h2.expEdit { font-size: 10pt; color: gray ;}"
                            + " #youtube_iframe { width:100% ; height:auto} </style>"
                            + detail, "text/html", "UTF-8", "");

        }

    }
    public class GetData_DT extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect(url)
                        .get();

                Elements title = doc.select("div.ovh.detail_w h1");
                Log.d("AAAA", title.toString()+"AAAA");
                Elements date = doc.select("div.publishdate");
                Elements description = doc.select("div.ovh.detail_w h2");
                doc.select("table").remove();
                Elements main = doc.select("div.ovh.content ");
                detail += "<h2 style = \" color: red \">" + title.text()
                        + "</h2>";
                detail += "<font size=\" 1.2em \" style = \" color: #005500 \"><em>"
                        + date.text() + "</em></font>";
                detail += "<p style = \" color: #999999 \"><b>" + "<font size=\" 4em \" >"
                        + description.text() + "</font></b></p>";
                detail += "<font size=\" 4em \" >"+  main.toString() + "</font>";

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pd.dismiss();
            xWalkView.loadDataWithBaseURL(
                    "",
                    "<style>img{display: inline;height: auto;max-width: 100%;}"
                            + " p {font-family:\"Tangerine\", \"Sans-serif\",  \"Serif\" font-size: 48px} </style>"
                            + detail, "text/html", "UTF-8", "");

        }

    }
    public class GetData_24h extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect(url)
                        .get();

                Elements title = doc.select("div.news_left h1");
                Elements date = doc.select("div.link_clb span");
                Elements description = doc.select("div.article_content h2");
//                doc.select("table").remove();
                Elements main = doc.select("div.article_content");
                doc.select("script[type]").remove();
                doc.select("div.article_content h2").remove();
                detail += "<h2 style = \" color: red \">" + title.text()
                        + "</h2>";
                detail += "<font size=\" 1.2em \" style = \" color: #005500 \"><em>"
                        + date.text() + "</em></font>";
                detail += "<p style = \" color: #999999 \"><b>" + "<font size=\" 4em \" >"
                        + description.text() + "</font></b></p>";
                detail += "<font size=\" 4em \" >"+  main.toString() + "</font>";

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pd.dismiss();
            xWalkView.loadDataWithBaseURL(
                    "",
                    "<style>img{display: inline;height: auto;max-width: 100%;}"
                            + " p {font-family:\"Tangerine\", \"Sans-serif\",  \"Serif\" font-size: 48px} </style>"
                            + detail, "text/html", "UTF-8", "");

        }

    }
    public class GetData_247 extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect(url)
                        .get();

                Elements title = doc.select("div.grid940.fl h1");
                Elements date = doc.select("div.date_news.ArticleDateTime");
                Elements description = doc.select("ddiv.sapo_detail");
                doc.select("div#abdf p iframe").remove();
                doc.select("div#abdf p[style]").remove();
                doc.select("div#mcle-V67ZPnQyAd").remove();
                Elements main = doc.select("div#main-detail");
                main.select("div#AdAsia").remove();
                main.select("div#bs_mobileinpage").remove();
                main.select("div#abde a").remove();
                detail += "<h2 style = \" color: red \">" + title.text()
                        + "</h2>";
                detail += "<font size=\" 1.2em \" style = \" color: #005500 \"><em>"
                        + date.text() + "</em></font>";
                detail += "<p style = \" color: #999999 \"><b>" + "<font size=\" 4em \" >"
                        + description.text() + "</font></b></p>";
                detail += "<font size=\" 4em \" >"+  main.toString() + "</font>";

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pd.dismiss();
            xWalkView.loadDataWithBaseURL(
                    "",
                    "<style>img{display: inline;height: auto;max-width: 100%;}"
                            + " p {font-family:\"Tangerine\", \"Sans-serif\",  \"Serif\" font-size: 48px} </style>"
                            + detail, "text/html", "UTF-8", "");

        }

    }

}
