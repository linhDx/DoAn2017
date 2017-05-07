package altplus.amazing.view.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import altplus.amazing.view.adapter.ViewPagerAdapter;

/**
 * Created by Nguyen Tien Hoang on 19/02/2016.
 */
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
