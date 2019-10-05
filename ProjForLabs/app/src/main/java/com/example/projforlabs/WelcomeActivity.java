package com.example.projforlabs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {

    private TextView tv;
    private Button btn;
    static int time;
    private FirebaseAuth fAuth;
    private long backPressedTime;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tv = findViewById(R.id.welcome);
        btn = findViewById(R.id.logout);
        fAuth = FirebaseAuth.getInstance();

        FirebaseUser fUser = fAuth.getCurrentUser();

        if (fUser != null) {
            final String uName = fUser.getDisplayName();
            if (uName != null) {
                tv.setText(R.string.welcome + uName);
            }
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(i);
                Toast.makeText(WelcomeActivity.this, R.string.logout, Toast.LENGTH_LONG).show();
            }
        });
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
