package com.zemoso.atul.maps.adapters;

import android.content.Context;
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
import com.zemoso.atul.maps.javabeans.FlightPlanResponse;

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
        FlightPlanResponse flightPlanResponse = flightPlanResponses.get(pos);
        String name = flightPlanResponse.getName();
        String created_by = flightPlanResponse.getCreated_by();
        String type = flightPlanResponse.getFlight_plan_details().getFlight_plan_type();
        String updated_time = "Last updated: " + context.getResources().getString(R.string.plan_card_time_default);
        Log.d(TAG, (flightPlanResponse.getCreated_at()));

        holder.heading.setText(name);
        holder.created_by.setText(created_by);
        holder.type.setText(type);
        holder.updated_time.setText(updated_time);
        holder.flight_type.setText(type);

        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int colorPrimary = context.getResources().getColor(R.color.primary);
                int colorWhite = context.getResources().getColor(R.color.white);
                holder.card.setCardBackgroundColor(colorPrimary);
                holder.fly_now.setVisibility(View.VISIBLE);
                holder.flight_type.setVisibility(View.GONE);
                holder.heading.setTextColor(colorWhite);
                holder.created_by.setTextColor(colorWhite);
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
                    holder.card.setCardBackgroundColor(colorWhite);
                    holder.fly_now.setVisibility(View.GONE);
                    holder.flight_type.setVisibility(View.VISIBLE);
                    holder.heading.setTextColor(colorAccent);
                    holder.created_by.setTextColor(colorAccent);
                    holder.type.setTextColor(colorAccent);
                    holder.updated_time.setTextColor(colorAccent);
                    holder.isLongPressed = false;
                } else {
//                    TODO: Start Detail Activity
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
        private TextView created_by;
        private TextView type;
        private TextView updated_time;
        private TextView flight_type;
        private CardView card;
        private ProgressBar progressBar;
        private Button fly_now;
        private Boolean isLongPressed;

        RecyclerViewHolder(View itemView) {
            super(itemView);

            heading = itemView.findViewById(R.id.plan_card_heading);
            created_by = itemView.findViewById(R.id.plan_card_created_by);
            type = itemView.findViewById(R.id.plan_card_type);
            updated_time = itemView.findViewById(R.id.plan_card_updated_time);
            flight_type = itemView.findViewById(R.id.plan_card_flight_type);
            card = itemView.findViewById(R.id.plan_card);
            progressBar = itemView.findViewById(R.id.plan_card_progress);
            fly_now = itemView.findViewById(R.id.plan_card_fly_now_button);
            isLongPressed = false;
        }
    }
}
