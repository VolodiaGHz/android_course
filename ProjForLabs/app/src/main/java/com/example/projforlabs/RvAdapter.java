package com.example.projforlabs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.DriverViewHolder> {
    private List<Driver> drivers;
    private Context context;

    public RvAdapter(Context context, List<Driver> drivers) {
        this.context = context;
        this.drivers = drivers;
    }

    @NonNull
    @Override
    public RvAdapter.DriverViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                                         final int viewType) {
        final View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        //context = parent.getContext();
        return new DriverViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DriverViewHolder driverViewHolder, final int i) {
        Picasso.get().load(drivers.get(i).getPhoto()).into(driverViewHolder.avatar);
        driverViewHolder.driverName.setText(drivers.get(i).getName());
        driverViewHolder.carModel.setText(drivers.get(i).getCarModel());
        driverViewHolder.rating.setText(drivers.get(i).getRating());
        driverViewHolder.status.setText(drivers.get(i).getStatus());
        driverViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openItemDetails(i);
            }
        });
    }

    private void openItemDetails(int position) {
        Intent intent = new Intent(context, DataContainerActivity.class);
        intent.putExtra("name", drivers.get(position).getName());
        intent.putExtra("model", drivers.get(position).getCarModel());
        intent.putExtra("rating", drivers.get(position).getRating());
        intent.putExtra("status", drivers.get(position).getStatus());
        intent.putExtra("image", drivers.get(position).getPhoto());

        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return drivers.size();
    }

    public static class DriverViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView driverName;
        private TextView carModel;
        private TextView rating;
        private TextView status;
        //        private SwipeRefreshLayout refreshLayout;
        private LinearLayout layout;

        DriverViewHolder(View itemView) {
            super(itemView);
//            refreshLayout = (SwipeRefreshLayout) itemView.findViewById(R.id.swipe);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            driverName = (TextView) itemView.findViewById(R.id.driver_name);
            carModel = (TextView) itemView.findViewById(R.id.car_model);
            status = (TextView) itemView.findViewById(R.id.status);
            rating = (TextView) itemView.findViewById(R.id.rating);
            layout = itemView.findViewById(R.id.main_layout);
        }
    }
}