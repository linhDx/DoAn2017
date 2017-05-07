package com.linhdx.footballfeed.utils;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;


import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by shine on 09/04/2017.
 */

public class Utils {

    //time : 2017-04-09T12:30:00Z
    public static String[] convertTime(String time) {
        String[] date;
        String[] result = new String[2];
        String[] part = time.split("T");
        String[] next = part[1].split(":");

        if ((Integer.parseInt(next[0]) + 7) >= 24) {
            int a = (Integer.parseInt(next[0]) + 7) - 24;
            result[1] = ("0" + a + ":" + next[1]);
            date = part[0].split("-");
            GregorianCalendar now = new GregorianCalendar();
            now.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]) -1, Integer.parseInt(date[2]));
            now.add(Calendar.DAY_OF_MONTH, 1);
            result[0] = DateFormat.format("yyyy-MM-dd", now.getTime()).toString();
        } else {
            result[1] = (Integer.parseInt(next[0]) + 7) + ":" + next[1];
            result[0] = part[0];
        }

        return result;
    }

    //28/04 02:00-Manchester City-? - ?-Manchester United
    public static String[] matchInforXml(String infor){
        String[] result= new String[6];
        String[] result_1= infor.split("-");
        String[] result_2 = result_1[0].split(" ");
        String[] result_3 = result_2[0].split("/");
        result[0] = "2017" +"-"+result_3[1]+"-"+result_3[0] ;
        result[1] = result_2[1];
        result[2] = result_1[1];
        result[3] = result_1[2];
        result[4] = result_1[3];
        result[5] = result_1[4];
        return result;
    }
    // show full item in listview
    public static boolean setListViewHeightBasedOnItems(ListView listView) {


        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();


            return true;

        } else {
            return false;
        }

    }

    //change http -> https
    public static String convertHttp(String url) {
        String result;
        if (url.contains("https")) {
            result = url;
        } else {
            result = url.replaceFirst("http", "https");
        }
        return result;
    }

    //get team id
    //http://api.football-data.org/v1/teams/66/players -> 66
    public static String getTeamId(String string) {
        String[] a = string.split("/");
        return a[5];
    }

    // "1985-07-28" -> "28-07-1985"
    public static String convertDate(String date) {
        String[] a = date.split("-");
        return a[2] + "-" + a[1] + "-" + a[0];
    }
    public static String getImgLink(String des){
        int pFrom = des.indexOf("src=") +5;
        int pEnd =des.indexOf(" />")-1;
        String url = des.substring(pFrom, pEnd);
        return  url;
    }
}
