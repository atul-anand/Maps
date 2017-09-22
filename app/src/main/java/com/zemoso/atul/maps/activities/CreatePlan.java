package com.zemoso.atul.maps.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zemoso.atul.maps.R;
import com.zemoso.atul.maps.javabeans.FlightPlanDetailsHybrid;
import com.zemoso.atul.maps.javabeans.FlightPlanRequestHybrid;
import com.zemoso.atul.maps.javabeans.ReservedVolume;
import com.zemoso.atul.maps.javabeans.Waypoint;
import com.zemoso.atul.maps.singletons.VolleyRequests;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreatePlan extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = CreatePlan.class.getSimpleName();
    private static String name;
    private static String pilot_id;
    private static String aircraft_id;
    private static String status;
    private static String organization_id;
    private static String description;
    private static String flight_plan_type;
    private static String flight_plan_category;
    private static String route_id;
    private static String altitude_mode;
    private static JSONObject props;
    private static Double gross_weight_lb;
    private static Double fuel_weight_lb;
    private static Double fuel_indicator;
    private static Double payload_weight_lb;
    private static FlightPlanDetailsHybrid flightPlanDetails;
    private static FlightPlanRequestHybrid flightPlanRequest;
    private SharedPreferences preferences;
    private Bundle bundle;
    private String mHostname;
    private CreatePlan.FlightPlanUpload mAuthTask = null;
    private CreatePlan.FlightDetail mFlightDetailFragment;
    private GoogleMap mMap;
    private Map<Marker, Waypoint> mMapMarkers;
    private List<ReservedVolume> mReservedVolumes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);


        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mHostname = preferences.getString("Hostname", "");

        mFlightDetailFragment = CreatePlan.FlightDetail.newInstance();

        attachFragments();


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void attachFragments() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down,
                        R.anim.slide_out_down, R.anim.slide_out_up)
                .replace(R.id.detail_create_fragment, mFlightDetailFragment)
                .commit();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(CreatePlan.this);
    }

    private void attemptUpload() {

        Log.d(TAG, "Attempting Upload");

        if (mAuthTask != null) {
            return;
        }

        mFlightDetailFragment.setError();
        mFlightDetailFragment.getData();
        mFlightDetailFragment.validateData();

        createFlightPlanRequest();

    }

    private void createFlightPlanRequest() {
        flightPlanRequest = new FlightPlanRequestHybrid();
        mAuthTask = new FlightPlanUpload(flightPlanRequest);
        mAuthTask.uploadPlan();

    }

    public static class FlightDetail extends Fragment {

        private static final String TAG = CreatePlan.FlightDetail.class.getSimpleName();

        private EditText mFlightPlan;
        private EditText mFlightType;
        private EditText mDate;
        private EditText mStartTime;
        private EditText mEndTime;
        private EditText mAircraft;
        private EditText mGrossWt;
        private EditText mPayloadWt;
        private EditText mFuelLoading;

        private String getAirspace;
        private String hideAirspace;

        private Button mGetAirspace;
        private View mAirspaceDetail;


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
//                    TODO: Attempt Login
                } else {
                    mAirspaceDetail.setVisibility(View.VISIBLE);
                    mGetAirspace.setText(hideAirspace);
                    hasDetailedView = true;
                }
            }
        };

        public FlightDetail() {
        }

        public static CreatePlan.FlightDetail newInstance() {
            return new CreatePlan.FlightDetail();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_create_flight_detail, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Log.d(TAG, "Created");

            getAirspace = getActivity().getResources().getString(R.string.detail_add_details);
            hideAirspace = getActivity().getResources().getString(R.string.detail_request_contract);

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

            mGetAirspace.setOnClickListener(getListener);


        }

        private void setError() {
            Log.d(TAG, "Error Setters");
            mFlightPlan.setError(null);
            mFlightType.setError(null);
            mDate.setError(null);
            mStartTime.setError(null);
            mEndTime.setError(null);
            mAircraft.setError(null);
            mGrossWt.setError(null);
            mPayloadWt.setError(null);
            mFuelLoading.setError(null);
        }

        private void getData() {
            Log.d(TAG, "Flight Detail Data");
            name = mFlightPlan.getText().toString().trim();
            pilot_id = "";
            aircraft_id = mAircraft.getText().toString().trim();
            status = "RECEIVED";
            organization_id = "";
            description = "";
            flight_plan_type = mFlightType.getText().toString().trim();
            flight_plan_category = "";
            route_id = "";
            altitude_mode = "";
            props = new JSONObject();
            gross_weight_lb = Double.valueOf(mGrossWt.getText().toString().trim());
            fuel_weight_lb = Double.valueOf(mFuelLoading.getText().toString().trim());
            fuel_indicator = 0.0;
            payload_weight_lb = Double.valueOf(mPayloadWt.getText().toString().trim());

        }

        private void validateData() {
            Log.d(TAG, "Validations");
            boolean cancel = false;
            View focusView = null;
//            TODO: Validations

        }


    }

    private class FlightPlanUpload {

        FlightPlanRequestHybrid flightPlanRequest;
        private String access_token;
        private String authorization;
        private JSONObject mFlightPlanRequest;

        FlightPlanUpload(FlightPlanRequestHybrid flightPlanRequest) {
            this.flightPlanRequest = flightPlanRequest;
            access_token = preferences.getString("access_token", "");
            authorization = "Bearer " + access_token;
            mFlightPlanRequest = flightPlanRequest.toJSON();
            Log.d(TAG, String.valueOf(mFlightPlanRequest));
        }

        void uploadPlan() {
            String extension = getResources().getString(R.string.url_flight_request_vlos);
            String url = mHostname + extension;

            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, String.valueOf(response));
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, String.valueOf(error));
                }
            };
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, mFlightPlanRequest,
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
