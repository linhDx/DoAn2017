package com.linhdx.footballfeed.View.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.linhdx.footballfeed.AppConstant;
import com.linhdx.footballfeed.utils.SharedPreferencesUtil;
import com.linhdx.footballfeed.utils.Utils;

/**
 * Created by shine on 15/05/2017.
 */

public class MyReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
      /*Intent service1 = new Intent(context, MyAlarmService.class);
        context.startService(service1);*/
        Log.i("App", "called receiver method");
        try{
            SharedPreferencesUtil.setStringPreference(context, AppConstant.SP_NOTIFICATION,"none");
            Utils.generateNotification(context);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
