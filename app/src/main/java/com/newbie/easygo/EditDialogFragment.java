package com.newbie.easygo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class EditDialogFragment extends DialogFragment {
    private GoodData goodData;
    private ImageView imageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.dialogStyle);
        goodData = (GoodData) getArguments().getSerializable("data");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_goods_edit,null);
        EditText etTitle = view.findViewById(R.id.et_title);
        EditText etPrice = view.findViewById(R.id.et_price);
        imageView = view.findViewById(R.id.iv_img);
        Button delete = view.findViewById(R.id.delete);
        Button commit = view.findViewById(R.id.commit);
        Button close = view.findViewById(R.id.close);
        if (goodData != null) {
            etTitle.setText(goodData.title);
            etPrice.setText(goodData.price);
            Glide.with(this).load(goodData.imgUrl).into(imageView);
        }
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(R.drawable.dialog_bg);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String price = etPrice.getText().toString();
                if (!TextUtils.isEmpty(title)){
                    goodData.setTitle(title);
                }
                if (!TextUtils.isEmpty(price)){
                    goodData.setPrice(price);
                }
                EventBus.getDefault().post(new UpdateGoodsEvent());
                getDialog().dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new UpdateGoodsEvent());
                getDialog().dismiss();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(EditDialogFragment.this)
                        .openGallery(PictureMimeType.ofImage())
                        .imageEngine(new GlideEngine())
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
            if (list != null && !list.isEmpty()) {
                goodData.setImgUrl(list.get(0).getPath());
                GlideEngine.getInstance().loadImage(getContext(), list.get(0).getPath(), imageView);
            }
        }
    }
}
