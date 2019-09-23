package com.example.projforlabs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BasicActivity extends AppCompatActivity {

    TextView tv;
    Button btn;
    FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private long backPressedTime;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        tv = findViewById(R.id.textView3);
        btn = findViewById(R.id.logout);
        fAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        FirebaseUser fUser = fAuth.getCurrentUser();

        readData();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(BasicActivity.this, LoginActivity.class);
                startActivity(i);
                Toast.makeText(BasicActivity.this, "Logout!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void readData() {
        FirebaseUser fUser = fAuth.getCurrentUser();
        databaseReference.orderByChild("email").equalTo(fUser.getEmail()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                User user = dataSnapshot.getValue(User.class);
                tv.setText("Welcome "+ user.getUsername());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            fAuth.getInstance().signOut();
            finishAffinity();
            finish();
        }else {
            Toast.makeText(getBaseContext(), "Press again to exit",Toast.LENGTH_SHORT ).show();
        }
        backPressedTime = System.currentTimeMillis();

    }

}
