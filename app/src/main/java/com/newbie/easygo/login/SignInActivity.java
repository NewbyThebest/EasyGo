package com.newbie.easygo.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import com.newbie.easygo.R;
import com.newbie.easygo.common.BaseActivity;
import com.newbie.easygo.common.MainManager;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SignInActivity extends BaseActivity implements View.OnClickListener {
    private final static int BUYER = 0;
    private final static int SELLER = 1;
    private Button sign;
    private Button back;
    private EditText user;
    private EditText password;
    private TextView userType;
    private SwitchCompat switchUser;
    private int type = BUYER;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        sign = findViewById(R.id.signin);
        user = findViewById(R.id.username);
        password = findViewById(R.id.password);
        back = findViewById(R.id.back);
        userType = findViewById(R.id.userType);
        switchUser = findViewById(R.id.switch_user);
        sign.setOnClickListener(this);
        back.setOnClickListener(this);
        type = getIntent().getIntExtra("USER_TYPE", BUYER);
        if (type == SELLER) {
            switchUser.setChecked(true);
            userType.setText("卖家注册");
        }
        userType.setOnClickListener(this);
        switchUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = SELLER;
                    userType.setText("卖家注册");
                } else {
                    type = BUYER;
                    userType.setText("买家注册");
                }
            }
        });
    }

    /**
     * 检查是否已有用户
     */
    void checkUserInfo() {
        String etUser = user.getText().toString();
        String etPsw = password.getText().toString();
        if (TextUtils.isEmpty(etUser)) {
            Toast.makeText(this, "用户名不能为空，请重新输入！", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(etPsw)) {
            Toast.makeText(this, "密码不能为空，请重新输入！", Toast.LENGTH_LONG).show();
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("uid", etUser);
        map.put("password", etPsw);
        map.put("type", String.valueOf(type));

        MainManager.getInstance().getNetService().addUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(SignInActivity.this, "网络不佳，请重试！", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(Boolean result) {
                        if (result) {
                            finish();
                            Toast.makeText(SignInActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(SignInActivity.this, "用户已存在，请重新输入！", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 点击注册
             */
            case R.id.signin:
                checkUserInfo();
                break;
            /**
             * 点击返回
             */
            case R.id.back:
                onBackPressed();
                break;
        }
    }


    /**
     * 返回登陆界面
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("USER_TYPE", type);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
