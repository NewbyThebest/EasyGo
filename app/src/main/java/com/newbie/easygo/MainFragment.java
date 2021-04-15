package com.newbie.easygo;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainFragment extends BaseFragment {

    private List<TabFragment> tabFragmentList = new ArrayList<>();
    private MZBannerView mMZBanner;
    private List<GoodData> mBanners = new ArrayList<>();
    private FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frgment_main, container, false);
        initView(root);
        return root;
    }

    void initData() {
        mBanners.clear();
        for (int i = 0; i < 3; i++) {
            String title = "测试数据" + i;
            String price = "" + (i * i);
            String seller = "张三";
            String category = "安卓手机";
            String url = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpicture.ik123.com%2Fuploads%2Fallimg%2F161203%2F3-1612030ZG5.jpg&refer=http%3A%2F%2Fpicture.ik123.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620888901&t=01c32eff8a057c03ef89b029b6aaa3f5";
            mBanners.add(new GoodData(title, price, url, seller, category));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMZBanner.start();
    }

    void initView(View root) {
        TabLayout tabLayout = root.findViewById(R.id.tab_layout);
        ViewPager viewPager = root.findViewById(R.id.view_pager);
        FloatingActionButton btn = root.findViewById(R.id.floatingBtn);
        String[] tabs = CommonData.getCommonData().getTabs();
        if (CommonData.getCommonData().getUserType() == Constants.SELLER) {
            btn.setVisibility(View.VISIBLE);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonUtil.showGoodsEditDialog(MainFragment.this, null);
                }
            });
        }

        tabFragmentList.clear();
        //添加tab
        for (int i = 0; i < tabs.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabs[i]));
            tabFragmentList.add(TabFragment.newInstance(tabs[i]));
        }

        fragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return tabFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return tabFragmentList.size();
            }

            @Override
            public int getItemPosition(@NonNull Object object) {
                return POSITION_NONE;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabs[position];
            }
        };
        viewPager.setAdapter(fragmentPagerAdapter);

        //设置TabLayout和ViewPager联动
        tabLayout.setupWithViewPager(viewPager, false);
        mMZBanner = root.findViewById(R.id.banner);

        // 设置数据
        mMZBanner.setPages(mBanners, new MZHolderCreator<MainFragment.BannerViewHolder>() {
            @Override
            public MainFragment.BannerViewHolder createViewHolder() {
                return new MainFragment.BannerViewHolder();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateGoodsEvent event){
        fragmentPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public class BannerViewHolder implements MZViewHolder<GoodData> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);

            return view;
        }

        @Override
        public void onBind(Context context, int position, GoodData data) {
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CommonData.getCommonData().getUserType() == Constants.SELLER){
                        CommonUtil.showGoodsEditDialog(MainFragment.this, data);
                    }else {
                        CommonUtil.showBuyDialog(MainFragment.this,data,null);
                    }
                }
            });
            Glide.with(mImageView.getContext()).load(data.imgUrl).into(mImageView);
        }
    }


}
