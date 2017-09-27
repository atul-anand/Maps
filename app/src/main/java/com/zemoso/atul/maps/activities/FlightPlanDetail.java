package com.zemoso.atul.maps.activities;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;
import com.zemoso.atul.maps.R;
import com.zemoso.atul.maps.javabeans.Aircraft;
import com.zemoso.atul.maps.javabeans.FlightPlanDetailsHybrid;
import com.zemoso.atul.maps.javabeans.FlightPlanResponse;
import com.zemoso.atul.maps.javabeans.GeoCircle;
import com.zemoso.atul.maps.javabeans.ReservedVolume;
import com.zemoso.atul.maps.javabeans.Waypoint;
import com.zemoso.atul.maps.singletons.VolleyRequests;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FlightPlanDetail extends AppCompatActivity {

    private static final String TAG = FlightPlanDetail.class.getSimpleName();
    private static Boolean isFlightDownloaded = false;
    private static List<Waypoint> waypoints;
    private static List<ReservedVolume> reservedVolumes;
    private static FlightPlanDetailsHybrid flightPlanDetails;
    private static FlightPlanResponse flightPlan;
    private static Aircraft aircraft;


    private FlightPlanDownload flightPlanDownload;
    private SharedPreferences preferences;
    private String mHostname;
    private Toolbar mToolbar;
    private Bundle bundle;
    private FlightMap flightMap;
    private FlightDetail flightDetail;
    private String flight_plan_id;
    private String flight_title;

//    private View mapLayout;
//    private View detailLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_flight_plan_detail, null);
        setContentView(view);


        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } catch (NullPointerException e) {
            Log.e(TAG, String.valueOf(e));
        }
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mHostname = getResources().getString(R.string.url_base_address);

        bundle = getIntent().getExtras();
        flight_plan_id = bundle.getString("flight_plan_id");
        flight_title = bundle.getString("flight_title");

        getSupportActionBar().setTitle(flight_title);

        flightMap = FlightMap.newInstance();
        flightDetail = FlightDetail.newInstance();

        flightPlanDownload = new FlightPlanDownload();
        flightPlanDownload.getFlightPlan();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static class FlightDetail extends Fragment {

        private static final String TAG = FlightDetail.class.getSimpleName();

        private String getAirspace;
        private String hideAirspace;

        private Button mGetAirspace;
        private View mAirspaceDetail;

        private TextView mFlightPlan;
        private TextView mFlightType;
        private TextView mDate;
        private TextView mStartTime;
        private TextView mEndTime;
        private TextView mAircraft;
        private TextView mGrossWt;
        private TextView mPayloadWt;
        private TextView mFuelLoading;

        private String mFlightPlanText;
        private String mFlightTypeText;
        private String mDateText;
        private String mStartTimeText;
        private String mEndTimeText;

        private String mAircraftText;
        private String mGrossWtText;
        private String mPayloadWtText;
        private String mFuelLoadingText;

        private Boolean hasDetailedView = false;

        private View.OnClickListener getListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasDetailedView) {
                    mAirspaceDetail.setVisibility(View.GONE);
                    mGetAirspace.setText(getAirspace);
                    hasDetailedView = false;
                } else {
                    mAirspaceDetail.setVisibility(View.VISIBLE);
                    mGetAirspace.setText(hideAirspace);
                    hasDetailedView = true;
                }
            }
        };

        public FlightDetail() {
        }

        public static FlightDetail newInstance() {
            return new FlightDetail();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_maps_flight_detail, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Log.d(TAG, "Created");

            getAirspace = getActivity().getResources().getString(R.string.detail_get_airspace);
            hideAirspace = getActivity().getResources().getString(R.string.detail_hide_airspace);

            mGetAirspace = view.findViewById(R.id.profile_get_airspace);
            mAirspaceDetail = view.findViewById(R.id.drawer);

            mAirspaceDetail.setVisibility(View.GONE);

            mFlightPlan = view.findViewById(R.id.text_flight_plan);
            mFlightType = view.findViewById(R.id.text_flight_type);
            mDate = view.findViewById(R.id.text_date);
            mStartTime = view.findViewById(R.id.text_start_time);
            mEndTime = view.findViewById(R.id.text_end_time);
            mAircraft = view.findViewById(R.id.text_aircraft);
            mGrossWt = view.findViewById(R.id.text_gross_weight);
            mPayloadWt = view.findViewById(R.id.text_payload_weight);
            mFuelLoading = view.findViewById(R.id.text_fuel_loading);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Log.d(TAG, "ActivityCreated");

            mFlightPlanText = flightPlan.getName();
            mFlightTypeText = flightPlanDetails.getFlight_plan_type();
            mDateText = getString(R.string.detail_item_date);
            mStartTimeText = getString(R.string.detail_item_start_time);
            mEndTimeText = getString(R.string.detail_item_end_time);
            mAircraftText = flightPlan.getAircraft_id();
            mGrossWtText = String.valueOf(flightPlanDetails.getGross_weight_lb());
            mPayloadWtText = String.valueOf(flightPlanDetails.getPayload_weight_lb());
            mFuelLoadingText = String.valueOf(flightPlanDetails.getFuel_weight_lb());

            mFlightPlan.setText(mFlightPlanText);
            mFlightType.setText(mFlightTypeText);
            mDate.setText(mDateText);
            mStartTime.setText(mStartTimeText);
            mEndTime.setText(mEndTimeText);
            mAircraft.setText(mAircraftText);
            mGrossWt.setText(mGrossWtText);
            mPayloadWt.setText(mPayloadWtText);
            mFuelLoading.setText(mFuelLoadingText);

            mGetAirspace.setOnClickListener(getListener);


        }


    }

    public static class FlightMap extends SupportMapFragment implements OnMapReadyCallback {

        private static final String TAG = FlightMap.class.getSimpleName();

        private MapView mapView;


        public FlightMap() {
        }

        public static FlightMap newInstance() {
            return new FlightMap();
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Mapbox.getInstance(getContext(), getString(R.string.mapbox_token));

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_flight_map, container, false);
            mapView = view.findViewById(R.id.detail_map);
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(this);
            Log.d(TAG, "Created");
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        private void addCircle(GeoCircle geoCircle, int colorResId) {
            Log.d(TAG, String.valueOf(geoCircle));
        }

        @Override
        public void onResume() {
            super.onResume();
            mapView.onResume();
        }

        @Override
        public void onPause() {
            super.onPause();
            mapView.onPause();
        }

        @Override
        public void onStart() {
            super.onStart();
            mapView.onStart();
        }

        @Override
        public void onStop() {
            super.onStop();
            mapView.onStop();
        }

        @Override
        public void onLowMemory() {
            super.onLowMemory();
            mapView.onLowMemory();
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            mapView.onSaveInstanceState(outState);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mapView.onDestroy();
        }

        @Override
        public void onMapReady(MapboxMap mapboxMap) {
            new OnMapReadyCallback() {
                @Override
                public void onMapReady(final MapboxMap mapboxMap) {

                    mapboxMap.addMarker(new MarkerOptions()
                            .position(new LatLng(48.85819, 2.29458))
                            .title("Eiffel Tower")
                    );

                    for (Waypoint waypoint : waypoints) {
//                        com.google.android.gms.maps.model.LatLng location = waypoint.getWp_location();
//                        Log.d(TAG, String.valueOf(location));
//                        mapboxMap.addMarker(new MarkerOptions()
//                                .position(location)
//                                .title(waypoint.getMission_notes())
//                        );

                    }
                    for (ReservedVolume reservedVolume : reservedVolumes) {
                        GeoCircle geoCircle = reservedVolume.getReserved_volume_projection();
                        com.google.android.gms.maps.model.LatLng point = geoCircle.getCoordinates();
                        Log.d(TAG, String.valueOf(point));
                        addCircle(geoCircle, R.color.map_circle);
                    }
                }
            };
        }
    }

    private class FlightPlanDownload {


        private String access_token;
        private String authorization;

        FlightPlanDownload() {
            access_token = preferences.getString("access_token", "");
            authorization = "Bearer " + access_token;
        }

        @Override
        public String toString() {
            return "?id=" + flight_plan_id;
        }

        private void getFlightPlan() {
            String extension = getResources().getString(R.string.url_flight_plan);
            String url = mHostname + extension;
            url += this.toString();
            Log.d(TAG, url);

            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, String.valueOf(response));
                    flightPlan = new FlightPlanResponse(response);
                    flightPlanDetails = flightPlan.getFlight_plan_details();
                    waypoints = flightPlanDetails.getWaypoints_info();
                    reservedVolumes = flightPlanDetails.getReserved_volumes();
                    isFlightDownloaded = true;
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down,
                                    R.anim.slide_out_down, R.anim.slide_out_up)
                            .replace(R.id.detail_fragment, flightDetail)
                            .commit();

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.map_fragment, flightMap)
                            .commit();
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
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("authorization", authorization);
                    return params;
                }
            };
            VolleyRequests.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        }
    }


}
