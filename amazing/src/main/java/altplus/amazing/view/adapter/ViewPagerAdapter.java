package altplus.amazing.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

import altplus.amazing.view.fragment.AmazingBaseFragment;

/**
 * Created by Nguyen Tien Hoang on 26/01/2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<AmazingBaseFragment> mListFragments;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction;
    private int count = 100;
    private int newPos = -1;

    public ViewPagerAdapter(FragmentManager fm, List<AmazingBaseFragment> listFragment) {
        super(fm);
        mFragmentManager = fm;
        mListFragments = listFragment;
    }

    @Override
    public int getItemPosition(Object object) {
//        int position = mListFragments.indexOf(object);
//        if (newPos != -1 && position == newPos) {
//            newPos = -1;
//            return POSITION_NONE;
//        }
//        if (position >= 0) {
//            return position;
//        } else {
//            return POSITION_NONE;
//        }
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return mListFragments.get(position);
    }

    @Override
    public int getCount() {
        return mListFragments.size();
    }

    public void addFragment(int position, AmazingBaseFragment fragment) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        newPos = position;
        count++;
        mListFragments.add(position, fragment);
        ft.add(fragment, "tag_" + count);
        notifyDataSetChanged();
    }

    public void addFragmentAtPosition(int position, AmazingBaseFragment fragment) {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }

        newPos = position;
        mListFragments.add(position, fragment);
        mCurTransaction.add(fragment, "tag" + count++);
        notifyDataSetChanged();
    }

    public void destroyFragment(int position) {
        mFragmentManager.beginTransaction().remove(getItem(position));
        mListFragments.remove(position);
        notifyDataSetChanged();
    }

    public void clearFragment() {
        if (mListFragments != null && mListFragments.size() > 0) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            while (mListFragments.size() > 0) {
                ft.remove(getItem(0));
                mListFragments.remove(0);
            }
            mListFragments.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mListFragments.get(position).getArguments().getString(AmazingBaseFragment.PARAM1);
    }
}