package com.kxj.rx.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.kxj.rx.R;

import java.io.File;

public class ImageLoaderManager {
    /**
     * 默认加载方式
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .priority(Priority.HIGH)
                .placeholder(R.drawable.imageloader)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate();

        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .priority(Priority.HIGH)
                .dontAnimate()
                .placeholder(R.drawable.imageloader)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new CircleCrop());

        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * 加载圆角图片
     * @param context
     * @param url
     * @param imageView
     * @param radius 圆角大小
     */
    public static void loadRoundImage(Context context, String url, ImageView imageView, int radius) {
        RequestOptions requestOptions = new RequestOptions()
                .priority(Priority.HIGH)
                .dontAnimate()
                .placeholder(R.drawable.imageloader)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transforms(new CenterCrop(), new RoundedCorners(radius));

        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * 加载图片指定大小
     *
     * @param context
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public static void loadSizeImage(Context context, String url, ImageView imageView, int width, int height) {
        RequestOptions requestOptions = new RequestOptions()
                .priority(Priority.HIGH)
                .placeholder(R.drawable.imageloader)
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * 加载本地图片文件
     *
     * @param context
     * @param file
     * @param imageView
     */
    public static void loadFileImage(Context context, File file, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .priority(Priority.HIGH)
                .placeholder(R.drawable.imageloader)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        Glide.with(context)
                .load(file)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载高斯模糊
     *
     * @param context
     * @param url
     * @param imageView
     * @param radius      模糊级数 最大25
     */
    public static void loadBlurImage(Context context, String url, ImageView imageView, int radius) {
        RequestOptions requestOptions = new RequestOptions()
                .override(300)
                .placeholder(R.drawable.imageloader)
                .transforms(new BlurTransformation());

        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * 加载gif图
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadGifImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }
}
