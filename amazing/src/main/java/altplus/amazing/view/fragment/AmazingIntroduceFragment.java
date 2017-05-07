package altplus.amazing.view.fragment;

import altplus.amazing.util.SharedPreferencesUtil;

/**
 * Created by Nguyen Tien Hoang on 26/01/2016.
 */
public abstract class AmazingIntroduceFragment extends AmazingBaseFragment {
    /**
     * Call me to don't show Introduce screen on next open app.
     */
    protected void finishIntroduce() {
        SharedPreferencesUtil.setFinishIntroduce(getContext());
    }
}