package com.kxj.rx.http.result;

import java.util.List;

public class DataList<T> {
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataList{" +
                "data=" + data +
                '}';
    }
}
