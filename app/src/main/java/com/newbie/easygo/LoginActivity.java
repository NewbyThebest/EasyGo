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

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.newbie.easygo.Constants.BUYER;
import static com.newbie.easygo.Constants.SELLER;


public class LoginActivity extends BaseActivity implements View.OnClickListener {
    public final static int REQUEST_CODE = 1024;
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
        MainManager.getInstance().getNetService().checkUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GoodData>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(LoginActivity.this, "网络不佳，请重试！", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(GoodData result) {
                        if (result != null && !TextUtils.isEmpty(result.getUid())) {
                            CommonData.getCommonData().setUserType(type);
                            CommonData.getCommonData().setUserInfo(result);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "账号或密码错误，请重新输入！", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                checkUserInfo();
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
