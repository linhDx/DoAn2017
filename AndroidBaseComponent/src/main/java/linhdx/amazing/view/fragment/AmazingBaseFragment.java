package linhdx.amazing.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import linhdx.amazing.util.LogUtil;
import linhdx.amazing.view.activity.AmazingBaseActivity;


public abstract class AmazingBaseFragment<T> extends Fragment {
    public static final String PARAM1 = "param1";
    public static final String PARAM2 = "param2";
    public static int UPDATE_LIST_ITEM = 0;

    protected AmazingBaseActivity parentActivity;

    public abstract void refreshList(T video);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutId() <= 0) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        LogUtil.i("Fragment: " + getClass().getSimpleName());

        View view = inflater.inflate(getLayoutId(), container, false);

        initViews(view);

        parentActivity = (AmazingBaseActivity) getActivity();

        initData();

        initListeners();

        return view;
    }

    /**
     * Define layout id for view of this fragment
     *
     * @return The layout id of this fragment
     */
    protected abstract int getLayoutId();

    /**
     * @param view
     */
    protected abstract void initViews(View view);

    protected abstract void initData();

    protected abstract void initListeners();

    /**
     * Design observer pattern. This method using to other class can ping an update to this fragment
     *
     * @param updateTag using to tag the type update
     * @param object    the object using to update to this fragment
     */
    public void update(int updateTag, Object object) {
    }

    public void startActivity(Class<?> cls) {
        startActivity(new Intent(getActivity(), cls));
    }
}