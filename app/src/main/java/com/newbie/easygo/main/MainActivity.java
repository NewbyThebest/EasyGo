package com.newbie.easygo.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.ImmersionBar;
import com.newbie.easygo.login.MainFragment;
import com.newbie.easygo.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private LinearLayout mTabMain;
    private LinearLayout mTabBuy;
    private LinearLayout mTabMe;
    private ImageButton mImageTabMain;
    private ImageButton mImageTabBuy;
    private ImageButton mImageTabMe;

    private ViewPager mViewPager;
    private TabFragmentViewPagerAdapter mAdapter;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this).init();
        initView();
        initClickListener();
    }


    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.tab_main_viewpager);
        mTabMain = (LinearLayout) findViewById(R.id.id_tab_main);
        mTabBuy = (LinearLayout) findViewById(R.id.id_tab_buy);
        mTabMe = (LinearLayout) findViewById(R.id.id_tab_me);
        mImageTabMain = (ImageButton) findViewById(R.id.tab_main_icon_grey);
        mImageTabBuy = (ImageButton) findViewById(R.id.tab_buy_icon_grey);
        mImageTabMe = (ImageButton) findViewById(R.id.tab_me_icon_grey);

        mFragments = new ArrayList<Fragment>();
        Fragment mTab_01 = new MainFragment();
        Fragment mTab_02 = new BuyFragment();
        Fragment mTab_03 = new MeFragment();
        mFragments.add(mTab_01);
        mFragments.add(mTab_02);
        mFragments.add(mTab_03);

        mAdapter = new TabFragmentViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //????????? ??????????????????
            @Override
            public void onPageSelected(int position) {
                int currentItem = mViewPager.getCurrentItem();
                initTabImage();
                switch (currentItem) {
                    case 0:
                        mImageTabMain.setBackgroundResource(R.drawable.ic_main);
                        break;
                    case 1:
                        mImageTabBuy.setBackgroundResource(R.drawable.ic_buy);
                        break;
                    case 2:
                        mImageTabMe.setBackgroundResource(R.drawable.ic_user);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //?????????????????????(???????????????????????????????????????????????????)
    private void initTabImage() {
        mImageTabMain.setBackgroundResource(R.drawable.ic_mian_normal);
        mImageTabBuy.setBackgroundResource(R.drawable.ic_buy_normal);
        mImageTabMe.setBackgroundResource(R.drawable.ic_user_normal);
    }

    //???????????????????????????
    private void initClickListener() {
        mTabMain.setOnClickListener(this);
        mTabBuy.setOnClickListener(this);
        mTabMe.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.id_tab_main:
                // initTabImage();
                //mImageTabMain.setBackgroundResource(R.drawable.tab_main_icon_green);
                //??????????????????????????????????????????,????????????????????????fragment;
                setSelect(0);
                break;
            case R.id.id_tab_buy:
                setSelect(1);
                break;
            case R.id.id_tab_me:
                setSelect(2);
                break;
        }
    }

    //???????????????????????????????????????,??????????????????
    private void setSelect(int i) {

        initTabImage();
        switch (i) {
            case 0:
                mImageTabMain.setBackgroundResource(R.drawable.ic_main);
                break;
            case 1:
                mImageTabBuy.setBackgroundResource(R.drawable.ic_buy);
                break;
            case 2:
                mImageTabMe.setBackgroundResource(R.drawable.ic_user);
                break;
            default:
                break;
        }
        mViewPager.setCurrentItem(i);
    }

}