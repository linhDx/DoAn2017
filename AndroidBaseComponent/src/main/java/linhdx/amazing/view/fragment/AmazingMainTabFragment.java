package linhdx.amazing.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Stack;

import linhdx.amazing.R;

public abstract class AmazingMainTabFragment extends Fragment {
    private Stack<AmazingBaseFragment> fragmentStack;
    private FragmentManager fragmentManager;

    private int idContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_tab, container, false);
        initUI();
        return rootView;
    }

    protected void initUI() {
        fragmentStack = new Stack<>();
        fragmentManager = getChildFragmentManager();
        setupFirstTabFragment();
    }

    public void startNewFragment(AmazingBaseFragment newFragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        for (AmazingBaseFragment abf : fragmentStack) {
            ft.hide(abf);
        }
        ft.add(idContainer, newFragment);
        fragmentStack.push(newFragment);
        ft.commit();
    }

    public boolean backFragment() {
        if (fragmentStack.size() > 1) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.remove(fragmentStack.pop());
            for (int i = 0; i < fragmentStack.size(); i++) {
                if (i == fragmentStack.size() - 1) {
                    ft.show(fragmentStack.get(i));
                } else {
                    ft.hide(fragmentStack.get(i));
                }
            }
            ft.commit();
            return true;
        } else {
            return false;
        }
    }

    protected abstract void setupFirstTabFragment();

    /**
     * Enter next fragment.
     *
     * @param cls  The class of fragment will go to
     * @param args The arguments to transfer data into next fragment.
     */
    public void startNewFragment(Class<?> cls, Bundle args) {
    }

    /**
     * When user click on the same current tab, app will show the first page in this tab
     */
    public void gotoFirstFragment() {
        if (fragmentStack.size() > 1) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            do {
                ft.remove(fragmentStack.pop());
            } while (fragmentStack.size() > 1);

            ft.show(fragmentStack.peek());
            ft.commit();
        }
    }
}
