package com.kxj.rx.basemvp;

/**
 * Created by Chu on 2017/5/29.
 */

public interface BaseLcedView<M> extends BaseLceView {

    /**
     * 设置数据
     *
     */
    void setData(M data);

}
