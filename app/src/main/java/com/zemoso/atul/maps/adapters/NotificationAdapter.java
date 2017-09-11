package com.zemoso.atul.maps.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zemoso.atul.maps.R;
import com.zemoso.atul.maps.javabeans.Notification;

import java.util.List;

/**
 * Created by zemoso on 11/9/17.
 */

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

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        RecyclerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
