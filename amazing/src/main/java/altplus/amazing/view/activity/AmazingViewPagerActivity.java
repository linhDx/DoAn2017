package altplus.amazing.view.activity;

import java.util.ArrayList;
import java.util.List;

import altplus.amazing.view.adapter.ViewPagerAdapter;
import altplus.amazing.view.fragment.AmazingBaseFragment;
import altplus.amazing.view.widget.CustomViewPager;

/**
 * Created by Nguyen Tien Hoang on 30/12/2015.
 */
public abstract class AmazingViewPagerActivity extends AmazingBaseActivity {
    protected CustomViewPager viewPager;
    protected List<AmazingBaseFragment> listFragments;

    @Override
    protected void initViews() {
        viewPager = (CustomViewPager) findViewById(getViewPagerId());
    }

    @Override
    protected void initData() {
        listFragments = new ArrayList<>();

        initListFragments();

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), listFragments));
    }

    protected abstract int getViewPagerId();

    protected abstract void initListFragments();
}