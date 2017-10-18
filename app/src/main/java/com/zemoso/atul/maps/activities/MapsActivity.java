package com.zemoso.atul.maps.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.zemoso.atul.maps.R;
import com.zemoso.atul.maps.javabeans.Aircraft;
import com.zemoso.atul.maps.javabeans.Contract;
import com.zemoso.atul.maps.javabeans.FlightPlanDetailsHybrid;
import com.zemoso.atul.maps.javabeans.FlightPlanResponse;
import com.zemoso.atul.maps.javabeans.GeoCircle;
import com.zemoso.atul.maps.javabeans.ReservedVolume;
import com.zemoso.atul.maps.javabeans.Waypoint;
import com.zemoso.atul.maps.singletons.VolleyRequests;
import com.zemoso.atul.maps.utils.DateTimeUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, GoogleMap.InfoWindowAdapter,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    //region Variable Declaration
    private static final String TAG = MapsActivity.class.getSimpleName();
    private static Boolean isFlightDownloaded = false;
    private static Boolean isContractDownloaded = false;
    private static Boolean isAircraftDownloaded = false;
    private static List<Waypoint> waypoints;
    private static List<ReservedVolume> reservedVolumes;
    private static FlightPlanDetailsHybrid flightPlanDetails;
    private static FlightPlanResponse flightPlan;
    private static Contract contract;
    private static Aircraft aircraft;
    private SharedPreferences preferences;
    private Bundle bundle;
    private String mHostname;
    private MapsActivity.FlightPlanDownload flightPlanDownload = null;
    private MapsActivity.ContractDownload contractDownload = null;
    private MapsActivity.AircraftDownload aircraftDownload = null;
    private MapsActivity.FlightDetail flightDetail;
    private GoogleMap mMap;
    private Map<Marker, Waypoint> mMapMarkers;
    private List<CircleOptions> mMapCircles;

    private String flight_plan_id;
    private String flight_title;
    private String contract_id;
    private String aircraft_id;
    //endregion

    //region Overridden Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mHostname = preferences.getString("Hostname", "");

        bundle = getIntent().getExtras();
        flight_plan_id = bundle.getString("flight_plan_id");
        flight_title = bundle.getString("flight_title");
        contract_id = bundle.getString("contract_id");
        aircraft_id = bundle.getString("aircraft_id");

        setTitle(flight_title);


//        Window w = getWindow(); // in Activity's onCreate() for instance
//        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        flightDetail = MapsActivity.FlightDetail.newInstance();

        flightPlanDownload = new FlightPlanDownload();
        flightPlanDownload.getFlightPlan();

        contractDownload = new ContractDownload();
        contractDownload.getContract();

        aircraftDownload = new AircraftDownload();
        aircraftDownload.getContract();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        finish();
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
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setAllGesturesEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.setTrafficEnabled(true);

//        googleMap.setMyLocationEnabled(true);
        mMap = googleMap;
        mMapMarkers = new HashMap<>();
        mMapCircles = new ArrayList<>();
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setInfoWindowAdapter(this);
        mMap.setOnInfoWindowClickListener(this);

        switch (flightPlanDetails.getFlight_plan_type()) {
            case "BVLOS":
                Log.d(TAG, String.valueOf(waypoints.size()));
                for (Waypoint waypoint : waypoints) {
                    LatLng location = waypoint.getWp_location();
                    Log.d(TAG, String.valueOf(location));

                    Marker marker = mMap.addMarker(new MarkerOptions().position(location)
                            .title("I did it.")
                            .anchor(0.5f, 0.5f)
                            .snippet("Position: " + Math.round(location.latitude * 100.00) / 100.00 + ", " + Math.round(location.longitude * 100.00) / 100.00));
//                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_adjust_black_24dp);
//                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
                    marker.setIcon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.way_point_black));
                    mMapMarkers.put(marker, waypoint);
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                    Log.d(TAG, String.valueOf(location));

                }
                for (int i = 0; i < waypoints.size() - 1; i++) {
                    Waypoint a = waypoints.get(i);
                    Waypoint b = waypoints.get(i + 1);
                    LatLng gpa = a.getWp_location();
                    LatLng gpb = b.getWp_location();
                    float widthWhite = getResources().getDimension(R.dimen.map_polyline_stroke);
                    mMap.addPolyline(new PolylineOptions().width(widthWhite).add(gpa).add(gpb).clickable(true).color(R.color.map_polyline_stroke));
                    float widthBlack = getResources().getDimension(R.dimen.map_polyline_fill);
                    mMap.addPolyline(new PolylineOptions().width(widthBlack).add(gpa).add(gpb).clickable(true).color(R.color.map_polyline_fill));
                }

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker key : mMapMarkers.keySet())
                    builder.include(key.getPosition());
                LatLngBounds bounds = builder.build();
                int padding = 100;
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                mMap.animateCamera(cu);

            case "VLOS":
                LatLngBounds.Builder builder1 = new LatLngBounds.Builder();
                for (ReservedVolume reservedVolume : reservedVolumes) {
                    GeoCircle geoCircle = reservedVolume.getReserved_volume_projection();
                    LatLng point = geoCircle.getCoordinates();
                    builder1.include(geoCircle.getCoordinates());
                    LatLng pointRight = getHorizontalBoundary(geoCircle);
                    Log.d(TAG, String.valueOf(pointRight));
                    addCircle(geoCircle, R.color.map_circle);
                    Log.d(TAG, String.valueOf(point));
                }
