package com.newbie.easygo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BuyActivity extends AppCompatActivity implements View.OnClickListener {
    private Button commit;
    private Button close;
    private TextView user;
    private TextView phone;
    private TextView address;
    private TextView price;
    private TextView title;
    private TextView seller;
    private ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        commit = findViewById(R.id.commit);
        close = findViewById(R.id.close);
        user = findViewById(R.id.userName);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        price = findViewById(R.id.price);
        title = findViewById(R.id.title);
        seller = findViewById(R.id.seller);
        img = findViewById(R.id.photo);
        initData();
    }

    void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commit:

                break;
            case R.id.close:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
