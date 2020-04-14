package com.kxj.rx.bean;

import java.util.List;

/**
 * 通过城市名称或城市ID查询天气预报情况
 */
public class Weather {

    private String city;
    private Realtime realtime;
    private List<Future> future;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Realtime getRealtime() {
        return realtime;
    }

    public void setRealtime(Realtime realtime) {
        this.realtime = realtime;
    }

    public List<Future> getFuture() {
        return future;
    }

    public void setFuture(List<Future> future) {
        this.future = future;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "city='" + city + '\'' +
                ", realtime=" + realtime +
                ", future=" + future +
                '}';
    }
}
