package linhdx.amazing.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import linhdx.amazing.R;


public abstract class AmazingBaseActivity extends AppCompatActivity {
    public static final int ANIM_NONE = 0;
    public static final int ANIM_BOTTOM_TO_TOP = 1;
    public static final int ANIM_TOP_TO_BOTTOM = 2;
    public static final int ANIM_RIGHT_TO_LEFT = 3;
    public static final int ANIM_LEFT_TO_RIGHT = 4;
    private ProgressDialog mProgressDialog;

    protected abstract int getLayoutId();

    protected abstract void initViews();

    protected abstract void initData();

    protected abstract void initListeners();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
        }

        initViews();

        initData();

        initListeners();
    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int messageId) {
        Toast.makeText(getApplicationContext(), getString(messageId), Toast.LENGTH_SHORT).show();
    }

    public void showToast(String message, int position) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(position, 0, 0);
        toast.show();
    }

    public void showProgressDialog(String message) {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.show();
            } else {
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage(message);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Using try catch to catch the case: The activity is not running but still show the dialog.
    }

    public void showProgressDialog(int messageId) {
        showProgressDialog(getString(messageId));
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
    }

    public void startActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    public void startActivity(Class<?> cls, int animationType) {
        startActivity(cls);
        startTransition(animationType);
    }

    public void finish(int animationType) {
        finish();
        startTransition(animationType);
    }

    public void startActivity(Intent intent, int animationType) {
        super.startActivity(intent);
        startTransition(animationType);
    }

    public void startActivityForResult(Intent intent, int requestCode, int animationType) {
        super.startActivityForResult(intent, requestCode);
        startTransition(animationType);
    }

    private void startTransition(int animationType) {
        switch (animationType) {
            case ANIM_BOTTOM_TO_TOP:
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
            case ANIM_TOP_TO_BOTTOM:
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
                break;
            case ANIM_RIGHT_TO_LEFT:
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
            case ANIM_LEFT_TO_RIGHT:
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
        }
    }

    /**
     * Finish activity with Go to bottom animation.
     */
    public void finishGoToBottom() {
        finish(ANIM_TOP_TO_BOTTOM);
    }

    /**
     * Finish activity with Go to right animation.
     */
    public void finishGoToRight() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    /**
     * Start activity with Go to top animation.
     *
     * @param intent Intent start activity.
     */
    public void startActivityFromBottom(Intent intent) {
        startActivity(intent, ANIM_BOTTOM_TO_TOP);
    }

//    public void startActivityFromRight(Class<?> cls) {
//        startActivity(new Intent(this, cls));
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
//    }
}