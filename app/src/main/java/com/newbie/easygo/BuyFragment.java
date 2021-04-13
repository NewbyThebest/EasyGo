package com.newbie.easygo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BuyFragment extends Fragment {
    private RecyclerView recyclerView;
    private MainRvAdapter mainRvAdapter;
    private List<GoodData> mList = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frgement_buy, container, false);
        initView(root);
        return root;
    }

    void initData(){
        mList.clear();
        for (int i = 0; i < 10; i++) {
            String title = "测试数据" + i;
            String price = "" + (i * i);
            String url = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpicture.ik123.com%2Fuploads%2Fallimg%2F161203%2F3-1612030ZG5.jpg&refer=http%3A%2F%2Fpicture.ik123.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620888901&t=01c32eff8a057c03ef89b029b6aaa3f5";
            mList.add(new GoodData(title,price,url));
        }
    }

    void initView(View root){
        recyclerView = root.findViewById(R.id.buy_rv);
        mainRvAdapter = new MainRvAdapter(mList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mainRvAdapter);
    }
}
