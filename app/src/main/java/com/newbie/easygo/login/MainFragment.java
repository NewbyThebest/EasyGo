package com.newbie.easygo.login;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.newbie.easygo.common.MainManager;
import com.newbie.easygo.R;
import com.newbie.easygo.main.TabFragment;
import com.newbie.easygo.common.UpdateGoodsEvent;
import com.newbie.easygo.common.BaseFragment;
import com.newbie.easygo.common.CommonData;
import com.newbie.easygo.common.CommonUtil;
import com.newbie.easygo.common.Constants;
import com.newbie.easygo.common.GoodData;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainFragment extends BaseFragment {

    /**
     * 类别fragment list
     */
    private List<TabFragment> tabFragmentList = new ArrayList<>();

    /**
     * 自动轮播图控件
     */
    private MZBannerView mMZBanner;

    /**
     * 轮播图的数据
     */
    private List<GoodData> mBanners = new ArrayList<>();

    /**
     * 适配器
     */
    private FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 注册eventbus
         */
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

    /**
     * 请服务器请求轮播图数据
     */
    void initData() {
        mBanners.add(new GoodData());
        mBanners.add(new GoodData());
        mBanners.add(new GoodData());
        if (CommonData.getCommonData().getUserType() == Constants.SELLER){
            Map<String, String> map = new HashMap<>();
            MainManager.getInstance().getNetService().querySellerBannerGoods(map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<GoodData>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (getActivity() != null) {
                                Toast.makeText(getActivity(), "网络不佳，请重试！", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onNext(List<GoodData> datas) {
                            mBanners.clear();
                            if (datas != null && !datas.isEmpty()) {
                                for (int i = 0; i < 3; i++) {
                                    int size = datas.size();
                                    Random random = new Random();
                                    int pos = random.nextInt(size);
                                    mBanners.add(datas.get(pos));
                                }
                                // 设置数据
                                mMZBanner.setPages(mBanners, new MZHolderCreator<MainFragment.BannerViewHolder>() {
                                    @Override
                                    public MainFragment.BannerViewHolder createViewHolder() {
                                        return new MainFragment.BannerViewHolder();
                                    }
                                });
                            }else{
                                mBanners.add(new GoodData());
                                mBanners.add(new GoodData());
                                mBanners.add(new GoodData());
                            }
                        }
                    });
        }else {
            Map<String, String> map = new HashMap<>();
            MainManager.getInstance().getNetService().queryBannerGoods(map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<GoodData>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (getActivity() != null) {
                                Toast.makeText(getActivity(), "网络不佳，请重试！", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onNext(List<GoodData> datas) {
                            mBanners.clear();
                            if (datas != null && !datas.isEmpty()) {
                                for (int i = 0; i < 3; i++) {
                                    int size = datas.size();
                                    Random random = new Random();
                                    int pos = random.nextInt(size);
                                    mBanners.add(datas.get(pos));
                                }
                                // 设置数据
                                mMZBanner.setPages(mBanners, new MZHolderCreator<MainFragment.BannerViewHolder>() {
                                    @Override
                                    public MainFragment.BannerViewHolder createViewHolder() {
                                        return new MainFragment.BannerViewHolder();
                                    }
                                });
                            }else{
                                mBanners.add(new GoodData());
                                mBanners.add(new GoodData());
                                mBanners.add(new GoodData());
                            }
                        }
                    });
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mMZBanner.start();
    }

    /**
     * 初始化界面控件
     * @param root
     */
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
                    if (TextUtils.isEmpty(CommonData.getCommonData().getUserInfo().title)) {
                        Toast.makeText(getContext(), "请先完善个人信息！", Toast.LENGTH_LONG).show();
                        CommonUtil.showUserEditDialog(MainFragment.this, CommonData.getCommonData().getUserInfo());
                    } else {
                        CommonUtil.showGoodsEditDialog(MainFragment.this, null);
                    }
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

    /**
     * eventbus更新界面
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateGoodsEvent event) {
        for (BaseFragment fragment : tabFragmentList) {
            fragment.updateView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 轮播图适配器
     */
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
                    if (!TextUtils.isEmpty(data.imgUrl)){
                        if (CommonData.getCommonData().getUserType() == Constants.SELLER) {
                            CommonUtil.showGoodsEditDialog(MainFragment.this, data);
                        } else {
                            CommonUtil.showBuyDialog(MainFragment.this, data, CommonData.getCommonData().getUserInfo());
                        }
                    }
                }
            });
            if (!TextUtils.isEmpty(data.imgUrl)) {
                Glide.with(mImageView.getContext()).load(data.imgUrl).into(mImageView);
            }
        }


    }


}
