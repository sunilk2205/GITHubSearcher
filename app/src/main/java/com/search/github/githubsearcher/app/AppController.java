package com.search.github.githubsearcher.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.search.github.githubsearcher.util.LruBitmapCache;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private RequestQueue mRequestQueue_github;
    private ImageLoader mImageLoader_github;


    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public RequestQueue getRequestQueue_article() {
        if (mRequestQueue_github == null) {
            mRequestQueue_github = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue_github;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public ImageLoader getImageLoader_article() {
        getRequestQueue_article();
        if (mImageLoader_github == null) {
            mImageLoader_github = new ImageLoader(this.mRequestQueue_github,
                    new LruBitmapCache());
        }
        return this.mImageLoader_github;
    }

    public <T> void addToRequestQueue_article(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue_article().add(req);
    }

    public <T> void addToRequestQueue_article(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue_article().add(req);
    }



    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void cancelPendingRequests_article(Object tag) {
        if (mRequestQueue_github != null) {
            mRequestQueue_github.cancelAll(tag);
        }
    }
}