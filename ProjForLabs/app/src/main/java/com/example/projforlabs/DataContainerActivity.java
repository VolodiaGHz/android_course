package com.example.projforlabs;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DataContainerActivity extends AppCompatActivity {
    private TextView name;
    private TextView model;
    private TextView rating;
    private TextView status;
    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_container);
        name = findViewById(R.id.name_of_driver);
        model = findViewById(R.id.model_of_car);
        rating = findViewById(R.id.rating_of_driver);
        status = findViewById(R.id.status_of_driver);
        photo = findViewById(R.id.driver_avatar);

        getData();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void getData() {
        if (getIntent().hasExtra("name") &&
                getIntent().hasExtra("model") &&
                getIntent().hasExtra("rating") &&
                getIntent().hasExtra("status")){
            String nameOfDriver = getIntent().getStringExtra("name");
            String modelOfCar = getIntent().getStringExtra("model");
            String ratingOfDriver = getIntent().getStringExtra("rating");
            String statusOfDriver = getIntent().getStringExtra("status");
            String avatarOfDriver = getIntent().getStringExtra("image");

            setData(nameOfDriver, modelOfCar, ratingOfDriver, statusOfDriver, avatarOfDriver);
        }
    }

    private void setData(String nameOfDriver,
                         String modelOfCar,
                         String ratingOfDriver,
                         String statusOfDriver,
                         String avatarOfDriver) {
        name.setText(nameOfDriver);
        model.setText(modelOfCar);
        rating.setText(ratingOfDriver);
        status.setText(statusOfDriver);
        Picasso.get().load(avatarOfDriver).into(photo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(DataContainerActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
