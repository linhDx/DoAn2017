package altplus.amazing.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by congn_000 on 9/7/2016.
 */
public class NetworkUtil {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
