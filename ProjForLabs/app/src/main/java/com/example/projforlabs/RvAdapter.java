package com.example.projforlabs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.DriverViewHolder> {

    private List<Driver> drivers;

    RvAdapter(List<Driver> drivers) {
        this.drivers = drivers;
    }


    @Override
    public DriverViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        DriverViewHolder driverViewHolder = new DriverViewHolder(v);
        return driverViewHolder;
    }

    @Override
    public void onBindViewHolder(final DriverViewHolder driverViewHolder, final int i) {
        Picasso.get().load(drivers.get(i).getPhoto()).into(driverViewHolder.avatar);
        driverViewHolder.driverName.setText(drivers.get(i).getName());
        driverViewHolder.carModel.setText(drivers.get(i).getCarModel());
        driverViewHolder.reiting.setText(drivers.get(i).getReiting());
        driverViewHolder.status.setText(drivers.get(i).getStatus());
    }

    @Override
    public int getItemCount() {
        return drivers.size();
    }

    public static class DriverViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView avatar;
        TextView driverName;
        TextView carModel;
        TextView reiting;
        TextView status;
        SwipeRefreshLayout refreshLayout;

        DriverViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv);
            refreshLayout = (SwipeRefreshLayout) itemView.findViewById(R.id.swipe);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            driverName = (TextView) itemView.findViewById(R.id.driver_name);
            carModel = (TextView) itemView.findViewById(R.id.car_model);
            status = (TextView) itemView.findViewById(R.id.status);
            reiting = (TextView) itemView.findViewById(R.id.reiting);
        }
    }

}

