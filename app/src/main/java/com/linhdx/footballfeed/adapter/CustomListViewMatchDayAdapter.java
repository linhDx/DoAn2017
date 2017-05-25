package com.linhdx.footballfeed.adapter;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.linhdx.footballfeed.AppConstant;
import com.linhdx.footballfeed.View.Activity.MyReceiver;
import com.linhdx.footballfeed.View.Fragment.MatchDayFragment.MatchDayFragment;
import com.linhdx.footballfeed.entity.NextMatchStatus;
import com.linhdx.footballfeed.R;
import com.linhdx.footballfeed.utils.SharedPreferencesUtil;
import com.linhdx.footballfeed.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by shine on 08/04/2017.
 */

public class CustomListViewMatchDayAdapter extends BaseAdapter {

    List<NextMatchStatus> list;
    Context context;
    private PendingIntent pendingIntent;
    MatchDayFragment matchDayFragment;
    public CustomListViewMatchDayAdapter(Context context, List<NextMatchStatus> list, MatchDayFragment matchDayFragment) {
        this.list = list;
        this.context = context;
        this.matchDayFragment = matchDayFragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder{
        TextView tvTime, tvHome, tvAway, tvDate;
        ImageView imgAlert;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        String check;
        final Holder holder= new Holder();
        View rowView;
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.custom_lv_match_day, parent, false);

        check = list.get(position).getDate()+"-"+ list.get(position).getHomeName()+"-" + list.get(position).getAwayName();

        holder.tvTime = (TextView)rowView.findViewById(R.id.tv_time_matchday);
        holder.tvHome= (TextView)rowView.findViewById(R.id.tv_home_team);
        holder.tvAway = (TextView)rowView.findViewById(R.id.tv_away_team);
        holder.imgAlert = (ImageView) rowView.findViewById(R.id.iv_alert_time);
        holder.tvDate=(TextView)rowView.findViewById(R.id.tv_date_matchday);

        String saved = SharedPreferencesUtil.getStringPreference(context, AppConstant.SP_NOTIFICATION);
        if(saved!= null && saved.compareTo(check)==0){
            holder.imgAlert.setImageResource(R.drawable.alarm_2);
        }
        holder.tvTime.setText(list.get(position).getTime());
        holder.tvHome.setText(list.get(position).getHomeName());
        holder.tvAway.setText(list.get(position).getAwayName());
        holder.tvDate.setText(Utils.convertDate(list.get(position).getDate()));
        holder.imgAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(list.get(position),holder.imgAlert);
            }
        });
        return rowView;
    }

    public void setAlarm(NextMatchStatus match){

        String time[] = genTime(match.getTime()).split(":");
        String dates[] = match.getDate().split("-");
        Calendar calendar = Calendar.getInstance();
//        Log.d("AAAA", time[0] +":" + time[1]);


        calendar.set(Calendar.MONTH, Integer.parseInt(dates[1])-1);
        calendar.set(Calendar.YEAR, Integer.parseInt(dates[0]));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dates[2]));

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM,Calendar.AM);

//        calendar.set(Calendar.HOUR_OF_DAY, 2);
//        calendar.set(Calendar.MINUTE, 15);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.AM_PM,Calendar.AM);

//        Log.d("AAAA", calendar.get(Calendar.DAY_OF_MONTH) + ":" + calendar.get(Calendar.MONTH));
        Intent myIntent = new Intent(context, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent,0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(context,"Notification has been set", Toast.LENGTH_SHORT).show();
    }

    public String genTime(String times){
        String hour, minus;
        String tm[] = times.split(":");
        if((Integer.parseInt(tm[1]) - 10) <0 ){
            hour = (Integer.parseInt(tm[0]) -1) +"";
            minus = "50";
        } else {
            hour = tm[0];
            minus = (Integer.parseInt(tm[1]) - 10)+"";
        }

        return hour+":"+minus;
    }

    private void showDialog(final NextMatchStatus matchs, final ImageView img){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Notification!");

        // set dialog message
        if(matchs.getStatus().compareTo("FINISHED")!= 0) {
            alertDialogBuilder
                    .setMessage("Are you want to set notification for this match?")
                    .setCancelable(false)
                    .setPositiveButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String saved = matchs.getDate()+ "-" + matchs.getHomeName()+"-" + matchs.getAwayName();
                            img.setImageResource(R.drawable.alarm_2);
                            setAlarm(matchs);
                            SharedPreferencesUtil.setStringPreference(context, AppConstant.SP_NOTIFICATION,saved);
                            matchDayFragment.refeshData();
                        }
                    });
        } else {
            alertDialogBuilder.setMessage("The Game has finished!")
                    .setCancelable(false)
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
        }
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
