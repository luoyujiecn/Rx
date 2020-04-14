package com.kxj.rx.ui.fragment;

import com.kxj.rx.basemvp.BaseLceView;
import com.kxj.rx.mvp.MvpPresenter;
import com.kxj.rx.mvp.MvpView;
import com.kxj.rx.ui.adapter.InformationAdapter;

public interface InformationCantract {

    interface View extends BaseLceView {
        void setAdapter(InformationAdapter adapter);
    }

    interface Persenter extends MvpPresenter<View> {
        void loadData(String type);
    }
}
