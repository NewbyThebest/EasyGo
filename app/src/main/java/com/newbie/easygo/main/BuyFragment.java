package com.newbie.easygo.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newbie.easygo.common.CommonData;
import com.newbie.easygo.common.Constants;
import com.newbie.easygo.common.GoodData;
import com.newbie.easygo.common.MainManager;
import com.newbie.easygo.R;
import com.newbie.easygo.common.UpdateGoodsEvent;
import com.newbie.easygo.common.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 售出界面或者购买界面
 */
public class BuyFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private MainRvAdapter mainRvAdapter;
    private List<GoodData> mList = new ArrayList<>();
    private ImageView mEmptyView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frgement_buy, container, false);
        initView(root);
        return root;
    }


    /**
     * 从服务器请求购买记录或者售出记录
     */
    void initData() {
        Map<String, String> map = new HashMap<>();
        if (CommonData.getCommonData().getUserType() == Constants.SELLER){
            map.put("sellerId", CommonData.getCommonData().getUserInfo().uid);
            MainManager.getInstance().getNetService().querySellerGoods(map)
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
                            if (mainRvAdapter != null){
                                mainRvAdapter.setList(mList);
                                mainRvAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }else {
            map.put("buyerId", CommonData.getCommonData().getUserInfo().uid);
            MainManager.getInstance().getNetService().queryBuyerGoods(map)
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
                            if (mainRvAdapter != null){
                                mainRvAdapter.setList(mList);
                                mainRvAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateGoodsEvent event) {
        updateView();
    }

    @Override
    public void updateView() {
        initData();
    }

    /**
     * 初始化界面
     * @param root
     */
    void initView(View root) {
        recyclerView = root.findViewById(R.id.buy_rv);
        mEmptyView = root.findViewById(R.id.empty_view);
        if (mList.isEmpty()){
            mEmptyView.setVisibility(View.VISIBLE);
        }else {
            mEmptyView.setVisibility(View.GONE);
        }
        mainRvAdapter = new MainRvAdapter(mList, false);
        if (CommonData.getCommonData().getUserType() == Constants.SELLER) {
            TextView title = root.findViewById(R.id.title);
            title.setText("售出记录");
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mainRvAdapter);
    }
}
