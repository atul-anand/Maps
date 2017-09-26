package com.zemoso.atul.maps.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zemoso.atul.maps.R;
import com.zemoso.atul.maps.interfaces.AttemptUploadPlan;
import com.zemoso.atul.maps.interfaces.UpdateAircraftData;
import com.zemoso.atul.maps.javabeans.Aircraft;
import com.zemoso.atul.maps.javabeans.FlightPlanDetailsHybrid;
import com.zemoso.atul.maps.javabeans.FlightPlanRequestHybrid;
import com.zemoso.atul.maps.javabeans.ReservedVolume;
import com.zemoso.atul.maps.javabeans.Waypoint;
import com.zemoso.atul.maps.singletons.VolleyRequests;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreatePlan extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = CreatePlan.class.getSimpleName();
    private static String pilot_id;
    private static FlightPlanDetailsHybrid flightPlanDetails;
    private static FlightPlanRequestHybrid flightPlanRequest;
    private SharedPreferences preferences;
    private Bundle bundle;
    private String mHostname;
    private CreatePlan.FlightPlanUpload mAuthTask = null;
    private CreatePlan.FlightDetail mFlightDetailFragment = null;
    private CreatePlan.AircraftsDownload mAircraftsDownload = null;
    private GoogleMap mMap;
    private Map<Marker, Waypoint> mMapMarkers;
    private List<ReservedVolume> mReservedVolumes;
    private List<Aircraft> mAircrafts;
    private AttemptUploadPlan attemptUploadPlan = new AttemptUploadPlan() {
        @Override
        public void attemptUpload() {
            attemptUploadPlan();
            Log.d(TAG, "Attempting Upload Plan!");
        }
    };
    private UpdateAircraftData updateAircraftDataImpl = new UpdateAircraftData() {
        @Override
        public void updateData() {
            mFlightDetailFragment.setAircraftSpinnerData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);


        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mHostname = preferences.getString("Hostname", "");
        pilot_id = preferences.getString("pilot_id", "");

        mAircraftsDownload.setUpdateAircraftData(updateAircraftDataImpl);
        getAircrafts();

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
        if (mFlightDetailFragment != null)
            return;
        mFlightDetailFragment = CreatePlan.FlightDetail.newInstance(attemptUploadPlan);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down,
                        R.anim.slide_out_down, R.anim.slide_out_up)
                .replace(R.id.detail_create_fragment, mFlightDetailFragment)
                .commit();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(CreatePlan.this);
    }

    private void getAircrafts() {
        if (mAircraftsDownload != null)
            return;
        mAircrafts = new ArrayList<>();
        mAircraftsDownload = new AircraftsDownload();
        mAircraftsDownload.getAircrafts();
    }

    private void attemptUploadPlan() {

        Log.d(TAG, "Attempting Upload");

        if (mAuthTask != null) {
            return;
        }

        flightPlanRequest = new FlightPlanRequestHybrid();

        mFlightDetailFragment.setError();
        mFlightDetailFragment.getData();
        mFlightDetailFragment.validateData();
        mFlightDetailFragment.setData();

        createFlightPlanRequest();

    }

    private void createFlightPlanRequest() {

        mAuthTask = new FlightPlanUpload(flightPlanRequest);
        mAuthTask.uploadPlan();

    }

    public static class FlightDetail extends Fragment {

        private static final String TAG = CreatePlan.FlightDetail.class.getSimpleName();

        private EditText mFlightPlan;
        private Spinner mFlightType;
        private TextView mDate;
        private TextView mStartTime;
        private TextView mEndTime;
        private Spinner mAircraft;
        private EditText mGrossWt;
        private EditText mPayloadWt;
        private EditText mFuelLoading;

        private String getAirspace;
        private String requestContract;

        private Button mGetAirspace;
        private View mAirspaceDetail;

        private String name;
        private String aircraft_id;
        private String status;
        private String organization_id;
        private String description;
        private String flight_plan_type;
        private String flight_plan_category;
        private String route_id;
        private String altitude_mode;
        private JSONObject props;
        private Double gross_weight_lb;
        private Double fuel_weight_lb;
        private Double fuel_indicator;
        private Double payload_weight_lb;

        private int year;
        private int month;
        private int day;
        private int hourStart;
        private int minuteStart;
        private int hourEnd;
        private int minuteEnd;


        private String mFlightPlanText;
        private String mFlightTypeText;
        private String mDateText;
        private String mStartTimeText;
        private String mEndTimeText;

        private String[] aircraftArray;
        private Map<Integer, String> aircraftMap;

        private String mAircraftText;
        private String mGrossWtText;
        private String mPayloadWtText;
        private String mFuelLoadingText;


        private Boolean hasDetailedView = false;

        private AttemptUploadPlan attemptUploadPlanImpl;



        //region Pickers and Listeners
        private View.OnClickListener getListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasDetailedView) {
//                    mAirspaceDetail.setVisibility(View.GONE);
//                    mGetAirspace.setText(getAirspace);
                    hasDetailedView = false;
                    attemptUploadPlanImpl.attemptUpload();

                } else {
                    mAirspaceDetail.setVisibility(View.VISIBLE);
                    mGetAirspace.setText(requestContract);
                    hasDetailedView = true;
                }
            }
        };
        private TimePickerDialog.OnTimeSetListener startTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                mStartTimeText = hourOfDay + ":" + minute;
                mStartTime.setText(mStartTimeText);
            }
        };
        private View.OnClickListener startTimeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(getActivity(), startTimeListener,
                        hourStart, minuteStart, true);
                dialog.show();
            }
        };
        private TimePickerDialog.OnTimeSetListener endTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                mEndTimeText = hourOfDay + ":" + minute;
                mEndTime.setText(mEndTimeText);
            }
        };
        private View.OnClickListener endTimeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(getActivity(), endTimeListener,
                        hourEnd, minuteEnd, true);
                dialog.show();
            }
        };
        private DatePickerDialog.OnDateSetListener datePickerListener
                = new DatePickerDialog.OnDateSetListener() {

            // when dialog box is closed, below method will be called.
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {
                //Do whatever you want
                mDateText = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                mDate.setText(mDateText);
            }
        };
        private View.OnClickListener selectDateListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), datePickerListener,
                        year, month, day);
                dialog.show();
            }
        };
        //endregion

        //region Constructor
        public FlightDetail() {
        }

        public static CreatePlan.FlightDetail newInstance(AttemptUploadPlan instance) {
            CreatePlan.FlightDetail newInst = new CreatePlan.FlightDetail();
            newInst.attemptUploadPlanImpl = instance;
            return newInst;
        }
        //endregion

        //region Overridden Methods
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
            requestContract = getActivity().getResources().getString(R.string.detail_request_contract);

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

            setDefaults();
            setClickListeners();

        }


        private void setAircraftSpinnerData() {
            List<Aircraft> aircrafts = ((CreatePlan) getActivity()).mAircrafts;
            aircraftArray = new String[aircrafts.size()];
            aircraftMap = new HashMap<>();
            for (int i = 0; i < aircrafts.size(); i++) {
                Aircraft aircraft = aircrafts.get(i);
                aircraftMap.put(i, aircraft.getId());
                aircraftArray[i] = aircraft.getName();
            }
            ArrayAdapter<String> aircraftAdapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, aircraftArray);
            aircraftAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mAircraft.setAdapter(aircraftAdapter);
        }

        private void setClickListeners() {
            mGetAirspace.setOnClickListener(getListener);
            mDate.setOnClickListener(selectDateListener);
            mStartTime.setOnClickListener(startTimeClickListener);
            mEndTime.setOnClickListener(endTimeClickListener);
        }

        private void setDefaults() {
            Calendar cal = Calendar.getInstance();
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
            mDateText = day + "/" + month + "/" + year;
            mDate.setText(mDateText);

            hourStart = cal.get(Calendar.HOUR_OF_DAY);
            minuteStart = cal.get(Calendar.MINUTE);
            mStartTimeText = hourStart + ":" + minuteStart;
            mStartTime.setText(mStartTimeText);

            cal.add(Calendar.MINUTE, 10);
            hourEnd = cal.get(Calendar.HOUR_OF_DAY);
            minuteEnd = cal.get(Calendar.MINUTE);
            mEndTimeText = hourEnd + ":" + minuteEnd;
            mEndTime.setText(mEndTimeText);


        }

        private void setError() {
            Log.d(TAG, "Error Setters");
            mFlightPlan.setError(null);

            mDate.setError(null);
            mStartTime.setError(null);
            mEndTime.setError(null);

            mGrossWt.setError(null);
            mPayloadWt.setError(null);
            mFuelLoading.setError(null);
        }

        private void getData() {
            Log.d(TAG, "Flight Detail Data");
            name = mFlightPlan.getText().toString().trim();
            aircraft_id = aircraftMap.get(mAircraft.getSelectedItemPosition());
            status = "DRAFT";
            organization_id = "";
            description = "";
            flight_plan_type = mFlightType.getSelectedItem().toString().trim();
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

        public void setData() {
//            TODO: Setters
            flightPlanRequest.setName(name);
            flightPlanRequest.setAircraft_id(aircraft_id);
            flightPlanRequest.setPilot_id(pilot_id);
            FlightPlanDetailsHybrid flightPlanDetails = new FlightPlanDetailsHybrid();
            flightPlanRequest.setFlight_plan_details(flightPlanDetails);
            flightPlanDetails.setGross_weight_lb(gross_weight_lb);
            flightPlanDetails.setFuel_indicator(fuel_indicator);
            flightPlanDetails.setFuel_weight_lb(fuel_weight_lb);
            flightPlanDetails.setPayload_weight_lb(payload_weight_lb);

            Log.d(TAG, String.valueOf(flightPlanRequest));
        }
        //endregion

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

    private class AircraftsDownload {

        private String access_token;
        private String authorization;

        private UpdateAircraftData updateAircraftData;

        AircraftsDownload() {
            access_token = preferences.getString("access_token", "");
            authorization = "Bearer " + access_token;
        }

        void getAircrafts() {
            String extension = getResources().getString(R.string.url_aircrafts);
            String url = mHostname + extension;

            Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d(TAG, String.valueOf(response));
                    for (int i = 0; i < response.length(); i++) {
                        Aircraft aircraft = new Aircraft(response.optJSONObject(i));
                        mAircrafts.add(aircraft);
                    }
                    attachFragments();
                    updateAircraftData.updateData();

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
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("authorization", authorization);
                    return params;
                }
            };
            VolleyRequests.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
        }

        public void setUpdateAircraftData(UpdateAircraftData updateAircraftData) {
            this.updateAircraftData = updateAircraftData;
        }
    }



}
