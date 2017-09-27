package com.zemoso.atul.maps.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.zemoso.atul.maps.R;
import com.zemoso.atul.maps.adapters.NotificationAdapter;
import com.zemoso.atul.maps.javabeans.Notification;
import com.zemoso.atul.maps.singletons.VolleyRequests;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notifications extends Fragment {

    private static final String TAG = Notifications.class.getSimpleName();

    private SharedPreferences preferences;

    private LinearLayout mEmptyScreen;

    private String mHostname;

    private NotificationDownload notificationDownload;
    private List<Notification> notifications;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NotificationAdapter mNotificationAdapter;

    public Notifications() {
        // Required empty public constructor
    }

    public static Notifications newInstance() {
        return new Notifications();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mHostname = preferences.getString("Hostname", "");

        notifications = new ArrayList<>();

        mRecyclerView = view.findViewById(R.id.notification_recycler);
        mLayoutManager = new LinearLayoutManager(getContext());
        mNotificationAdapter = new NotificationAdapter(getContext(), notifications);

        mEmptyScreen = view.findViewById(R.id.notification_empty_layout);
        showEmptyScreen(true);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle(getResources().getString(R.string.nav_notifications));

        notificationDownload = new NotificationDownload();
        notificationDownload.getNotifications();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mNotificationAdapter);
    }

    private void showEmptyScreen(final Boolean show) {
        mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        mEmptyScreen.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private class NotificationDownload {
        //        private String start_date;
//        private String end_date;
//        private Integer limit;
        private String access_token;
        private String authorization;

        NotificationDownload() {
            access_token = preferences.getString("access_token", "");
            authorization = "Bearer " + access_token;
        }

        void getNotifications() {
            String extension = getResources().getString(R.string.url_notifications);
            String url = mHostname + extension;

            Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d(TAG, String.valueOf(response));
                    notifications.clear();
                    showEmptyScreen(true);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.optJSONObject(i);
                        Notification notification = new Notification(jsonObject);
                        notifications.add(notification);
                        Log.d(TAG, String.valueOf(notification));
                        showEmptyScreen(false);
                    }
                    mNotificationAdapter.notifyDataSetChanged();
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, String.valueOf(error));
                }
            };
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    listener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/json");
                    params.put("authorization", authorization);
                    return params;
                }
            };
            VolleyRequests.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
        }

    }
}
