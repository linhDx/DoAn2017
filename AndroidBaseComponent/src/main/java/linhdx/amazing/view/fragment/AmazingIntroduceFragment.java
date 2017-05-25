package linhdx.amazing.view.fragment;

import linhdx.amazing.util.SharedPreferencesUtil;


public abstract class AmazingIntroduceFragment extends AmazingBaseFragment {
    /**
     * Call me to don't show Introduce screen on next open app.
     */
    protected void finishIntroduce() {
        SharedPreferencesUtil.setFinishIntroduce(getContext());
    }
}