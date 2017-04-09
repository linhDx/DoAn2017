package com.linhdx.footballfeed.utils;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by shine on 09/04/2017.
 */

public class Utils {

    //time : 2017-04-09T12:30:00Z
    public static String[] convertTime(String time){
        String[] result= new String[2];
        String[] part = time.split("T");
        String[] next = part[1].split(":");
        result[0] = part[0];
        result[1]= (Integer.parseInt(next[0]) +7) + ":" + next[1];
        return  result;
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {


        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                Log.d("BBB", item.getMeasuredHeight()+"aaa");
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight  + totalDividersHeight;
            Log.d("AAAA", totalItemsHeight  + ":" + totalDividersHeight);
            listView.setLayoutParams(params);
            listView.requestLayout();


            return true;

        } else {
            return false;
        }

    }
}
