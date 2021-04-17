package com.newbie.easygo.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.listener.OnImageCompleteCallback;
import com.luck.picture.lib.tools.MediaUtils;
import com.luck.picture.lib.widget.longimage.ImageSource;
import com.luck.picture.lib.widget.longimage.ImageViewState;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.newbie.easygo.R;

/**
 * Glide图片加载框架
 */
public class GlideEngine implements ImageEngine {
    private static GlideEngine glideEngine = new GlideEngine();

    public static GlideEngine getInstance(){
        return glideEngine;
    }

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, SubsamplingScaleImageView longImageView, OnImageCompleteCallback callback) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(new  ImageViewTarget<Bitmap>(imageView) {
                          @Override
                          public void onLoadStarted(@Nullable Drawable placeholder) {
                              super.onLoadStarted(placeholder);
                              callback.onShowLoading();
                          }

                          @Override
                          public void onLoadFailed(@Nullable Drawable errorDrawable) {
                              super.onLoadFailed(errorDrawable);
                              callback.onHideLoading();
                          }

                          @Override
                          protected void setResource(@Nullable Bitmap resource) {
                              callback.onHideLoading();
                              if (resource != null) {
                                  boolean eqLongImage = MediaUtils.isLongImg(
                                          resource.getWidth(),
                                          resource.getHeight()
                                  );
                                  longImageView.setVisibility(eqLongImage? View.VISIBLE:View.GONE);
                                  imageView.setVisibility(eqLongImage?View.GONE:View.VISIBLE);
                                  if (eqLongImage) {
                                      // 加载长图
                                      longImageView.setQuickScaleEnabled(true);
                                      longImageView.setZoomEnabled(true);
                                      longImageView.setDoubleTapZoomDuration(100);
                                      longImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
                                      longImageView.setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER);
                                      longImageView.setImage(
                                              ImageSource.bitmap(resource),
                                              new ImageViewState(0F, new PointF(0F, 0F), 0)
                                      );
                                  } else {
                                      // 普通图片
                                      imageView.setImageBitmap(resource);
                                  }
                              }
                          }
                      });
    }

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, SubsamplingScaleImageView longImageView) {

    }

    @Override
    public void loadFolderImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .override(180, 180)
                .centerCrop()
                .sizeMultiplier(0.5f)
                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                .into(new  BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(8f);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
            });
    }

    @Override
    public void loadAsGifImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        Glide.with(context)
                .asGif()
                .load(url)
                .into(imageView);
    }

    @Override
    public void loadGridImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        Glide.with(context)
                .load(url)
                .override(200, 200)
                .centerCrop()
                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                .into(imageView);
    }
}
