package com.example.projforlabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class RvActivity extends AppCompatActivity {

    private SwipeRefreshLayout refreshLayout;
    private List<Driver> drivers;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private static int time;
    private long backPressedTime;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
        refreshLayout = findViewById(R.id.swipe);
        recyclerView = (RecyclerView)findViewById(R.id.r_view);

        fAuth = FirebaseAuth.getInstance();
        linearLayout = findViewById(R.id.main_data);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });

        checkInernet();
        initializeAdapter();


    }

    public void checkInernet(){
        if (!network()){
            Snackbar.make(linearLayout, R.string.conError, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.close, new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorAccent)).show();
        }
    }

    public boolean network(){
        try {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;

            if (manager != null){
                networkInfo = manager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        }catch (NullPointerException e){
            return false;
        }
    }

    private void initializeAdapter(){
        RvAdapter adapter = new RvAdapter(drivers);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        time = 2000;
        if (backPressedTime + time > System.currentTimeMillis()) {
            fAuth.getInstance().signOut();
            finishAffinity();
            finish();
        } else {
            Toast.makeText(getBaseContext(), R.string.pressToExit, Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();

    }
}
