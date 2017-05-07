package altplus.amazing.view.activity;

import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.View;

import altplus.amazing.R;
import altplus.amazing.view.widget.CustomViewPager;
import altplus.amazing.view.widget.viewpagerindicator.CirclePageIndicator;


/**
 * Created by Nguyen Tien Hoang on 26/01/2016.
 */
public abstract class AmazingIntroduceActivity extends AmazingViewPagerActivity {
    private CirclePageIndicator pageIndicator;
    protected boolean isTutorial = false;
    private GestureDetector tapOnViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_introduce;
    }

    @Override
    protected int getViewPagerId() {
        return R.id.pager;
    }

    @Override
    protected void initViews() {
        super.initViews();
        pageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);

    }

    @Override
    protected void initData() {
        super.initData();
        pageIndicator.setFillColor(getFillColorIndicator());
        pageIndicator.setPageColor(getPageColorIndicator());
    }

    @Override
    protected void initListeners() {
        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                AmazingIntroduceActivity.this.transformPage(view, position);
            }
        });

        viewPager.setOnSwipeOutListener(new CustomViewPager.OnSwipeOutListener() {
            @Override
            public void onSwipeOutAtStart() {

            }

            @Override
            public void onSwipeOutAtEnd() {
                if (isTutorial) {
                    onBackPressed();
                }
            }
        });

        pageIndicator.setViewPager(viewPager);
        pageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    protected abstract int getFillColorIndicator();

    protected abstract int getPageColorIndicator();

    protected void transformPage(View view, float position) {
        if (position <= -1.0F || position >= 1.0F) {
            view.setTranslationX(view.getWidth() * position);
            view.setAlpha(0.0F);
        } else if (position == 0.0F) {
            view.setTranslationX(view.getWidth() * position);
            view.setAlpha(1.0F);
        } else {
            // position is between -1.0F & 0.0F OR 0.0F & 1.0F
            view.setTranslationX(view.getWidth() * -position);
            view.setAlpha(1.0F - Math.abs(position));
        }
    }

    protected void setVisibilityIndicator(boolean visible) {
        pageIndicator.setVisibility(visible ? View.VISIBLE : View.GONE);
    }


}
