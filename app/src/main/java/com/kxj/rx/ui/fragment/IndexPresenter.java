package com.kxj.rx.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.kxj.rx.basemvp.BaseRxPresenter;
import com.kxj.rx.bean.IpBean;
import com.kxj.rx.bean.Weather;
import com.kxj.rx.http.DataManager;
import com.kxj.rx.http.result.ServerListResult;
import com.kxj.rx.http.result.ServerResult;
import com.kxj.rx.util.StreamUtils;

import java.io.IOException;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IndexPresenter extends BaseRxPresenter<IndexCantract.View> implements IndexCantract.Persenter {
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                getIPCity((String) msg.obj);
            }
        }
    };


    @Override
    public void loadWeather() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String ip = StreamUtils.urlToString("https://api.ip.sb/ip", "utf8");
                    Message message = new Message();
                    message.what = 1;
                    message.obj = ip;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    getView().showError(e.getMessage());
                }
            }
        }).start();
    }

    private void getIPCity(String ip) {
        DataManager.getInstance().getIpCity(ip).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ServerResult<IpBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ServerResult<IpBean> ipBeanServerResult) {
                        getWeather(ipBeanServerResult.getResult().getCity());
                    }
                });
    }


    private void getWeather(String city) {
        if (city.contains("市")) {
            city.replaceAll("市", "");
        }
        DataManager.getInstance().getWeather(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ServerResult<Weather>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ServerResult<Weather> weatherServerResult) {
                        getView().setWeather(weatherServerResult.getResult());
                    }
                });
    }


}
