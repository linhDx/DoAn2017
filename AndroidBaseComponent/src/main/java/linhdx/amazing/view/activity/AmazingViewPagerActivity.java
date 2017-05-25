package linhdx.amazing.view.activity;

import java.util.ArrayList;
import java.util.List;

import linhdx.amazing.view.adapter.ViewPagerAdapter;
import linhdx.amazing.view.fragment.AmazingBaseFragment;
import linhdx.amazing.view.widget.CustomViewPager;

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