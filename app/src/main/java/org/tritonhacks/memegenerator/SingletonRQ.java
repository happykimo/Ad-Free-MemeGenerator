package org.tritonhacks.memegenerator;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class SingletonRQ {
    private static SingletonRQ instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static Context context;

    /**
     * Private constructor called by the public factory getInstance method.
     * @param context the application's context
     */
    private SingletonRQ(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();

        this.imageLoader = new ImageLoader(this.requestQueue,
            new ImageLoader.ImageCache() {
                private final LruCache<String, Bitmap>
                    cache = new LruCache<>(20);
                @Override
                public Bitmap getBitmap(String url) {
                    return cache.get(url);
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                    cache.put(url, bitmap);
                }
            });
    }

    /**
     * Returns a factory instance of this class.
     * @param context the application context
     * @return a SingletonRQ instance
     */
    public static synchronized SingletonRQ getInstance(Context context) {
        if(instance == null) {
            instance = new SingletonRQ(context);
        }
        return instance;
    }

    /**
     * Gets the app's instance of RequestQueue. Creates one if null.
     * @return this.requestQueue
     */
    public RequestQueue getRequestQueue() {
        if(this.requestQueue == null) {
            this.requestQueue = Volley.newRequestQueue(this.context.getApplicationContext());
        }
        return this.requestQueue;
    }

    /**
     * Adds a request to the this singleton class's instance of RequestQueue.
     * @param req the Request instance
     * @param <T> Generic type
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * Getter for the ImageLoader instance.
     * @return this.imageLoader
     */
    public ImageLoader getImageLoader() {
        return this.imageLoader;
    }
}
