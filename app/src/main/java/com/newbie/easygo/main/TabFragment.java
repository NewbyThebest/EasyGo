package com.newbie.easygo.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newbie.easygo.R;
import com.newbie.easygo.common.BaseFragment;
import com.newbie.easygo.common.CommonData;
import com.newbie.easygo.common.CommonUtil;
import com.newbie.easygo.common.Constants;
import com.newbie.easygo.common.GoodData;
import com.newbie.easygo.common.MainManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TabFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private ImageView mEmptyView;
    private MainRvAdapter mainRvAdapter;
    private List<GoodData> mList = new ArrayList<>();
    private String mCategory;

    public static TabFragment newInstance(String label) {
        Bundle args = new Bundle();
        args.putString("label", label);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tab, container, false);
        recyclerView = root.findViewById(R.id.rv);
        mEmptyView = root.findViewById(R.id.empty_view);
        mainRvAdapter = new MainRvAdapter(mList, true);
        if (mList.isEmpty()) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mainRvAdapter.setOnItemClickListener(new MainRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GoodData data) {
                if (CommonData.getCommonData().getUserType() == Constants.BUYER) {
                    CommonUtil.showBuyDialog(TabFragment.this, data, CommonData.getCommonData().getUserInfo());
                } else {
                    CommonUtil.showGoodsEditDialog(TabFragment.this, data);
                }
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mainRvAdapter);
        return root;
    }

    void initData() {
        mCategory = getArguments().getString("label");
        Map<String, String> map = new HashMap<>();
        if (CommonData.getCommonData().getUserType() == Constants.SELLER) {
            map.put("category", mCategory);
            map.put("uid", CommonData.getCommonData().getUserInfo().uid);

            MainManager.getInstance().getNetService().querySellerCategoryGoods(map)
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
                            mList.clear();
                            if (datas != null && !datas.isEmpty()) {
                                mList.addAll(datas);
                                if (mEmptyView != null) {
                                    mEmptyView.setVisibility(View.GONE);
                                }
                            } else {
                                if (mEmptyView != null) {
                                    mEmptyView.setVisibility(View.VISIBLE);
                                }

                            }
                            if (mainRvAdapter != null) {
                                mainRvAdapter.setList(mList);
                                mainRvAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        } else {
            map.put("category", mCategory);
            MainManager.getInstance().getNetService().queryCategoryGoods(map)
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
                            mList.clear();
                            if (datas != null && !datas.isEmpty()) {
                                mList.addAll(datas);
                                if (mEmptyView != null) {
                                    mEmptyView.setVisibility(View.GONE);
                                }
                            } else {
                                if (mEmptyView != null) {
                                    mEmptyView.setVisibility(View.VISIBLE);
                                }

                            }
                            if (mainRvAdapter != null) {
                                mainRvAdapter.setList(mList);
                                mainRvAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }


    }

    @Override
    public void updateView() {
        initData();
    }
}
