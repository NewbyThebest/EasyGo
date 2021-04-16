package com.newbie.easygo;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.newbie.easygo.Constants.BASE_IP;

public class CommonUtil {
    public static void showGoodsEditDialog(BaseFragment fragment, GoodData data) {
        EditDialogFragment dialogFragment = new EditDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(fragment.getChildFragmentManager(), "edit");
    }

    public static void showUserEditDialog(BaseFragment fragment, GoodData data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity());
        View view = LayoutInflater.from(fragment.getActivity()).inflate(R.layout.dialog_user_edit, null, false);
        EditText etName = view.findViewById(R.id.et_name);
        EditText etPhone = view.findViewById(R.id.et_phone);
        ImageView ivImg = view.findViewById(R.id.iv_img);
        EditText address = view.findViewById(R.id.et_address);
        Button commit = view.findViewById(R.id.commit);
        Button close = view.findViewById(R.id.close);
        if (data != null) {
            etName.setText(data.title);
            etPhone.setText(data.price);
            address.setText(data.category);
            if (!TextUtils.isEmpty(data.imgUrl)){
                Glide.with(fragment.getContext()).load(data.imgUrl).into(ivImg);
            }
        }
        builder.setView(view);
        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.drawable.dialog_bg);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                String addr = address.getText().toString();

                Map<String, String> map = new HashMap<>();
                map.put("img", CommonData.getCommonData().getUserInfo().imgUrl);
                map.put("password", CommonData.getCommonData().getUserInfo().seller);
                map.put("phone", phone);
                map.put("address", addr);
                map.put("name", name);
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
                                e.printStackTrace();
                                Toast.makeText(fragment.getContext(), "网络不佳，请重试！", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onNext(Boolean result) {
                                if (result) {
                                    data.title = name;
                                    data.price = phone;
                                    data.category = addr;
                                    CommonData.getCommonData().setUserInfo(data);
                                    fragment.updateView();
                                    Toast.makeText(fragment.getContext(), "更新信息成功！", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(fragment.getContext(), "更新信息失败，请重试！", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                dialog.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog.show();
    }

    public static void showBuyDialog(BaseFragment fragment, GoodData goodData, GoodData userData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity());
        View view = LayoutInflater.from(fragment.getActivity()).inflate(R.layout.dialog_buy, null, false);
        Button commit = view.findViewById(R.id.commit);
        Button close = view.findViewById(R.id.close);
        TextView user = view.findViewById(R.id.userName);
        TextView phone = view.findViewById(R.id.phone);
        TextView address = view.findViewById(R.id.address);
        TextView price = view.findViewById(R.id.price);
        TextView title = view.findViewById(R.id.title);
        TextView seller = view.findViewById(R.id.seller);
        ImageView img = view.findViewById(R.id.img);
        TextView category = view.findViewById(R.id.tv_category);
        TextView emptyView = view.findViewById(R.id.empty_view);
        ViewGroup layout = view.findViewById(R.id.layout);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.drawable.dialog_bg);
        user.setText(userData.title);
        phone.setText(userData.price);
        address.setText(userData.category);
        if (TextUtils.isEmpty(userData.title)){
            emptyView.setVisibility(View.VISIBLE);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonUtil.showUserEditDialog(fragment, userData);
                    dialog.dismiss();
                }
            });
        }
        if (goodData != null) {
            title.setText(goodData.title);
            price.setText(goodData.price);
            seller.setText(goodData.seller);
            category.setText("类别：" + goodData.category);
            Glide.with(fragment.getContext()).load(goodData.imgUrl).into(img);
        }

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(userData.title) || TextUtils.isEmpty(userData.price) || TextUtils.isEmpty(userData.category)) {
                    Toast.makeText(fragment.getActivity(), "请先完善个人信息！", Toast.LENGTH_LONG).show();
                    CommonUtil.showUserEditDialog(fragment, userData);
                    dialog.dismiss();
                }else {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("uid", goodData.uid);
                    map.put("img",goodData.imgUrl);
                    map.put("title", goodData.title);
                    map.put("price", goodData.price);
                    map.put("category", goodData.category);
                    map.put("buyerId", CommonData.getCommonData().getUserInfo().uid);
                    MainManager.getInstance().getNetService().updateGoods(map)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Boolean>() {
                                @Override
                                public void onCompleted() {
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(fragment.getContext(), "网络不佳，请重试！", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onNext(Boolean result) {
                                    if (result) {
                                        Toast.makeText(fragment.getContext(), "购买商品成功！", Toast.LENGTH_LONG).show();
                                        EventBus.getDefault().post(new UpdateGoodsEvent());
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(fragment.getContext(), "购买商品失败，请重试！", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                dialog.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void showPswEditDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_psw_edit, null, false);
        EditText etName = view.findViewById(R.id.et_name);
        EditText etPhone = view.findViewById(R.id.et_phone);
        EditText address = view.findViewById(R.id.et_address);
        Button commit = view.findViewById(R.id.commit);
        Button close = view.findViewById(R.id.close);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.drawable.dialog_bg);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodData data = CommonData.getCommonData().getUserInfo();
                String old = etName.getText().toString();
                String new1 = etPhone.getText().toString();
                String new2 = address.getText().toString();
                if (TextUtils.isEmpty(old) || TextUtils.isEmpty(new1) || TextUtils.isEmpty(new2)) {
                    Toast.makeText(context, "密码不能为空，请重新输入！", Toast.LENGTH_LONG).show();
                } else if (!old.equals(data.seller)) {
                    Toast.makeText(context, "当前密码不正确，请重新输入！", Toast.LENGTH_LONG).show();
                } else if (!etPhone.getText().toString().equals(address.getText().toString())) {
                    Toast.makeText(context, "两次密码不相同，请重新输入！", Toast.LENGTH_LONG).show();
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("img", CommonData.getCommonData().getUserInfo().imgUrl);
                    map.put("password", new1);
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
                                    Toast.makeText(context, "网络不佳，请重试！", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onNext(Boolean result) {
                                    if (result) {
                                        CommonData.getCommonData().getUserInfo().seller = new1;
                                        Toast.makeText(context, "更新密码成功！", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(context, "更新密码失败，请重试！", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    dialog.dismiss();
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }
}
