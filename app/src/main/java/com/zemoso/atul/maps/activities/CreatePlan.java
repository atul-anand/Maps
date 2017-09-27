package com.zemoso.atul.maps.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.zemoso.atul.maps.fragments.TextEntryDialog;
import com.zemoso.atul.maps.interfaces.AttemptUploadPlan;
import com.zemoso.atul.maps.interfaces.UpdateAircraftData;
import com.zemoso.atul.maps.javabeans.Aircraft;
import com.zemoso.atul.maps.javabeans.FlightPlanDetailsHybrid;
import com.zemoso.atul.maps.javabeans.FlightPlanRequestHybrid;
import com.zemoso.atul.maps.javabeans.ReservedVolume;
import com.zemoso.atul.maps.javabeans.Waypoint;
import com.zemoso.atul.maps.singletons.VolleyRequests;
import com.zemoso.atul.maps.utils.DateTimeUtils;

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
    private static FlightPlanRequestHybrid flightPlanRequest;
    private static List<Waypoint> mWaypoints;
    private static List<ReservedVolume> mReservedVolumes;
    private static List<Aircraft> mAircrafts;
    private SharedPreferences preferences;
    private Bundle bundle;
    private String mHostname;
    private CreatePlan.FlightPlanUpload mAuthTask = null;
    private CreatePlan.FlightDetail mFlightDetailFragment = null;
    private CreatePlan.AircraftsDownload mAircraftsDownload = null;
    private GoogleMap mMap;
    private Map<Marker, Waypoint> mMapMarkers;
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
        mReservedVolumes = new ArrayList<>();
        mWaypoints = new ArrayList<>();

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
        mAircraftsDownload.setUpdateAircraftData(updateAircraftDataImpl);
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
        if (mFlightDetailFragment.validateData())
            createFlightPlanRequest();

    }

    private void createFlightPlanRequest() {

        mAuthTask = new FlightPlanUpload(flightPlanRequest);
        mAuthTask.uploadPlan();

    }

    public static class FlightDetail extends Fragment {

        private static final String TAG = CreatePlan.FlightDetail.class.getSimpleName();

        private TextView mFlightPlan;
        private TextView mDescription;
        private Spinner mFlightType;
        private TextView mDate;
        private TextView mStartTime;
        private TextView mEndTime;
        private Spinner mAltitudeMode;
        private Spinner mAircraft;
        private TextView mGrossWt;
        private TextView mPayloadWt;
        private TextView mFuelLoading;
        private TextView mFuelIndicator;

        private String getAirspace;
        private String hideDetails;

        private Button mGetAirspace;
        private Button mRequestContract;
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



        private String mFlightPlanText;
        private String mDescriptionText;
        private String mFlightTypeText;
        private String mDateText;
        private String mStartTimeText;
        private String mEndTimeText;
        private String mOrganizationIdText;
        private String mAircraftIdText;
        private String mAltitudeModeText;

        private List<String> aircraftArray;
        private Map<Integer, Aircraft> aircraftsMap;

        private String mGrossWtText;
        private String mPayloadWtText;
        private String mFuelLoadingText;
        private String mFuelIndicatorText;


        private Boolean hasDetailedView = false;

        private AttemptUploadPlan attemptUploadPlanImpl;


        //region Pickers and Listeners
        private View.OnClickListener requestListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptUploadPlanImpl.attemptUpload();
                mGetAirspace.callOnClick();
            }
        };
        private View.OnClickListener getListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasDetailedView) {
                    mAirspaceDetail.setVisibility(View.GONE);
                    mRequestContract.setVisibility(View.GONE);
                    mGetAirspace.setText(getAirspace);
                    hasDetailedView = false;
                } else {
                    mAirspaceDetail.setVisibility(View.VISIBLE);
                    mRequestContract.setVisibility(View.VISIBLE);
                    mGetAirspace.setText(hideDetails);
                    hasDetailedView = true;
                }
            }
        };
        private TimePickerDialog.OnTimeSetListener startTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                mStartTimeText = DateTimeUtils.getTimeFromElements(hourOfDay, minute);
                mStartTime.setText(mStartTimeText);
            }
        };
        private View.OnClickListener startTimeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                TimePickerDialog dialog = new TimePickerDialog(getActivity(), startTimeListener,
                        cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true);
                dialog.show();
            }
        };
        private TimePickerDialog.OnTimeSetListener endTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                mEndTimeText = DateTimeUtils.getTimeFromElements(hourOfDay, minute);
                mEndTime.setText(mEndTimeText);
            }
        };
        private View.OnClickListener endTimeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                TimePickerDialog dialog = new TimePickerDialog(getActivity(), endTimeListener,
                        cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true);
                dialog.show();
            }
        };
        private DatePickerDialog.OnDateSetListener datePickerListener
                = new DatePickerDialog.OnDateSetListener() {

            // when dialog box is closed, below method will be called.
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {
                //Do whatever you want
                mDateText = DateTimeUtils.getDateFromElements(selectedYear, selectedMonth + 1, selectedDay);
                mDate.setText(mDateText);
            }
        };
        private View.OnClickListener selectDateListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), datePickerListener,
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        };
        private TextEntryDialog.OnTextDialogCallBack flightPlanCallBack = new TextEntryDialog.OnTextDialogCallBack() {
            @Override
            public void onFragmentInteraction(String text, int i) {
                switch (i) {
                    case TextEntryDialog.OK:
                        mFlightPlanText = text;
                        mFlightPlan.setText(mFlightPlanText);
                        break;
                    case TextEntryDialog.CANCEL:
                        break;
                }

            }
        };
        private View.OnClickListener selectFlightPlanListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextEntryDialog dialogFragment = TextEntryDialog.newInstance(flightPlanCallBack);
                String title = getResources().getString(R.string.detail_item_flight_plan);
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("content", mFlightPlanText);
                bundle.putInt("inputType", InputType.TYPE_CLASS_TEXT);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "flightPlan");
            }
        };
        private TextEntryDialog.OnTextDialogCallBack descriptionCallBack = new TextEntryDialog.OnTextDialogCallBack() {
            @Override
            public void onFragmentInteraction(String text, int i) {
                switch (i) {
                    case TextEntryDialog.OK:
                        mDescriptionText = text;
                        mDescription.setText(mDescriptionText);
                        break;
                    case TextEntryDialog.CANCEL:
                        break;
                }

            }
        };
        private View.OnClickListener selectDescriptionListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextEntryDialog dialogFragment = TextEntryDialog.newInstance(descriptionCallBack);
                String title = getResources().getString(R.string.detail_item_description);
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("content", mDescriptionText);
                bundle.putInt("inputType", InputType.TYPE_CLASS_TEXT);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "description");
            }
        };
        private TextEntryDialog.OnTextDialogCallBack grossWeightCallBack = new TextEntryDialog.OnTextDialogCallBack() {
            @Override
            public void onFragmentInteraction(String text, int i) {
                switch (i) {
                    case TextEntryDialog.OK:
                        mGrossWtText = text;
                        mGrossWt.setText(mGrossWtText);
                        break;
                    case TextEntryDialog.CANCEL:
                        break;
                }

            }
        };
        private View.OnClickListener selectGrossWtListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextEntryDialog dialogFragment = TextEntryDialog.newInstance(grossWeightCallBack);
                String title = getResources().getString(R.string.detail_item_gross_weight);
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("content", mGrossWtText);
                bundle.putInt("inputType", InputType.TYPE_CLASS_NUMBER);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "grossWt");
            }
        };
        private TextEntryDialog.OnTextDialogCallBack payloadWeightCallBack = new TextEntryDialog.OnTextDialogCallBack() {
            @Override
            public void onFragmentInteraction(String text, int i) {
                switch (i) {
                    case TextEntryDialog.OK:
                        mPayloadWtText = text;
                        mPayloadWt.setText(mPayloadWtText);
                        break;
                    case TextEntryDialog.CANCEL:
                        break;
                }

            }
        };
        private View.OnClickListener selectPayloadWtListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextEntryDialog dialogFragment = TextEntryDialog.newInstance(payloadWeightCallBack);
                String title = getResources().getString(R.string.detail_item_payload_weight);
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("content", mPayloadWtText);
                bundle.putInt("inputType", InputType.TYPE_CLASS_NUMBER);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "payloadWt");
            }
        };
        private TextEntryDialog.OnTextDialogCallBack fuelLoadingCallBack = new TextEntryDialog.OnTextDialogCallBack() {
            @Override
            public void onFragmentInteraction(String text, int i) {
                switch (i) {
                    case TextEntryDialog.OK:
                        mFuelLoadingText = text;
                        mFuelLoading.setText(mFuelLoadingText);
                        break;
                    case TextEntryDialog.CANCEL:
                        break;
                }

            }
        };
        private View.OnClickListener selectFuelLoadingListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextEntryDialog dialogFragment = TextEntryDialog.newInstance(fuelLoadingCallBack);
                String title = getResources().getString(R.string.detail_item_fuel_loading);
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("content", mFuelLoadingText);
                bundle.putInt("inputType", InputType.TYPE_CLASS_NUMBER);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "fuelLoading");
            }
        };
        private TextEntryDialog.OnTextDialogCallBack fuelIndicatorCallBack = new TextEntryDialog.OnTextDialogCallBack() {
            @Override
            public void onFragmentInteraction(String text, int i) {
                switch (i) {
                    case TextEntryDialog.OK:
                        mFuelIndicatorText = text;
                        mFuelIndicator.setText(mFuelIndicatorText);
                        break;
                    case TextEntryDialog.CANCEL:
                        break;
                }

            }
        };
        private View.OnClickListener selectFuelIndicatorListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextEntryDialog dialogFragment = TextEntryDialog.newInstance(fuelIndicatorCallBack);
                String title = getResources().getString(R.string.detail_item_fuel_indicator);
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("content", mFuelIndicatorText);
                bundle.putInt("inputType", InputType.TYPE_CLASS_NUMBER);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "fuelIndicator");
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
            hideDetails = getActivity().getResources().getString(R.string.detail_hide_airspace);

            mGetAirspace = view.findViewById(R.id.profile_get_airspace);
            mRequestContract = view.findViewById(R.id.profile_request_contract);
            mAirspaceDetail = view.findViewById(R.id.drawer);

            mAirspaceDetail.setVisibility(View.GONE);

            mFlightPlan = view.findViewById(R.id.text_flight_plan);
            mDescription = view.findViewById(R.id.text_description);
            mFlightType = view.findViewById(R.id.text_flight_type);
            mDate = view.findViewById(R.id.text_date);
            mStartTime = view.findViewById(R.id.text_start_time);
            mEndTime = view.findViewById(R.id.text_end_time);
            mAltitudeMode = view.findViewById(R.id.text_altitude_mode);
            mAircraft = view.findViewById(R.id.text_aircraft);
            mGrossWt = view.findViewById(R.id.text_gross_weight);
            mPayloadWt = view.findViewById(R.id.text_payload_weight);
            mFuelLoading = view.findViewById(R.id.text_fuel_loading);
            mFuelIndicator = view.findViewById(R.id.text_fuel_indicator);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Log.d(TAG, "ActivityCreated");

            setDefaults();
            setClickListeners();

        }
        //endregion


        private void setAircraftSpinnerData() {
            aircraftArray = new ArrayList<>();
//            aircraftMap = new HashMap<>();
            aircraftsMap = new HashMap<>();
//            organizationMap = new HashMap<>();
            for (int i = 0; i < mAircrafts.size(); i++) {
                Aircraft aircraft = mAircrafts.get(i);
                Log.d(TAG, String.valueOf(aircraft));
//                aircraftMap.put(i, aircraft.getId());
//                organizationMap.put(i, aircraft.getOrganization_id());
                aircraftArray.add(aircraft.getName());
                aircraftsMap.put(i, aircraft);

            }
            Log.d(TAG, String.valueOf(aircraftArray.get(0)));
            if (getActivity() != null) {
                ArrayAdapter<String> aircraftAdapter = new ArrayAdapter<>(getContext(),
                        R.layout.card_autocomplete_item, aircraftArray);
//                aircraftAdapter.setDropDownViewResource(R.layout.card_autocomplete_item);
                mAircraft.setAdapter(aircraftAdapter);
            }
        }

        private void setClickListeners() {
            mFlightPlan.setOnClickListener(selectFlightPlanListener);
            mDescription.setOnClickListener(selectDescriptionListener);
            mDate.setOnClickListener(selectDateListener);
            mStartTime.setOnClickListener(startTimeClickListener);
            mEndTime.setOnClickListener(endTimeClickListener);
//            mAircraft.setOnItemClickListener(aircraftListener);

            mGrossWt.setOnClickListener(selectGrossWtListener);
            mPayloadWt.setOnClickListener(selectPayloadWtListener);
            mFuelLoading.setOnClickListener(selectFuelLoadingListener);
            mFuelIndicator.setOnClickListener(selectFuelIndicatorListener);
            mGetAirspace.setOnClickListener(getListener);
            mRequestContract.setOnClickListener(requestListener);
        }

        private void setDefaults() {
            Calendar cal = Calendar.getInstance();

            mDateText = DateTimeUtils.getDateFromCalendar(cal);
            Log.d(TAG, String.valueOf(cal.getTime()));
            mDate.setText(mDateText);

            mStartTimeText = DateTimeUtils.getTimeFromCalendar(cal);
            mStartTime.setText(mStartTimeText);

            cal.add(Calendar.MINUTE, 10);
            mEndTimeText = DateTimeUtils.getTimeFromCalendar(cal);
            mEndTime.setText(mEndTimeText);

            String defaultDouble = "0";
            mGrossWtText = defaultDouble;
            mPayloadWtText = defaultDouble;
            mFuelLoadingText = defaultDouble;
            mFuelIndicatorText = defaultDouble;

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
            mFuelIndicator.setError(null);
        }

        private void getData() {
            Log.d(TAG, "Flight Detail Data");
            mFlightPlanText = mFlightPlan.getText().toString().trim();
//            int pos = mAircraft.getSelectedItemPosition();
//            Log.d(TAG, String.valueOf(pos));
//            Aircraft aircraft = aircraftsMap.get(pos);
//            mAircraftIdText  = aircraft.getId();
//            mOrganizationIdText  = aircraft.getOrganization_id();
            status = "DRAFT";

            mAircraftIdText = "";
            mOrganizationIdText = "";
            mDescriptionText = mDescription.getText().toString().trim();
            mFlightTypeText = mFlightType.getSelectedItem().toString().trim();
            flight_plan_category = "";
            route_id = "";
            mAltitudeModeText = mAltitudeMode.getSelectedItem().toString().trim();
            props = new JSONObject();
            mGrossWtText = mGrossWt.getText().toString().trim();
            mFuelLoadingText = mFuelLoading.getText().toString().trim();
            mFuelIndicatorText = mFuelIndicator.getText().toString().trim();
            mPayloadWtText = mPayloadWt.getText().toString().trim();

        }

        private boolean validateData() {
            Log.d(TAG, "Validations");
            boolean cancel = false;
            View focusView = null;
            String fieldRequired = getResources().getString(R.string.error_field_required);
            String numberRequired = getResources().getString(R.string.error_number_required);

            if (mFlightPlanText.isEmpty()) {
                mFlightPlan.setError(fieldRequired);
                focusView = mFlightPlan;
                cancel = true;
            }

            if (mDescriptionText.isEmpty()) {
                mDescription.setError(fieldRequired);
                focusView = mDescription;
                cancel = true;
            }

            try {
                Double data = Double.parseDouble(mGrossWtText);
                Log.d(TAG, String.valueOf(data));
            } catch (Exception e) {
                mGrossWt.setError(numberRequired);
                focusView = mGrossWt;
                cancel = true;
            }
            try {
                Double data = Double.parseDouble(mPayloadWtText);
                Log.d(TAG, String.valueOf(data));
            } catch (Exception e) {
                mPayloadWt.setError(numberRequired);
                focusView = mPayloadWt;
                cancel = true;
            }
            try {
                Double data = Double.parseDouble(mFuelIndicatorText);
                Log.d(TAG, String.valueOf(data));
            } catch (Exception e) {
                mFuelIndicator.setError(numberRequired);
                focusView = mFuelIndicator;
                cancel = true;
            }

            try {
                Double data = Double.parseDouble(mFuelLoadingText);
                Log.d(TAG, String.valueOf(data));
            } catch (Exception e) {
                mFuelLoading.setError(numberRequired);
                focusView = mFuelLoading;
                cancel = true;
            }

            if (cancel) {
                focusView.requestFocus();
                return false;
            } else {
                setData();
                return true;
            }
        }


        public void setData() {
            name = mFlightPlanText;
            aircraft_id = mAircraftIdText;
            organization_id = "";
            status = "DRAFT";
            organization_id = mOrganizationIdText;
            description = mDescriptionText;
            flight_plan_type = mFlightTypeText;
            flight_plan_category = "";
            route_id = "";
            altitude_mode = mAltitudeModeText;
            props = new JSONObject();
            gross_weight_lb = Double.valueOf(mGrossWtText);
            fuel_weight_lb = Double.valueOf(mFuelLoadingText);
            fuel_indicator = Double.valueOf(mFuelIndicatorText);
            payload_weight_lb = Double.valueOf(mPayloadWtText);
            flightPlanRequest.setName(name);
            flightPlanRequest.setAircraft_id(aircraft_id);
            flightPlanRequest.setPilot_id(pilot_id);
            flightPlanRequest.setStatus(status);
            flightPlanRequest.setOrganization_id(organization_id);
            flightPlanRequest.setDescription(description);
            FlightPlanDetailsHybrid flightPlanDetails = new FlightPlanDetailsHybrid();
            flightPlanRequest.setFlight_plan_details(flightPlanDetails);
            flightPlanDetails.setFlight_plan_type(flight_plan_type);
            flightPlanDetails.setFlight_plan_category(flight_plan_category);
            flightPlanDetails.setRoute_id(route_id);
            flightPlanDetails.setAltitude_mode(altitude_mode);
            flightPlanDetails.setProps(props);
            flightPlanDetails.setGross_weight_lb(gross_weight_lb);
            flightPlanDetails.setFuel_indicator(fuel_indicator);
            flightPlanDetails.setFuel_weight_lb(fuel_weight_lb);
            flightPlanDetails.setPayload_weight_lb(payload_weight_lb);
            flightPlanDetails.setReserved_volumes(mReservedVolumes);
            flightPlanDetails.setWaypoints_info(mWaypoints);
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
                    mAuthTask = null;
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, String.valueOf(error));
                    mAuthTask = null;
                }
            };
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, mFlightPlanRequest,
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
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/json");
                    params.put("authorization", authorization);
                    return params;
                }
            };
            VolleyRequests.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
        }

        void setUpdateAircraftData(UpdateAircraftData updateAircraftData) {
            this.updateAircraftData = updateAircraftData;
        }
    }



}
