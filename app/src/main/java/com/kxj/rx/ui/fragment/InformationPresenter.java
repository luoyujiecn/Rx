package com.kxj.rx.ui.fragment;

import com.kxj.rx.AppContext;
import com.kxj.rx.basemvp.BaseRxPresenter;
import com.kxj.rx.bean.InformationBean;
import com.kxj.rx.http.DataManager;
import com.kxj.rx.http.result.ServerListResult;
import com.kxj.rx.ui.adapter.InformationAdapter;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InformationPresenter extends BaseRxPresenter<InformationCantract.View> implements InformationCantract.Persenter {
    private InformationAdapter adapter;

    @Override
    public void loadData(String type) {
        DataManager.getInstance().getInformationList(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ServerListResult<InformationBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(ServerListResult<InformationBean> informationBeanServerResult) {
                        adapter = new InformationAdapter(AppContext.context(), informationBeanServerResult.getResult().getData());
                        getView().setAdapter(adapter);
                    }
                });
    }
}
