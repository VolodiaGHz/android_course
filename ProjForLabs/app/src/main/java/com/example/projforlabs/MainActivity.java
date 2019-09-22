package com.example.projforlabs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText name, phone, email, pass;
    Button btn;
    TextView tv;
    FirebaseAuth fAuth;

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private String userID;
    private FirebaseUser fUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        btn = findViewById(R.id.button2);
        tv = findViewById(R.id.textView2);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference("Users");
        userID = databaseReference.push().getKey();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nm = name.getText().toString();
                final String pn = phone.getText().toString();
                final String em = email.getText().toString();
                final String ps = pass.getText().toString();

                String emPatt = getString(R.string.emailPattern);
                String psPatt = getString(R.string.passwordPattern);
                String pnPatt = getString(R.string.phonePattern);

                if(nm.isEmpty()){
                    name.setError("Please enter name!");
                    name.requestFocus();
                }else if(pn.isEmpty()){
                    phone.setError("Please enter phone!");
                    phone.requestFocus();
                }else if (em.isEmpty()){
                    email.setError("Please enter your email!");
                    email.requestFocus();
                }else if(ps.isEmpty()){
                    pass.setError("Please enter password!");
                    pass.requestFocus();
                }else if(!pn.matches(pnPatt)) {
                    Toast.makeText(MainActivity.this, "Phone must be 13 symbols, and starts with +380",
                            Toast.LENGTH_LONG).show();
                }else if(!em.matches(emPatt)) {
                    Toast.makeText(MainActivity.this, "Invalid email!",
                            Toast.LENGTH_SHORT).show();
                }else if(!ps.matches(psPatt)){
                    Toast.makeText(MainActivity.this, "Password must be 8+ symbols!",
                            Toast.LENGTH_SHORT).show();
                } else if(!(em.isEmpty() && ps.isEmpty())){
                    addUser(nm, pn, em, ps);
//                    createUser(em,ps);
                }else {
                    Toast.makeText(MainActivity.this, "Something gone wrong!",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }

    public void addUser(String nm, final String pn, final String em, final String ps){
        final User user = new User(nm, pn);
        databaseReference.child("Users").child(userID).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    createUser(em, ps);
                }else{
                    Toast.makeText(MainActivity.this, "Something gone wrong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }

    private void createUser(String em, String ps) {
        fAuth.createUserWithEmailAndPassword(em, ps)
                .addOnCompleteListener(MainActivity.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this,
                                            "Sorry but such email already used!",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    startActivity(new Intent(MainActivity.this,
                                            BasicActivity.class));
                                }
                            }
                        });
    }

}