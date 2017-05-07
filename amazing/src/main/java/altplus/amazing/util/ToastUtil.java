package altplus.amazing.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Nguyen Tien Hoang on 13/05/2016.
 */
public class ToastUtil {

    public static void showToast(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}