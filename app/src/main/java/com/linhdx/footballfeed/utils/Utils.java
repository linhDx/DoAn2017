package com.linhdx.footballfeed.utils;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.View.Activity.MainActivity;
import com.linhdx.footballfeed.entity.Competitions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
            now.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));
            now.add(Calendar.DAY_OF_MONTH, 1);
            result[0] = DateFormat.format("yyyy-MM-dd", now.getTime()).toString();
        } else {
            result[1] = (Integer.parseInt(next[0]) + 7) + ":" + next[1];
            result[0] = part[0];
        }

        return result;
    }

    //28/04 02:00-Manchester City-? - ?-Manchester United
    public static String[] matchInforXml(String infor) {
        String[] result = new String[6];
        String[] result_1 = infor.split("-");
        String[] result_2 = result_1[0].split(" ");
        String[] result_3 = result_2[0].split("/");
        result[0] = "2017" + "-" + result_3[1] + "-" + result_3[0];
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
        String result;
        if (date.contains("-")) {
            String[] a = date.split("-");
            result = a[2] + "-" + a[1] + "-" + a[0];
        } else {
            String[] a = date.split("/");
            result = a[2] + "-" + a[1] + "-" + a[0];
        }
        return result;
    }

    public static String getImgLink(String des) {
        int pFrom = des.indexOf("src=") + 5;
        int pEnd = des.indexOf(" />") - 1;
        String url = des.substring(pFrom, pEnd);
        return url;
    }

    public static String getImgLink_2(String des) {
        int pFrom = des.indexOf("src=") + 5;
        int pEnd = des.indexOf(">") - 1;
        String url = des.substring(pFrom, pEnd);
        Log.d("AAAA", "url" + url);
        return url;
    }


    //Thu, 11 May 2017 11:16:00 GMT+7
    public static Date StringToDate(String str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
        Date date = null;
        try {
            date = dateFormat.parse(str);
        } catch (ParseException e) {
            String dates[] = str.split(" ");
            Calendar cal = new GregorianCalendar();
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dates[1]));
            cal.set(Calendar.YEAR, Integer.parseInt(dates[3]));
            cal.set(Calendar.MONTH, convertMonth(dates[2]) - 1);
            String hours[] = dates[4].split(":");
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours[0]));
            cal.set(Calendar.MINUTE, Integer.parseInt(hours[1]));
            date = cal.getTime();
            e.printStackTrace();
            Log.d("AAAA", "error");
        }
        return date;
    }

    //Thu, 11 May 2017 16:44:35 GMT
    public static Date StringToDate_24h(String str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
        Date date = null;
        try {
            date = dateFormat.parse(str + "+7");
        } catch (ParseException e) {
            String dates[] = str.split(" ");
            Calendar cal = new GregorianCalendar();
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dates[1]));
            cal.set(Calendar.YEAR, Integer.parseInt(dates[3]));
            cal.set(Calendar.MONTH, convertMonth(dates[2]) - 1);
            String hours[] = dates[4].split(":");
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours[0]));
            cal.set(Calendar.MINUTE, Integer.parseInt(hours[1]));
            date = cal.getTime();
            e.printStackTrace();
            Log.d("AAAA", "error");
        }
        return date;
    }

    //11/05/2017 16:39:41
    public static Date StringToDate_BDC(String str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(str);
        } catch (ParseException e) {
            String dates[] = str.split(" ");
            String date1[] = dates[0].split("/");
            Calendar cal = new GregorianCalendar();
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date1[0]));
            cal.set(Calendar.YEAR, Integer.parseInt(date1[2]));
            cal.set(Calendar.MONTH, convertMonth(date1[1]) - 1);
            String hours[] = dates[2].split(":");
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours[0]));
            cal.set(Calendar.MINUTE, Integer.parseInt(hours[1]));
            date = cal.getTime();
            e.printStackTrace();
            Log.d("AAAA", "error");
        }
        return date;
    }

    private static int convertMonth(String MMM) {
        int month;
        switch (MMM) {
            case "Jan":
                month = 1;
                break;
            case "Feb":
                month = 2;
                break;
            case "Mar":
                month = 3;
                break;
            case "Apr":
                month = 4;
                break;
            case "May":
                month = 5;
                break;
            case "Jun":
                month = 6;
                break;
            case "Jul":
                month = 7;
                break;
            case "Aug":
                month = 8;
                break;
            case "Sep":
                month = 9;
                break;
            case "Oct":
                month = 10;
                break;
            case "Nov":
                month = 11;
                break;
            case "Dec":
                month = 12;
                break;
            default:
                month = 0;
        }
        return month;
    }

    public static void generateNotification(Context context) {

        android.support.v7.app.NotificationCompat.Builder nb = new android.support.v7.app.NotificationCompat.Builder(context);
        nb.setSmallIcon(R.drawable.app_icon);
        nb.setContentTitle("Ready for your match!!!");
        nb.setContentText("The match will start in about 10 minutes!!!");
        nb.setTicker("Ready for your match!");

        nb.setAutoCancel(true);


        //get the bitmap to show in notification bar
//        Bitmap bitmap_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.app_icon);
//        android.support.v7.app.NotificationCompat.BigPictureStyle s = new android.support.v7.app.NotificationCompat.BigPictureStyle().bigPicture(bitmap_image);
//        s.setSummaryText("The match will be play about 1 hours!!!");
//        nb.setStyle(s);


        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder TSB = TaskStackBuilder.create(context);
        TSB.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        TSB.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                TSB.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        nb.setContentIntent(resultPendingIntent);
        nb.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(11221, nb.build());
    }

    public static int getGoalDiff(String goal) {
        int result;
        String a[] = goal.split("-");
        result = (Integer.parseInt(a[0]) - Integer.parseInt(a[1]));
        return result;
    }

    public static String getmatchInfor(Competitions competitions) {
        String result;
        result = genTime(competitions.getDate()) + "-" + competitions.getHomeName() + "-" + competitions.getAwayName();
        return result;
    }

    //
    public static String genTime(String time) {
        String result;
        String a[] = time.split("-");
        String t1 = Integer.parseInt(a[2]) + "/" + a[1] + "/" + a[0];
        String t2 = Integer.parseInt(a[2]) + "/" + Integer.parseInt(a[1]) + "/" + a[0];
        return result = t1 + "-" + t2;
    }

    // 85 Overall 85 Potential Position CB Age30 (Sep 10, 1985) Height186cm Weight75kg Value€24M Wage€100K
    public static String[] getTable1(String a) {
        String result[] = new String[6];
        String data[] = a.split(" ");
        if (data.length == 14) {
            result[0] = data[5];
            result[1] = data[6].replaceFirst("Age", "") + " " + data[7] + " " + data[8] + " " + data[9];
            result[2] = data[10].replaceFirst("Height", "");
            result[3] = data[11].replaceFirst("Weight", "");
            result[4] = data[12].replaceFirst("Value", "");
            result[5] = data[13].replaceFirst("Wage", "");
        } else {
            result[0] = data[5] + " " + data[6];
            result[1] = data[7].replaceFirst("Age", "") + " " + data[8] + " " + data[9] + " " + data[10];
            result[2] = data[11].replaceFirst("Height", "");
            result[3] = data[12].replaceFirst("Weight", "");
            result[4] = data[13].replaceFirst("Value", "");
            result[5] = data[14].replaceFirst("Wage", "");
        }
        return result;
    }

    //Arsenal 83 Position LCB Jersey number 6 Joined Jul 7, 2010 Contract valid until 2020
//    public static String[] getTable2(String a) {
//        String result[] = new String[6];
//        String data[] = a.split(" ");
//        result[0] = data[0];
//        result[1] = data[1];
//        result[2] = data[3];
//        result[3] = data[6];
//        result[4] = data[8] + " " + data[9] + " " + data[10];
//        result[5] = data[14];
//        return result;
//    }
        public static String[] getTable2(String a[]){
            String result[] = new String[6];
            result[0] = a[0];
            result[1] = a[1]+"/100";
            result[2] = a[2].replaceFirst("Position ","");
            result[3] = a[3].replaceFirst("Jersey number ","");
            result[4] = a[4].replaceFirst("Joined ","");
            result[5] = a[5].replaceFirst("Contract valid until ","");
            return result;
        }

}
