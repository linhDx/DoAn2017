package altplus.amazing.view.activity;

import android.os.Handler;
import android.widget.ImageView;

import altplus.amazing.R;
import altplus.amazing.util.SharedPreferencesUtil;

/**
 * Created by Nguyen Tien Hoang on 30/12/2015.
 */
public abstract class AmazingSplashActivity extends AmazingBaseActivity {
    private ImageView imgSplash;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {
        imgSplash = (ImageView) findViewById(R.id.img_splash);
    }

    @Override
    protected void initData() {
        imgSplash.setImageResource(getSplashImageResource());
        prepareNextActivity();
    }

    @Override
    protected void initListeners() {
    }

    private void prepareNextActivity() {
        final boolean isFinishIntroduce = getIntroduceActivity() == null ? true : SharedPreferencesUtil.isFinishIntroduce(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFinishIntroduce) {
                    startActivity(getMainActivity());
                } else {
                    startActivity(getIntroduceActivity());
                }

                finish();
            }
        }, getSplashTime());
    }

    protected abstract int getSplashImageResource();

    protected abstract int getSplashTime();

    protected abstract Class<?> getIntroduceActivity();

    protected abstract Class<?> getMainActivity();
}
