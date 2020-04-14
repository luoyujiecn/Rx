package com.kxj.rx;

import android.app.Application;
import android.content.Context;

/**
 * 初始化工作
 */
public class AppContext extends Application {
    private static AppContext sContext = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = this;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //TODO XXXXXXX.init(this);
    }

    public static AppContext context() {
        return sContext;
    }
}
