package com.example.projforlabs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RvActivity extends AppCompatActivity {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private RvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
        refreshLayout = findViewById(R.id.swipe);

        recyclerView = (RecyclerView) findViewById(R.id.r_view);
        linearLayout = findViewById(R.id.main_data);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        checkInernet();
        loadData();
        refresh();
    }

    public void checkInernet() {
        if (!networkConnectionCheck()) {
            Snackbar.make(linearLayout, R.string.conError, Snackbar.LENGTH_SHORT);
        }
    }

    public void loadData() {
        final JsonDataApi jsonApi = getApplicationEx().getApi();
        final Call<List<Driver>> call = jsonApi.getDriver();
        call.enqueue(new Callback<List<Driver>>() {
            @Override
            public void onResponse(Call<List<Driver>> call, Response<List<Driver>> response) {
                adapter = new RvAdapter(response.body());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Driver>> call, Throwable t) {

            }
        });
    }

    public void refresh() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkInernet();
                loadData();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    public boolean networkConnectionCheck() {
        try {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;

            if (manager != null) {
                networkInfo = manager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        } catch (NullPointerException e) {
            return false;
        }
    }

    private ApplicationStaff getApplicationEx() {
        return (ApplicationStaff) getApplication();
    }

}