//                LatLngBounds bounds1 = builder1.build();
//                int padding1 = 100;
//                CameraUpdate cu1 = CameraUpdateFactory.newLatLngBounds(bounds1,padding1);
//                mMap.animateCamera(cu1);
        }


        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onMapClick(LatLng latLng) {
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        Marker marker = mMap.addMarker(markerOptions);
//
//        marker.setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.transparent));
//        marker.showInfoWindow();



    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(final Marker marker) {
        View view = getLayoutInflater().inflate(R.layout.content_map_click, null);
        Waypoint waypoint = mMapMarkers.get(marker);
        TextView tvSpeed = view.findViewById(R.id.tv_speed_bounds);
        TextView tvAltitude = view.findViewById(R.id.tv_altitude_bounds);
        TextView tvTurnType = view.findViewById(R.id.tv_turn_type);
        String textSpeed = getResources().getString(R.string.waypoint_speed);
        String textAltitude = getString(R.string.waypoint_altitude);
        String textTurnType = getString(R.string.waypoint_turn_type);
        textSpeed += waypoint.getMin_speed_kn() + " to " + waypoint.getMax_speed_kn();
        textSpeed += getString(R.string.waypoint_speed_unit);
        textAltitude += waypoint.getMin_altitude_ft() + " to " + waypoint.getMax_altitude_ft();
        textAltitude += getString(R.string.waypoint_altitude_unit);
        textTurnType += waypoint.getTurn_type();
        tvSpeed.setText(textSpeed);
        tvAltitude.setText(textAltitude);
        tvTurnType.setText(textTurnType);
//        ImageView buttonClose = view.findViewById(R.id.waypoint_close);
//        buttonClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                marker.hideInfoWindow();
//            }
//        });

        return view;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        marker.hideInfoWindow();
    }
    //endregion

    //region Private Methods

    private LatLng getHorizontalBoundary(GeoCircle geoCircle) {
        LatLng center = geoCircle.getCoordinates();
        String radius = geoCircle.getRadius();
        Double rad = getRadius(radius);
        Log.d(TAG, String.valueOf(rad));
        Double lat = center.latitude;
        Double lon = center.longitude;
        return new LatLng(lat, lon);
    }

    private void addCircle(GeoCircle geoCircle, int colorResId) {
        LatLng center = geoCircle.getCoordinates();
        Double radius = getRadius(geoCircle.getRadius());
//        radius = 1000.0;
        CircleOptions circleOptions = new CircleOptions().center(center).radius(radius)
                .fillColor(colorResId);
        mMapCircles.add(circleOptions);
        Circle circle = mMap.addCircle(circleOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                circleOptions.getCenter(), getZoomLevel(circle)));
    }

    public int getZoomLevel(Circle circle) {
        int zoomLevel = 11;
        if (circle != null) {
            double radius = circle.getRadius() + circle.getRadius() / 2;
            double scale = radius / 500;
            zoomLevel = (int) (16 - Math.log(scale) / Math.log(2));
        }
        zoomLevel -= 2;
        return zoomLevel;
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private Double getRadius(String radius) {
        String ft = radius.substring(0, radius.indexOf('f'));
        Double rad = Double.parseDouble(ft);
        return rad * 0.3048;
    }

    private void attachFragments() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down,
                        R.anim.slide_out_down, R.anim.slide_out_up)
                .replace(R.id.detail_fragment, flightDetail)
                .commit();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    private Boolean isDownloaded() {
        return isContractDownloaded && isFlightDownloaded && isAircraftDownloaded;
    }
    //endregion

    public static class FlightDetail extends Fragment {

        //region Variable Declaration
        private static final String TAG = MapsActivity.FlightDetail.class.getSimpleName();

        private TextView mFlightPlan;
        private TextView mFlightType;
        private TextView mDate;
        private TextView mStartTime;
        private TextView mEndTime;
        private TextView mAircraft;
        private TextView mGrossWt;
        private TextView mPayloadWt;
        private TextView mFuelLoading;

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

        private String getAirspace;
        private String hideAirspace;

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
        //endregion

        //region Constructor
        public FlightDetail() {
        }

        public static MapsActivity.FlightDetail newInstance() {
            return new MapsActivity.FlightDetail();
        }
        //endregion

        //region Overrridden Methods
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
            mDateText = flightPlan.getCreated_at();
            mDateText = DateTimeUtils.getDateFromString(mDateText);
            mStartTimeText = contract.getStart_time();
            mStartTimeText = DateTimeUtils.getTimeFromString(mStartTimeText);
            mEndTimeText = contract.getEnd_time();
            mEndTimeText = DateTimeUtils.getTimeFromString(mEndTimeText);
            mAircraftText = aircraft.getName();
            mGrossWtText = String.valueOf(flightPlanDetails.getGross_weight_lb());
            mPayloadWtText = String.valueOf(flightPlanDetails.getPayload_weight_lb());
            mFuelLoadingText = String.valueOf(flightPlanDetails.getFuel_weight_lb());
            Log.d(TAG, mPayloadWtText);


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
        //endregion
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
                    if (isDownloaded())
                        attachFragments();
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
            VolleyRequests.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        }
    }

    private class ContractDownload {

        private String access_token;
        private String authorization;

        ContractDownload() {
            access_token = preferences.getString("access_token", "");
            authorization = "Bearer " + access_token;
        }

        @Override
        public String toString() {
            return "?id=" + contract_id;
        }

        private void getContract() {
            String extension = getResources().getString(R.string.url_contract);
            String url = mHostname + extension;
            url += this.toString();
            Log.d(TAG, url);

            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, String.valueOf(response));
                    contract = new Contract(response);
                    isContractDownloaded = true;
                    if (isDownloaded())
                        attachFragments();
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
            VolleyRequests.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        }
    }

    private class AircraftDownload {

        private String access_token;
        private String authorization;

        AircraftDownload() {
            access_token = preferences.getString("access_token", "");
            authorization = "Bearer " + access_token;
        }

        @Override
        public String toString() {
            return "?id=" + aircraft_id;
        }

        private void getContract() {
//            TODO: Ask
            String extension = getResources().getString(R.string.url_aircraft);
            String url = mHostname + extension;
            url += this.toString();
            Log.d(TAG, url);

            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, String.valueOf(response));
                    aircraft = new Aircraft(response);
                    isAircraftDownloaded = true;
                    if (isDownloaded())
                        attachFragments();
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
            VolleyRequests.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        }
    }

    private class AirspaceDownload {


        private String access_token;
        private String authorization;

        AirspaceDownload() {
            access_token = preferences.getString("access_token", "");
            authorization = "Bearer " + access_token;
        }

        @Override
        public String toString() {
            return "?id=" + flight_plan_id;
        }

        private void getAirspaces() {
            String extension = getResources().getString(R.string.url_airspace);
            String url = mHostname + extension;
            url += this.toString();
            Log.d(TAG, url);

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
            VolleyRequests.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        }
    }
}


