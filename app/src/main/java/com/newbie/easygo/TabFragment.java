package com.newbie.easygo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabFragment extends BaseFragment {
    private RecyclerView recyclerView;
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

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initData();
        View root = inflater.inflate(R.layout.fragment_tab, container, false);
        recyclerView = root.findViewById(R.id.rv);
        mainRvAdapter = new MainRvAdapter(mList, true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mainRvAdapter.setOnItemClickListener(new MainRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GoodData data) {
                if (CommonData.getCommonData().getUserType() == Constants.BUYER) {
                    CommonUtil.showBuyDialog(TabFragment.this, data, null);
                } else {
                    CommonUtil.showGoodsEditDialog(TabFragment.this,data);
                }
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mainRvAdapter);
        return root;
    }

    void initData() {
        mCategory = getArguments().getString("label");
        mList.clear();
        for (int i = 0; i < 10; i++) {
            String title = "测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据" + i;
            String price = "" + (i * i);
            String seller = "张三";
            String category = mCategory;
            String url = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpicture.ik123.com%2Fuploads%2Fallimg%2F161203%2F3-1612030ZG5.jpg&refer=http%3A%2F%2Fpicture.ik123.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620888901&t=01c32eff8a057c03ef89b029b6aaa3f5";
            mList.add(new GoodData(title, price, url, seller, category));
        }
    }

}
