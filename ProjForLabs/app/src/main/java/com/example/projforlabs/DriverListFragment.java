package com.example.projforlabs;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverListFragment extends Fragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linearLayout;
    private RvAdapter adapter;
    private View rootView;

    public DriverListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_data_list, container, false);

        initViews();
        checkInternetConnection();
        loadData();

        return rootView;
    }

    private void checkInternetConnection() {
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        NetworkCheck receiver = new NetworkCheck(linearLayout);
        Objects.requireNonNull(getActivity()).registerReceiver(receiver, filter);
    }

    private void initViews() {
        recyclerView = rootView.findViewById(R.id.data_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        linearLayout = rootView.findViewById(R.id.linearLayout);
        swipeRefreshLayout = rootView.findViewById(R.id.data_list_swipe_refresh);
        SwipeToRefresh();
    }

    private void SwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        loadData();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    private void loadData() {
        swipeRefreshLayout.setRefreshing(true);
        final DataApi dataApi = getApplicationStaff().getApi();
        final Call<List<Driver>> call = dataApi.getDriver();

        call.enqueue(new Callback<List<Driver>>() {
            @Override
            public void onResponse(final Call<List<Driver>> call,
                                   final Response<List<Driver>> response) {
                adapter = new RvAdapter(getActivity(), response.body());
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Driver>> call, Throwable t) {
            }
        });
    }

    private ApplicationStaff getApplicationStaff() {
        return ((ApplicationStaff) Objects.requireNonNull(getActivity()).getApplication());
    }
}