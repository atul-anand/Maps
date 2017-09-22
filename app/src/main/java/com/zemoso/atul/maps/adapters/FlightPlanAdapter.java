package com.zemoso.atul.maps.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zemoso.atul.maps.R;
import com.zemoso.atul.maps.activities.MapsActivity;
import com.zemoso.atul.maps.javabeans.FlightPlanResponse;
import com.zemoso.atul.maps.utils.DateTimeUtils;

import java.util.List;

/**
 * Created by zemoso on 8/9/17.
 */

public class FlightPlanAdapter extends RecyclerView.Adapter<FlightPlanAdapter.RecyclerViewHolder> {

    private static final String TAG = FlightPlanAdapter.class.getSimpleName();

    private List<FlightPlanResponse> flightPlanResponses;
    private Context context;

    public FlightPlanAdapter(List<FlightPlanResponse> flightPlanResponses, Context context) {
        this.flightPlanResponses = flightPlanResponses;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.card_flight_plan, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        final FlightPlanResponse flightPlanResponse = flightPlanResponses.get(pos);
        String name = flightPlanResponse.getName();
        String created_by = flightPlanResponse.getCreated_by();
        String type = "Type: " + flightPlanResponse.getFlight_plan_details().getFlight_plan_type();
        String updated_time = "Created: " + DateTimeUtils.getTimeFromNow(flightPlanResponse.getCreated_at());
        String status = flightPlanResponse.getStatus();
        Log.d(TAG, (flightPlanResponse.getCreated_at()));

        holder.heading.setText(name);
//        holder.created_by.setText(created_by);
        holder.type.setText(type);
        holder.updated_time.setText(updated_time);
        holder.status.setText(status);

        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int colorPrimary = context.getResources().getColor(R.color.primary);
                int colorWhite = context.getResources().getColor(R.color.white);
                holder.card.setCardBackgroundColor(colorPrimary);
                holder.fly_now.setVisibility(View.VISIBLE);
                holder.status.setVisibility(View.GONE);
                holder.heading.setTextColor(colorWhite);
//                holder.created_by.setTextColor(colorWhite);
                holder.type.setTextColor(colorWhite);
                holder.updated_time.setTextColor(colorWhite);
                holder.isLongPressed = true;
                return true;
            }
        });
        holder.card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (holder.isLongPressed) {
                    int colorPrimary = context.getResources().getColor(R.color.primary);
                    int colorAccent = context.getResources().getColor(R.color.accent);
                    int colorWhite = context.getResources().getColor(R.color.white);
                    int colorDark = context.getResources().getColor(R.color.cardview_dark_background);
                    holder.card.setCardBackgroundColor(colorWhite);
                    holder.fly_now.setVisibility(View.GONE);
                    holder.status.setVisibility(View.VISIBLE);
                    holder.heading.setTextColor(colorDark);
//                    holder.created_by.setTextColor(colorAccent);
                    holder.type.setTextColor(colorDark);
                    holder.updated_time.setTextColor(colorAccent);
                    holder.isLongPressed = false;
                } else {
//                    TODO: Start Detail Activity
                    Intent intent = new Intent(context, MapsActivity.class);
                    Bundle args = new Bundle();
                    args.putString("flight_plan_id", flightPlanResponse.getId());
                    args.putString("flight_title", flightPlanResponse.getName());
                    args.putString("contract_id", flightPlanResponse.getContract_id());
                    intent.putExtras(args);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return flightPlanResponses.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView heading;
        //        private TextView created_by;
        private TextView type;
        private TextView updated_time;
        private TextView status;
        private CardView card;
        private ProgressBar progressBar;
        private Button fly_now;
        private Boolean isLongPressed;

        RecyclerViewHolder(View itemView) {
            super(itemView);

            heading = itemView.findViewById(R.id.plan_card_heading);
//            created_by = itemView.findViewById(R.id.plan_card_created_by);
            type = itemView.findViewById(R.id.plan_card_type);
            updated_time = itemView.findViewById(R.id.plan_card_updated_time);
            status = itemView.findViewById(R.id.plan_card_status);
            card = itemView.findViewById(R.id.plan_card);
            progressBar = itemView.findViewById(R.id.plan_card_progress);
            fly_now = itemView.findViewById(R.id.plan_card_fly_now_button);
            isLongPressed = false;
        }
    }
}
