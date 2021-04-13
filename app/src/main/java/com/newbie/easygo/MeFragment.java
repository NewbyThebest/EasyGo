package com.newbie.easygo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MeFragment extends Fragment implements View.OnClickListener {
    private ImageView photo;
    private TextView userName;
    private TextView phone;
    private TextView address;
    private final static int TYPE_USERNAME = 0;
    private final static int TYPE_PHONE = 1;
    private final static int TYPE_ADDRESS = 2;

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

    }

    void initView(View root) {
        phone = root.findViewById(R.id.phoneNumber);
        photo = root.findViewById(R.id.photo);
        userName = root.findViewById(R.id.userName);
        address = root.findViewById(R.id.address);
        phone.setOnClickListener(this);
        userName.setOnClickListener(this);
        address.setOnClickListener(this);
        photo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo:
                break;
            case R.id.userName:
                showDialog(TYPE_USERNAME);
                break;
            case R.id.phoneNumber:
                showDialog(TYPE_PHONE);
                break;
            case R.id.address:
                showDialog(TYPE_ADDRESS);
                break;
        }
    }

    void showDialog(int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_layout, null);
        TextView title = view.findViewById(R.id.msg);
        EditText editText = view.findViewById(R.id.editInfo);
        TextView close = view.findViewById(R.id.close);
        TextView update = view.findViewById(R.id.update);
        editText.requestFocus();
        builder.setView(view);
        if (type == TYPE_USERNAME) {
            title.setText("更新用户名：");
        } else if (type == TYPE_PHONE) {
            title.setText("更新手机号码：");
        } else {
            title.setText("更新收货地址：");
        }
        Dialog dialog = builder.create();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
