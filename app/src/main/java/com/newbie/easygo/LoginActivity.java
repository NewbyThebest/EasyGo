package com.newbie.easygo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public final static int REQUEST_CODE = 1024;
    private final static int BUYER = 0;
    private final static int SELLER = 1;
    private Button login;
    private EditText user;
    private EditText password;
    private TextView signin;
    private TextView forget;
    private TextView userType;
    private SwitchCompat switchUser;
    private ImageView eye;
    private boolean isOpen;
    private int type = BUYER;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        user = findViewById(R.id.username);
        password = findViewById(R.id.password);
        eye = findViewById(R.id.eye);
        login = findViewById(R.id.login);
        signin = findViewById(R.id.sign);
        forget = findViewById(R.id.forget);
        userType = findViewById(R.id.userType);
        switchUser = findViewById(R.id.switch_user);
        login.setOnClickListener(this);
        eye.setOnClickListener(this);
        signin.setOnClickListener(this);
        forget.setOnClickListener(this);
        userType.setOnClickListener(this);
        switchUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = SELLER;
                    userType.setText("卖家登录");
                } else {
                    type = BUYER;
                    userType.setText("买家登录");
                }
            }
        });
    }

    boolean checkUserInfo() {
        boolean hasUser = false;
        boolean correctPsw = false;
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
        } else {
            Toast.makeText(this, "用户不存在，请重新输入！", Toast.LENGTH_LONG).show();
        }
        if (etPsw.equals("1234")) {
            correctPsw = true;
        } else {
            if (hasUser) {
                Toast.makeText(this, "密码不正确，请重新输入！", Toast.LENGTH_LONG).show();
            }
        }
        return hasUser && correctPsw;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (checkUserInfo()) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("USER_TYPE", type);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.eye:
                isOpen = !isOpen;
                if (isOpen) {
                    eye.setBackgroundResource(R.drawable.ic_eye_open);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    eye.setBackgroundResource(R.drawable.ic_eye_close);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
                password.setSelection(password.getText().toString().length());
                break;
            case R.id.sign:
                Intent intent = new Intent(this, SignInActivity.class);
                intent.putExtra("USER_TYPE", type);
                startActivityForResult(intent, REQUEST_CODE);
                break;

            case R.id.forget:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("请联系作者寻求帮助\nqq:123456789");
                builder.setPositiveButton("我已知晓", null);
                Dialog dialog = builder.create();
                dialog.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            type = data.getIntExtra("USER_TYPE", BUYER);
            if (type == BUYER) {
                userType.setText("买家登录");
                switchUser.setChecked(false);
            } else {
                userType.setText("卖家登录");
                switchUser.setChecked(true);
            }
        }
    }
}
