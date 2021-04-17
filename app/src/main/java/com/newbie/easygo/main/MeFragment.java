package com.newbie.easygo.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.newbie.easygo.R;
import com.newbie.easygo.common.BaseFragment;
import com.newbie.easygo.common.CommonData;
import com.newbie.easygo.common.CommonUtil;
import com.newbie.easygo.common.Constants;
import com.newbie.easygo.common.GlideEngine;
import com.newbie.easygo.common.GoodData;
import com.newbie.easygo.common.MainManager;
import com.newbie.easygo.login.LoginActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static com.newbie.easygo.common.Constants.BASE_IP;

/**
 * 个人信息的界面
 */
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
        mData = CommonData.getCommonData().getUserInfo();
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
        if (!TextUtils.isEmpty(mData.imgUrl)){
            Glide.with(getContext()).load(mData.imgUrl).into(photo);
        }
        button.setOnClickListener(this);
        photo.setOnClickListener(this);
        changePsw.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    /**
     * 更新界面
     */
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
            /**
             * 点击更新按钮
             */
            case R.id.updateBtn:
                CommonUtil.showUserEditDialog(this, mData);
                break;

            /**
             * 点击头像
             */
            case R.id.photo:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .imageEngine(new GlideEngine())
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;

            /**
             * 点击更新密码
             */
            case R.id.updatePsw:
                CommonUtil.showPswEditDialog(getContext());
                break;

            /**
             * 登出账号
             */
            case R.id.logout:
                CommonData.getCommonData().setUserInfo(new GoodData());
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
                LocalMedia media = list.get(0);
                mData.setImgUrl(media.getPath());
                uploadImg(media.getPath());
            }
        }
    }

    /**
     * 上传头像
     * @param path
     */
    void uploadImg(String path){
        String img = CommonUtil.imageToBase64(path);
        Map<String, String> map = new HashMap<>();
        map.put("img", img);
        MainManager.getInstance().getNetService().uploadImg(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GoodData>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "网络不佳，请重试！", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(GoodData result) {
                        if (!TextUtils.isEmpty(result.imgUrl)) {
                            updateUserInfo(result.imgUrl);
                        }
                    }
                });
    }

    /**
     * 更新用户信息
     * @param name
     */
    void updateUserInfo(String name){
        Map<String, String> map = new HashMap<>();
        String url = "http://" + BASE_IP + "/EasyGo/img/" + name;
        map.put("img", url);
        map.put("password", CommonData.getCommonData().getUserInfo().seller);
        map.put("phone", CommonData.getCommonData().getUserInfo().price);
        map.put("address", CommonData.getCommonData().getUserInfo().category);
        map.put("name", CommonData.getCommonData().getUserInfo().title);
        map.put("uid", CommonData.getCommonData().getUserInfo().uid);
        MainManager.getInstance().getNetService().updateUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "网络不佳，请重试！", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(Boolean result) {
                        if (result) {
                            GlideEngine.getInstance().loadImage(getContext(), url, photo);
                        }else {
                            Toast.makeText(getContext(), "更新头像失败，请重试！", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
