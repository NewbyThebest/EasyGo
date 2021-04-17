package com.newbie.easygo.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.newbie.easygo.common.GlideEngine;
import com.newbie.easygo.common.MainManager;
import com.newbie.easygo.R;
import com.newbie.easygo.common.UpdateGoodsEvent;
import com.newbie.easygo.common.CommonData;
import com.newbie.easygo.common.CommonUtil;
import com.newbie.easygo.common.GoodData;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static com.newbie.easygo.common.Constants.BASE_IP;

public class EditDialogFragment extends DialogFragment {
    private GoodData goodData;
    private ImageView imageView;
    private String mImgPath;
    private String mTitle;
    private String mPrice;
    private String mCategory;
    private boolean mIsAdd;
    private String mImgTemp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.dialogStyle);
        goodData = (GoodData) getArguments().getSerializable("data");
        if (goodData == null) {
            mIsAdd = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_goods_edit, null);
        EditText etTitle = view.findViewById(R.id.et_title);
        EditText etPrice = view.findViewById(R.id.et_price);
        imageView = view.findViewById(R.id.iv_img);
        Button delete = view.findViewById(R.id.delete);
        Button commit = view.findViewById(R.id.commit);
        Button close = view.findViewById(R.id.close);
        AppCompatSpinner spinner = view.findViewById(R.id.spinner);
        String[] tabs = CommonData.getCommonData().getTabs();
        int position = 0;
        if (!mIsAdd) {
            if (!TextUtils.isEmpty(goodData.category)) {
                for (int i = 0; i < tabs.length; i++) {
                    if (goodData.category.equals(tabs[i])) {
                        position = i;
                    }
                }
            }
            etTitle.setText(goodData.title);
            etPrice.setText(goodData.price);
            mImgPath = goodData.imgUrl;
            mImgTemp = mImgPath;
            Glide.with(this).load(mImgPath).into(imageView);
        }else {
            delete.setVisibility(View.GONE);
        }
        mCategory = tabs[position];


        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, tabs);
        spinner.setAdapter(adapter);
        spinner.setSelection(position);
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(R.drawable.dialog_bg);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitle = etTitle.getText().toString();
                mPrice = etPrice.getText().toString();
                if (TextUtils.isEmpty(mTitle)) {
                    Toast.makeText(getActivity(), "标题不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mPrice)) {
                    Toast.makeText(getActivity(), "价格不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mImgPath)) {
                    Toast.makeText(getActivity(), "图片为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mIsAdd) {
                    uploadImg(mImgPath);
                } else {
                    if (mImgPath.equals(mImgTemp)) {
                        updateGoodsInfo(null);
                    } else {
                        uploadImg(mImgPath);
                    }
                }


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
                deleteGoodsInfo();
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCategory = tabs[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                mImgPath = list.get(0).getPath();
                GlideEngine.getInstance().loadImage(getContext(), mImgPath, imageView);
            }
        }
    }

    void uploadImg(String path) {
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
                            if (mIsAdd) {
                                addGoodsInfo(result.imgUrl);
                            } else {
                                updateGoodsInfo(result.imgUrl);
                            }
                        }
                    }
                });
    }

    void deleteGoodsInfo(){
        Map<String, String> map = new HashMap<>();
        map.put("uid", goodData.uid);
        MainManager.getInstance().getNetService().deleteGoods(map)
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
                            Toast.makeText(getContext(), "删除商品成功！", Toast.LENGTH_LONG).show();
                            EventBus.getDefault().post(new UpdateGoodsEvent());
                            getDialog().dismiss();
                        } else {
                            Toast.makeText(getContext(), "删除商品失败，请重试！", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    void updateGoodsInfo(String name) {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(name)) {
            String url = "http://" + BASE_IP + "/EasyGo/img/" + name;
            map.put("img", url);
        } else {
            map.put("img", goodData.imgUrl);
        }
        map.put("uid", goodData.uid);
        map.put("title", mTitle);
        map.put("price", mPrice);
        map.put("category", mCategory);
        map.put("buyerId", goodData.buyerId);
        MainManager.getInstance().getNetService().updateGoods(map)
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
                            Toast.makeText(getContext(), "更新商品成功！", Toast.LENGTH_LONG).show();
                            EventBus.getDefault().post(new UpdateGoodsEvent());
                            getDialog().dismiss();
                        } else {
                            Toast.makeText(getContext(), "更新商品失败，请重试！", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    /**
     * 添加商品信息
     * @param name
     */
    void addGoodsInfo(String name) {
        Map<String, String> map = new HashMap<>();
        String url = "http://" + BASE_IP + "/EasyGo/img/" + name;
        map.put("img", url);
        map.put("title", mTitle);
        map.put("price", mPrice);
        map.put("seller", CommonData.getCommonData().getUserInfo().title);
        map.put("category", mCategory);
        map.put("sellerId", CommonData.getCommonData().getUserInfo().uid);
        MainManager.getInstance().getNetService().addGoods(map)
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
                            Toast.makeText(getContext(), "添加商品成功！", Toast.LENGTH_LONG).show();
                            EventBus.getDefault().post(new UpdateGoodsEvent());
                            getDialog().dismiss();
                        } else {
                            Toast.makeText(getContext(), "添加商品失败，请重试！", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
