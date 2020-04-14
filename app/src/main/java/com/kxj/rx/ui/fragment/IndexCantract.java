package com.kxj.rx.ui.fragment;

import com.kxj.rx.basemvp.BaseLceView;
import com.kxj.rx.bean.Weather;
import com.kxj.rx.mvp.MvpPresenter;

public interface IndexCantract {
    interface View extends BaseLceView {
        void setWeather(Weather weather);
    }

    interface Persenter extends MvpPresenter<IndexCantract.View> {
        void loadWeather();
    }
}
