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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;
import com.zemoso.atul.maps.R;
import com.zemoso.atul.maps.javabeans.FlightPlanDetailsHybrid;
import com.zemoso.atul.maps.javabeans.FlightPlanResponse;
import com.zemoso.atul.maps.javabeans.GeoCircle;
import com.zemoso.atul.maps.javabeans.GeoPoint;
import com.zemoso.atul.maps.javabeans.Point2D;
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
    private FlightPlanDownload flightPlanDownload;
    private SharedPreferences preferences;
    private String mHostname;
    private Toolbar mToolbar;
    private Bundle bundle;
    private FlightMap flightMap;
    private FlightDetail flightDetail;
    private String flight_plan_id;
    private String flight_title;
    private FlightPlanResponse flightPlan;

//    private View mapLayout;
//    private View detailLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_flight_plan_detail, null);
        setContentView(view);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

//        mapLayout = findViewById(R.id.map_fragment);
//        detailLayout = findViewById(R.id.detail_fragment);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mHostname = getResources().getString(R.string.url_base_address);

        bundle = getIntent().getExtras();
        flight_plan_id = bundle.getString("flight_plan_id");
        flight_title = bundle.getString("flight_title");

        setTitle(flight_title);

        flightMap = FlightMap.newInstance();
        flightDetail = FlightDetail.newInstance();

        flightPlanDownload = new FlightPlanDownload();
        flightPlanDownload.getFlightPlan();


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
            return inflater.inflate(R.layout.fragment_flight_detail, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Log.d(TAG, "Created");

            getAirspace = getActivity().getResources().getString(R.string.detail_get_airspace);
            hideAirspace = getActivity().getResources().getString(R.string.detail_hide_airspace);

            mGetAirspace = view.findViewById(R.id.profile_get_airspace);
            mAirspaceDetail = view.findViewById(R.id.drawer);


        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Log.d(TAG, "ActivityCreated");

            mGetAirspace.setOnClickListener(getListener);

        }


    }

    public static class FlightMap extends SupportMapFragment {

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
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(MapboxMap mapboxMap) {
//                    if(isFlightDownloaded)
                    for (Waypoint waypoint : waypoints) {
                        GeoPoint location = waypoint.getWp_location();
                        Log.d(TAG, String.valueOf(location));
                    }
                    for (ReservedVolume reservedVolume : reservedVolumes) {
                        GeoCircle geoCircle = reservedVolume.getReserved_volume_projection();
                        Point2D point = geoCircle.getCoordinates();
                        Log.d(TAG, String.valueOf(point));
                        addCircle(geoCircle, R.color.map_circle);
                    }
                }
            });
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
