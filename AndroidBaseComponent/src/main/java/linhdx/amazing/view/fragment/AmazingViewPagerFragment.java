package linhdx.amazing.view.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import linhdx.amazing.view.adapter.ViewPagerAdapter;


public abstract class AmazingViewPagerFragment extends AmazingBaseFragment {
    protected ViewPager viewPagerF;
    protected List<AmazingBaseFragment> listFragmentsC;

    @Override
    protected void initViews(View view) {
        viewPagerF = (ViewPager) view.findViewById(getViewPagerId());
    }

    @Override
    protected void initData() {
        listFragmentsC = new ArrayList<>();

        initListFragments();

        viewPagerF.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), listFragmentsC));
    }

    protected abstract int getViewPagerId();

    protected abstract void initListFragments();
}
