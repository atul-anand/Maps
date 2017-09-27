package com.zemoso.atul.maps.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.zemoso.atul.maps.R;
import com.zemoso.atul.maps.activities.CreatePlan;
import com.zemoso.atul.maps.adapters.FlightPlanAdapter;
import com.zemoso.atul.maps.javabeans.FlightPlanResponse;
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
public class FlightPlan extends Fragment {

    private static final String TAG = FlightPlan.class.getSimpleName();

    private SharedPreferences preferences;

    private LinearLayout mEmptyScreen;

    private String mHostname;

    private FlightPlanDownload flightPlanDownload = new FlightPlanDownload();
    private List<FlightPlanResponse> flightPlanResponses;

    private TextView mCreatePlan;
    private TextView mEmptyScreenCreate;
    private NestedScrollView mNestedScrollView;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FlightPlanAdapter mFlightPlanAdapter;

    private View.OnClickListener mCreateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(), CreatePlan.class);
            startActivity(intent);
        }
    };
    public FlightPlan() {
        // Required empty public constructor
    }

    public static FlightPlan newInstance() {
        return new FlightPlan();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flight_plan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mHostname = preferences.getString("Hostname", "");

        flightPlanResponses = new ArrayList<>();

        mCreatePlan = view.findViewById(R.id.create_plan);
        mEmptyScreenCreate = view.findViewById(R.id.plan_create_flight);

        mNestedScrollView = view.findViewById(R.id.nested_scroll);

        mRecyclerView = view.findViewById(R.id.plan_recycler);
        mLayoutManager = new LinearLayoutManager(getContext());
        mFlightPlanAdapter = new FlightPlanAdapter(flightPlanResponses, getContext());

        mEmptyScreen = view.findViewById(R.id.plan_empty_layout);

        showEmptyScreen(true);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle(getResources().getString(R.string.nav_flight_plan));


        flightPlanDownload.getFlightPlans();

        mCreatePlan.setOnClickListener(mCreateListener);
        mEmptyScreenCreate.setOnClickListener(mCreateListener);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mFlightPlanAdapter);

    }

    private void showEmptyScreen(final Boolean show) {
        mNestedScrollView.setVisibility(show ? View.GONE : View.VISIBLE);
        mEmptyScreen.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private class FlightPlanDownload {
        //        private String status;
//        private String start_time;
//        private String end_time;
//        private Integer page_size;
//        private Integer offset;
        private String access_token;
        private String authorization;

        FlightPlanDownload() {
            access_token = preferences.getString("access_token", "");
            authorization = "Bearer " + access_token;
        }

        @Override
        public String toString() {
            return "?authorization=" + authorization;
        }

        void getFlightPlans() {
            String extension = getResources().getString(R.string.url_flight_plans);
            String url = mHostname + extension;

            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    JSONArray jsonArray = response.optJSONArray("objectsList");
                    flightPlanResponses.clear();
                    showEmptyScreen(true);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        flightPlanResponses.add(new FlightPlanResponse(jsonArray.optJSONObject(i)));
                        Log.d(TAG, String.valueOf(flightPlanResponses));
                    }
                    if (flightPlanResponses.size() > 0)
                        showEmptyScreen(false);
                    mFlightPlanAdapter.notifyDataSetChanged();
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, String.valueOf(error));
                }
            };
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    listener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/json");
                    params.put("authorization", authorization);
                    return params;
                }
            };
            VolleyRequests.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);

        }

    }
}
