package com.example.projforlabs;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.DriverViewHolder> {
    public static class DriverViewHolder extends RecyclerView.ViewHolder{
        private CardView cv;
        private TextView driverName;
        private TextView carModel;
        private TextView reiting;
        private TextView status;
        private SwipeRefreshLayout refreshLayout;

        DriverViewHolder(View itemView){
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.cv);
            refreshLayout = (SwipeRefreshLayout)itemView.findViewById(R.id.swipe);
            driverName = (TextView)itemView.findViewById(R.id.driver_name);
            carModel = (TextView)itemView.findViewById(R.id.car_model);
            status = (TextView)itemView.findViewById(R.id.status);
            reiting = (TextView)itemView.findViewById(R.id.reiting);



        }
    }

    List<Driver> drivers;

    RvAdapter(List<Driver> drivers){
        this.drivers = drivers;
    }


    @Override
    public DriverViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        DriverViewHolder dvh = new DriverViewHolder(v);
        return dvh;
    }

    @Override
    public void onBindViewHolder(final DriverViewHolder driverViewHolder, final int i){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-android-course-528bc.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Driver>> call = jsonPlaceHolderApi.getDriver();

        call.enqueue(new Callback<List<Driver>>() {
            @Override
            public void onResponse(Call<List<Driver>> call, Response<List<Driver>> response) {
                if (!response.isSuccessful()){
                    return;
                }
                List<Driver> drivers =response.body();
                for (Driver driver: drivers){
                    if (drivers.size() > i) {
                        driverViewHolder.driverName.setText(drivers.get(i).getName());
                        driverViewHolder.carModel.setText(drivers.get(i).getCarModel());
                        driverViewHolder.reiting.setText(drivers.get(i).getReiting());
                        driverViewHolder.status.setText(drivers.get(i).getStatus());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Driver>> call, Throwable t) {
            }
        });

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);

    }

    @Override
    public int getItemCount(){
        return Log.d("Problem!", "Hello");

    }


}

