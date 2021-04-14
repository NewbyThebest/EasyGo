package com.newbie.easygo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class BaseFragment extends SimpleImmersionFragment {
    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true).fitsSystemWindows(true).init();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void updateView(){

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (getChildFragmentManager().getFragments().size() > 0) {
            List<Fragment> fragments = getChildFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
