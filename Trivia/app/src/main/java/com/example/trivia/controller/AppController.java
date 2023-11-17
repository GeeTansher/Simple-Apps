package com.example.trivia.controller;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {
    private static AppController instance;
    //    private static Context context;
    private RequestQueue requestQueue;
//    private final ImageLoader imageLoader;

    public static synchronized AppController getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}


//    private AppController(Context context) {
//        AppController.context = context;
//        requestQueue = getRequestQueue();
//
//        imageLoader = new ImageLoader(requestQueue,
//                new ImageLoader.ImageCache() {
//                    private final LruCache<String, Bitmap>
//                            cache = new LruCache<String, Bitmap>(20);
//
//                    @Override
//                    public Bitmap getBitmap(String url) {
//                        return cache.get(url);
//                    }
//
//                    @Override
//                    public void putBitmap(String url, Bitmap bitmap) {
//                        cache.put(url, bitmap);
//                    }
//                });
//    }
//
//    public static synchronized AppController getInstance(Context context) {
//        if (instance == null) {
//            instance = new AppController(context);
//        }
//        return instance;
//    }
//
//    public RequestQueue getRequestQueue() {
//        if (requestQueue == null) {
//            requestQueue = Volley.newRequestQueue(AppController.context.getApplicationContext());
//        }
//        return requestQueue;
//    }
//
//    public <T> void addToRequestQueue(Request<T> req) {
//        getRequestQueue().add(req);
//    }
//
//    public ImageLoader getImageLoader() {
//        return imageLoader;
//    }

