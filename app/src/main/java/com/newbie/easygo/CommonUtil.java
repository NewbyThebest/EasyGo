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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CommonUtil {
    public static void showGoodsEditDialog(BaseFragment fragment,  GoodData data) {
        EditDialogFragment dialogFragment = new EditDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",data);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(fragment.getChildFragmentManager(),"edit");
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
            Glide.with(fragment.getContext()).load(data.imgUrl).into(ivImg);
        }
        builder.setView(view);
        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.drawable.dialog_bg);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.title = etName.getText().toString();
                data.price = etPhone.getText().toString();
                data.category = address.getText().toString();
                fragment.updateView();
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
        ViewGroup layout = view.findViewById(R.id.layout);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.drawable.dialog_bg);
        if (userData != null) {
            user.setText(userData.title);
            phone.setText(userData.price);
            address.setText(userData.category);
        } else {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonUtil.showUserEditDialog(fragment, null);
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
                if (userData == null || TextUtils.isEmpty(userData.title)
                        || TextUtils.isEmpty(userData.price)
                        || TextUtils.isEmpty(userData.category)) {
                    Toast.makeText(fragment.getActivity(), "请先完善个人信息！", Toast.LENGTH_LONG).show();
                    CommonUtil.showUserEditDialog(fragment, null);
                    dialog.dismiss();
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
                String old = etName.getText().toString();
                String new1  = etPhone.getText().toString();
                String new2  = address.getText().toString();
                if (TextUtils.isEmpty(old) || TextUtils.isEmpty(new1) || TextUtils.isEmpty(new2)){
                    Toast.makeText(context, "密码不能为空，请重新输入！", Toast.LENGTH_LONG).show();
                }else if (!etPhone.getText().toString().equals(address.getText().toString())) {
                    Toast.makeText(context, "两次密码不相同，请重新输入！", Toast.LENGTH_LONG).show();
                }else {
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
    public static String imageToBase64(String path){
        if(TextUtils.isEmpty(path)){
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try{
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data,Base64.DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null !=is){
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
