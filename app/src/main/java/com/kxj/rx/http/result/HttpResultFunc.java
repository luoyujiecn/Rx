package com.kxj.rx.http.result;


import rx.functions.Func1;

/**
 * Created by zchu on 17-2-6.
 * 用于map操作符，只想拿HttpResult.data的数据
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 */

public class HttpResultFunc<T> implements Func1<ServerListResult<T>, ServerListResult<T>> {

    @Override
    public ServerListResult<T> call(ServerListResult<T> tServerResult) {
        return tServerResult;
    }
}
