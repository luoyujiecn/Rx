package com.kxj.rx.http.request;


import com.kxj.rx.bean.InformationBean;
import com.kxj.rx.bean.IpBean;
import com.kxj.rx.bean.JokeBean;
import com.kxj.rx.bean.Weather;
import com.kxj.rx.http.result.ServerListResult;
import com.kxj.rx.http.result.ServerResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ServerAPI {

    String BASE_URL = "http://v.juhe.cn";
    String IpUrl = "https://api.ip.sb";
    String APISURL = "http://apis.juhe.cn";
    String toutiaoKey = "02249748bf127a031ba7ee1593eaec76";//新闻头条
    String jokeKey = "1addfdaf68180bed30626e1ac29edcf2";//笑话大全
    String lishi_today = "8e61839a8a5f89576e3926aa8fd8d3b1";//历史上的今天
    String weatherkey = "a5df206da21d30ad838ec86a01c3b58e";//天气预报
    String xingzuo_peidui_key = "96c854258505f1ff5e9a2356ff43b4dc";//星座配对
    String QQceXiongJi = "261358203be8d1fc3c181299435db2a8";//QQ号码测吉凶
    String wangNianLi = "f6ccc962353a870a8d46a4763eb788f1";//万年历
    String zhouGongJieMeng = "c331f82f5d85b77dd281bdb7df4923b8";//周公解梦
    String huangJinShuju = "7c3d20c0896aa16115b0ab1121dc727b";//黄金数据
    String laoHuangLi = "1e22658cd238c7b18cbd089440f73e33";//老黄历
    String guoNeiYouJia = "64686775c136ee90b09539cb5e4cc8a4";//今日国内油价
    String xingzuo_yunshi = "9315f7a5083089ab7e9c711af99735e0";//星座运势
    String ipKey = "9aa739c4ad215d25874c102bc3f2be82";//ip

    @GET("/toutiao/index")
    Observable<ServerListResult<InformationBean>> getInformationList(@Query("key") String key, @Query("type") String type);

    @GET("/joke/randJoke.php")
    Observable<ServerListResult<JokeBean>> getJokeList(@Query("key") String key);

    @GET("/ip")
    Observable<String> getIpAddress();

    @GET("/ip/ipNew")
    Observable<ServerResult<IpBean>> getIpCity(@Query("ip") String ip, @Query("key") String key);

    @GET("/simpleWeather/query")
    Observable<ServerResult<Weather>> getWeather(@Query("city")String city, @Query("key") String key);
}
