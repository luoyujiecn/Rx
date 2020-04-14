package com.kxj.rx.http;


import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.kxj.rx.AppContext;
import com.kxj.rx.BuildConfig;
import com.kxj.rx.bean.InformationBean;
import com.kxj.rx.bean.IpBean;
import com.kxj.rx.bean.Weather;
import com.kxj.rx.http.request.RequestClient;
import com.kxj.rx.http.request.ServerAPI;
import com.kxj.rx.http.result.HttpResultFunc;
import com.kxj.rx.http.result.ServerListResult;
import com.kxj.rx.http.result.ServerResult;
import com.zchu.rxcache.RxCache;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.diskconverter.GsonDiskConverter;
import com.zchu.rxcache.stategy.CacheStrategy;

import java.io.File;

import rx.Observable;
import rx.functions.Func1;

/**
 * <p>
 * 负责提供数据，这里采用了 Repository 模式，DataManager 就是一个仓库管理员，业务层 需要什么东西只需告诉仓库管理员，由仓库管理员把东西拿给它，并不需要知道东西实际放在哪。
 * 常见的数据来源有，RestAPI、SQLite数据库、本地缓存等
 * <p>
 * ps：有些时候，访问数据压根就涉及不到什么业务逻辑，如：请求数据给一个列表展示，这就压根没有业务逻辑
 * 这时present直接访问数据层就可以了，当然最好还是写一个业务逻辑类，什么也不干，只是转发一下数据，以后突然有业务逻辑了，就只要关注这个业务逻辑类就可以了
 */

public class DataManager {

    private static final DataManager sInstance = new DataManager();

    private RxCache rxCache;

    private DataManager() {
        rxCache = new RxCache.Builder()
                .appVersion(BuildConfig.VERSION_CODE)
                .diskDir(new File(AppContext.context().getFilesDir().getPath() + File.separator + "data-cache"))
                .setDebug(BuildConfig.DEBUG)
                .diskConverter(new GsonDiskConverter())//支持Serializable、Json(GsonDiskConverter)
                .diskMax(50 * 1024 * 1024)
                .build();
    }

    public static DataManager getInstance() {
        return sInstance;
    }


    /**
     * 获取头条类资讯
     *
     * @param type 类型,,top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
     * @return
     */
    public Observable<ServerListResult<InformationBean>> getInformationList(String type) {
        Log.e("information", type);
        return RequestClient
                .getServerAPI()
                .getInformationList(ServerAPI.toutiaoKey, type)
                .map(new HttpResultFunc<InformationBean>())
                .compose(rxCache.<ServerListResult<InformationBean>>transformer("getInformationList" + type, new TypeToken<ServerListResult<InformationBean>>() {
                }.getType(), CacheStrategy.firstCache()))
                .map(new Func1<CacheResult<ServerListResult<InformationBean>>, ServerListResult<InformationBean>>() {
                    @Override
                    public ServerListResult<InformationBean> call(CacheResult<ServerListResult<InformationBean>> cacheResult) {
                        return cacheResult.getData();
                    }
                });
    }

    /**
     * 获取当前外网ip地址
     *
     * @return
     */
    public Observable<String> getIpAddress() {
        return RequestClient
                .getServerAPI(ServerAPI.IpUrl)
                .getIpAddress();
    }

    /**
     * 通过ip得到当前城市信息
     *
     * @param ip
     * @return
     */
    public Observable<ServerResult<IpBean>> getIpCity(String ip) {
        return RequestClient
                .getServerAPI(ServerAPI.APISURL)
                .getIpCity(ip, ServerAPI.ipKey)
               ;
    }

    /**
     * 通过城市得到当前天气信息
     *
     * @param city
     * @return
     */
    public Observable<ServerResult<Weather>> getWeather(String city) {
        return RequestClient
                .getServerAPI(ServerAPI.APISURL)
                .getWeather(city, ServerAPI.weatherkey)
               ;
    }


}
