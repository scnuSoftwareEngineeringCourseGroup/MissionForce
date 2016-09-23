package com.cczq.missionforce.utils;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * 创建一个单例模式
 * Created by Shyuan on 2016/9/23.
 */

public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private static AppController Instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;
    }

    //单例模式
    public static synchronized AppController getInstance() {
        return Instance;
    }

    //返回requestQueue (单例模式)
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    //返回imageLoader (单例模式)
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (imageLoader == null) {
            imageLoader = new ImageLoader(this.requestQueue, new LruBitmapCache());
        }
        return this.imageLoader;
    }

    //加入请求队列
    public <T> void addToRequestQueue(Request<T> req, String tag)
    {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    //加入请求队列
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    //取消所有正在等待的请求队列
    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

}







































