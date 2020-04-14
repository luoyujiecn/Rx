package com.kxj.rx.http.request;


import com.kxj.rx.BuildConfig;
import com.kxj.rx.http.converter.GsonConverterFactory;
import com.kxj.rx.http.converter.NullOnEmptyConverterFactory;
import com.kxj.rx.http.interceptor.HeaderInterceptor;
import com.kxj.rx.http.interceptor.LoggingInterceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class RequestClient {


    private static volatile ServerAPI sServerAPI;//单例模式
    private static volatile ServerAPI otherServerAPI;//单例模式
    private static Map<String, ServerAPI> cacheApi = new HashMap<>();

    public static ServerAPI getServerAPI() {
        if (sServerAPI == null) {
            synchronized (RequestClient.class) {
                if (sServerAPI == null) {
                    OkHttpClient.Builder clientBuilder = getClientBuilder();
                    HashMap<String, String> headerMap = new HashMap<>();
                    headerMap.put("appver", String.valueOf(BuildConfig.VERSION_CODE));
                    clientBuilder.addInterceptor(new HeaderInterceptor(headerMap));
                    //配置日志拦截器
                    if (BuildConfig.DEBUG) {
                        clientBuilder
                                .addInterceptor(new LoggingInterceptor());
                    }
                    sServerAPI = getRetrofitBuilder(ServerAPI.BASE_URL, clientBuilder.build()).build().create(ServerAPI.class);
                }
            }
        }
        return sServerAPI;
    }


    public static ServerAPI getServerAPI(String BASEURL) {
        if (cacheApi.containsKey(BASEURL)) {
            return cacheApi.get(BASEURL);
        }
        synchronized (RequestClient.class) {
            OkHttpClient.Builder clientBuilder = getClientBuilder();
            HashMap<String, String> headerMap = new HashMap<>();
            headerMap.put("appver", String.valueOf(BuildConfig.VERSION_CODE));
            clientBuilder.addInterceptor(new HeaderInterceptor(headerMap));
            //配置日志拦截器
            if (BuildConfig.DEBUG) {
                clientBuilder
                        .addInterceptor(new LoggingInterceptor());
            }
            otherServerAPI = getRetrofitBuilder(BASEURL, clientBuilder.build()).build().create(ServerAPI.class);
            cacheApi.put(BASEURL, otherServerAPI);
        }
        return otherServerAPI;
    }


    /**
     * @param url    域名
     * @param client okhttp请求客户端
     * @return retrofit的构建器
     */
    private static Retrofit.Builder getRetrofitBuilder(String url, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client);
    }


    private static OkHttpClient.Builder getClientBuilder() {
        return new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.CONNECT_TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.READ_TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);//重试
        // .writeTimeout(HttpConfig.WRITE_TIME_OUT_SECONDS, TimeUnit.SECONDS);
    }
}
