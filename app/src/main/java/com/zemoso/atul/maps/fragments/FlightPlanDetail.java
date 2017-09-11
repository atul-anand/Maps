package com.zemoso.atul.maps.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zemoso.atul.maps.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FlightPlanDetail extends Fragment {


    public FlightPlanDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flight_plan_detail, container, false);
    }

}
