package com.newbie.easygo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class MeFragment extends BaseFragment implements View.OnClickListener {
    private ImageView photo;
    private TextView userName;
    private TextView phone;
    private TextView address;
    private Button button;
    private Button changePsw;
    private Button logout;
    private final static int TYPE_USERNAME = 0;
    private final static int TYPE_PHONE = 1;
    private final static int TYPE_ADDRESS = 2;
    private GoodData mData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frgment_me, container, false);
        initView(root);
        return root;
    }

    void initData() {
        String name = "张三";
        String phone = "12345568901";
        String address = "广东深圳市南山区腾讯科技大厦";
        String url = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpicture.ik123.com%2Fuploads%2Fallimg%2F161203%2F3-1612030ZG5.jpg&refer=http%3A%2F%2Fpicture.ik123.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620888901&t=01c32eff8a057c03ef89b029b6aaa3f5";
        mData = new GoodData(name, phone, url, "", address);
    }


    void initView(View root) {
        phone = root.findViewById(R.id.phoneNumber);
        photo = root.findViewById(R.id.photo);
        userName = root.findViewById(R.id.userName);
        address = root.findViewById(R.id.address);
        button = root.findViewById(R.id.updateBtn);
        changePsw = root.findViewById(R.id.updatePsw);
        logout = root.findViewById(R.id.logout);

        if (!TextUtils.isEmpty(mData.title)) {
            userName.setText(mData.title);
        }
        if (!TextUtils.isEmpty(mData.price)) {
            phone.setText(mData.price);
        }
        if (!TextUtils.isEmpty(mData.category)) {
            address.setText(mData.category);
        }
        if (CommonData.getCommonData().getUserType() == Constants.SELLER) {
            TextView addressTitle = root.findViewById(R.id.addressTitle);
            addressTitle.setText("商家地址");
        }
        Glide.with(getContext()).load(mData.imgUrl).into(photo);
        button.setOnClickListener(this);
        photo.setOnClickListener(this);
        changePsw.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @Override
    public void updateView() {
        super.updateView();
        if (!TextUtils.isEmpty(mData.title)) {
            userName.setText(mData.title);
        }
        if (!TextUtils.isEmpty(mData.price)) {
            phone.setText(mData.price);
        }
        if (!TextUtils.isEmpty(mData.category)) {
            address.setText(mData.category);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateBtn:
                MainManager.getInstance().showUserEditDialog(this, mData);
                break;
            case R.id.photo:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .imageEngine(new GlideEngine())
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.updatePsw:
                MainManager.getInstance().showPswEditDialog(getContext());
                break;
            case R.id.logout:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
            if (list != null && !list.isEmpty()) {
                mData.setImgUrl(list.get(0).getPath());
                GlideEngine.getInstance().loadImage(getContext(), list.get(0).getPath(), photo);
            }
        }
    }

}
