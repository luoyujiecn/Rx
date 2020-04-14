package com.kxj.rx.http.interceptor;


import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Description 为请求添加header的过虑器
 */
public class HeaderInterceptor implements Interceptor {
    private Map<String, String> mHeader;

    public HeaderInterceptor(Map<String, String> header) {
        this.mHeader = header;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        for (String key : mHeader.keySet()) {
            builder.addHeader(key, mHeader.get(key));
        }
        return chain.proceed(builder.build());
    }
}
