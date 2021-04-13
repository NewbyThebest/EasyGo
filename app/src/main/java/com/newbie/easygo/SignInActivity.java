package com.newbie.easygo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
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

    boolean checkUserInfo() {
        boolean hasUser = false;
        String etUser = user.getText().toString();
        String etPsw = password.getText().toString();
        if (TextUtils.isEmpty(etUser)) {
            Toast.makeText(this, "用户名不能为空，请重新输入！", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(etPsw)) {
            Toast.makeText(this, "密码不能为空，请重新输入！", Toast.LENGTH_LONG).show();
            return false;
        }
        if (etUser.equals("1234")) {
            hasUser = true;
            Toast.makeText(this, "用户已存在，请重新输入！", Toast.LENGTH_LONG).show();
        }
        return !hasUser;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin:
                if (checkUserInfo()) {
                    Toast.makeText(this, "注册成功！", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
                break;
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("USER_TYPE", type);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
