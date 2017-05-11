package com.linhdx.footballfeed.View.Fragment.ArticlesFragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.linhdx.footballfeed.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by shine on 19/04/2017.
 */

public class PageArticleFragment extends Fragment{
    WebView xWalkView;
    String url;
    String webName;
    String detail="";
    public static final String PARA1 = "param1";
    public static final String PARA2 = "param2";
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
        Log.d("AAAA", webName + ":" + url);
        return inflater.inflate(R.layout.article_page_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        xWalkView = (WebView) view.findViewById(R.id.xWalkView);
        new GetData().execute();
    }

    public class GetData extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect("http://www.bongda.com.vn/mourinho-che-bai-cdv-man-utd-d392813.html").get();

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
            xWalkView.loadDataWithBaseURL(
                    "",
                    "<style>img{display: inline;height: auto;max-width: 100%;}"
                            + " p {font-family:\"Tangerine\", \"Sans-serif\",  \"Serif\" font-size: 48px}"
                            + " h2.expEdit { font-size: 10pt; color: gray ;}"
                            + " #youtube_iframe { width:100% ; height:auto} </style>"
                            + detail, "text/html", "UTF-8", "");

        }

    }

}
