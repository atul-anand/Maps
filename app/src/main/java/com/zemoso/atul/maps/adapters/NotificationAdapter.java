package com.zemoso.atul.maps.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zemoso.atul.maps.R;
import com.zemoso.atul.maps.javabeans.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.RecyclerViewHolder> {

    private static final String TAG = NotificationAdapter.class.getSimpleName();

    private Context context;
    private List<Notification> notifications;

    public NotificationAdapter(Context context, List<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.card_notifications, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        Notification notification = notifications.get(pos);
        String heading = notification.getFlight_plan_name();
        String subHeading = notification.getRoute_owner_name();
        String type = "Type: " + notification.getFlight_plan_type();
        Log.d(TAG, type);
        holder.heading.setText(heading);
        holder.created_by.setText(subHeading);
        holder.type.setText(type);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView heading;
        private TextView created_by;
        private TextView type;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.notification_card_heading);
            created_by = itemView.findViewById(R.id.notification_card_created_by);
            type = itemView.findViewById(R.id.notification_card_type);
        }
    }
}
