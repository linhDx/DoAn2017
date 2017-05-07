package altplus.amazing.util;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nguyen Tien Hoang on 17/05/2016.
 */
public class AppUtil {
    public static String getAppPackage(Context context) {
        return context.getApplicationContext().getPackageName();
    }

    public static void openAppInPlayStore(Context context) {
        final String appPackageName = getAppPackage(context);
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static void openMailApp(Context context, String dialogTitle, String subject, String text) {
        List<Intent> targetShareIntents = new ArrayList<>();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        List<ResolveInfo> listResolveInfos = context.getPackageManager().queryIntentActivities(shareIntent, 0);
        if (!listResolveInfos.isEmpty()) {
            for (ResolveInfo resInfo : listResolveInfos) {
                String packageName = resInfo.activityInfo.packageName;
                if (isPackageOfMailApp(packageName)) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, text);
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    intent.setPackage(packageName);
                    targetShareIntents.add(intent);
                }
            }
            if (!targetShareIntents.isEmpty()) {
                Intent chooserIntent = Intent.createChooser(targetShareIntents.remove(0), dialogTitle);
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
                context.startActivity(chooserIntent);
            }
        }
    }

    private static boolean isPackageOfMailApp(String packageName) {
        return packageName.contains("com.google.android.gm") || packageName.contains("android.mail");
    }
}