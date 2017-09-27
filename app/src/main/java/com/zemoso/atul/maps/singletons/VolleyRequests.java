package com.zemoso.atul.maps.singletons;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class VolleyRequests {

    //region Variable Declaration
    private static final String TAG = VolleyRequests.class.getSimpleName();

    private static VolleyRequests mInstance;
    private RequestQueue mRequestQueue;
    private Context mContext;

    //endregion

    //region Constructors
//    @TargetApi(Build.VERSION_CODES.N)
    private VolleyRequests(Context mContext) {
        this.mContext = mContext;
        mRequestQueue = getRequestQueue();
//        mHostName = mContext.getResources().getString(R.string.url_address);
//        preferences = mContext.getSharedPreferences("Settings", 0);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String mHostName = preferences.getString("Hostname", "");
//        mUserId = preferences.getLong("userId", 0);
//        Log.d(TAG, String.valueOf(mUserId));
//        mHostName = ;
        Log.d(TAG, mHostName);
//        mHostName = mContext.getSharedPreferences();
//        image = mContext.getResources().getString(R.string.url_image);
    }

    public static synchronized VolleyRequests getInstance(Context mContext) {
        if (mInstance == null)
            mInstance = new VolleyRequests(mContext);
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        return mRequestQueue;
    }
    //endregion

    //region Volley Methods
    public <T> void addToRequestQueue(@NonNull Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(@NonNull Request<T> request) {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public void cancelPendingRequests(@NonNull Object tag) {
        if (mRequestQueue != null)
            mRequestQueue.cancelAll(tag);
    }
//endregion
}