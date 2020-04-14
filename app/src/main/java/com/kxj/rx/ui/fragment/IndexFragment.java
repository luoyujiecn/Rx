package com.kxj.rx.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kxj.rx.R;
import com.kxj.rx.basemvp.BaseFragment;
import com.kxj.rx.bean.Weather;
import com.kxj.rx.mvp.MvpFragment;
import com.kxj.rx.util.ToastUtil;

public class IndexFragment extends MvpFragment<IndexCantract.Persenter> implements IndexCantract.View {
    private ImageView imageView;
    private TextView temperature, info, direct, power,today;


    @Override
    protected int setLayout() {
        return R.layout.index_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = $(R.id.weatherPicture);
        temperature = $(R.id.temperature);
        info = $(R.id.info);
        direct = $(R.id.direct);
        power = $(R.id.power);
        today = $(R.id.today);
    }

    @Override
    public void setWeather(Weather weather) {
        temperature.setText(weather.getRealtime().getTemperature());
        info.setText(weather.getRealtime().getInfo());
        direct.setText(weather.getRealtime().getDirect());
        power.setText(weather.getRealtime().getPower());
        
    }

    @Override
    protected void initData() {
        getPresenter().loadWeather();
    }

    @Override
    public void showContent() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        ToastUtil.showToast(errorMsg);
    }

    @NonNull
    @Override
    public IndexCantract.Persenter createPresenter() {
        return new IndexPresenter();
    }


}
