package com.example.projforlabs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsActivity extends AppCompatActivity {
    private RvAdapter adapter;
    private TextView name;
    private TextView model;
    private TextView rating;
    private TextView status;
    private ImageView avatar;
    private TextView error;
    private Button back;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        FirebaseMessaging.getInstance().subscribeToTopic("HRYNYK_LAB_5");
        name = findViewById(R.id.note_name);
        model = findViewById(R.id.note_model);
        rating = findViewById(R.id.note_rating);
        status = findViewById(R.id.note_status);
        avatar = findViewById(R.id.note_avatar);
        error = findViewById(R.id.note_error);
        back = findViewById(R.id.note_button);
        data = getIntent().getStringExtra("text");
        getData(data);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationsActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void getData(final String data) {
        final DataApi api = getApplicationStaff().getApi();
        final Call<List<Driver>> call = api.getDriver();
        call.enqueue(new Callback<List<Driver>>() {
            @Override
            public void onResponse(final Call<List<Driver>> call,
                                   final Response<List<Driver>> response) {
                adapter = new RvAdapter(response.body());
                for (int i = 0; adapter.drivers.size() > i; i++) {
                    if (adapter.drivers.get(i).getName().equals(data)) {
                        Picasso.get().load(adapter.drivers.get(i).getPhoto()).into(avatar);
                        name.setText(adapter.drivers.get(i).getName());
                        model.setText(adapter.drivers.get(i).getCarModel());
                        rating.setText(adapter.drivers.get(i).getRating());
                        status.setText(adapter.drivers.get(i).getStatus());
                    }
                }
                if (name.length() == 0) {
                    error.setText(R.string.error);
                }
            }

            @Override
            public void onFailure(Call<List<Driver>> call, Throwable t) {
            }
        });
    }

    private ApplicationStaff getApplicationStaff() {
        return (ApplicationStaff) getApplication();
    }
}