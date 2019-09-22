package com.example.projforlabs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BasicActivity extends AppCompatActivity {

    TextView tv;
    Button btn;
    FirebaseAuth fAuth;
    String userID;
    FirebaseUser fUser;
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
        databaseReference= firebaseDatabase.getReference("Users");
        fUser = fAuth.getCurrentUser();
        userID = fUser.getUid();


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

    public void readData(){
        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        String dUser = ds.child("username").getValue(String.class);
                        tv.setText("Welcome "+ dUser + "!");
                    }
                }
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
