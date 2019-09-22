package com.example.projforlabs;

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

public class LoginActivity extends AppCompatActivity {
    EditText email, pass;
    Button btnIn;
    TextView tvUp;
    FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.lemail);
        pass = findViewById(R.id.lpass);
        btnIn = findViewById(R.id.button1);
        tvUp = findViewById(R.id.textView4);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = fAuth.getCurrentUser();
                if(firebaseUser != null){
                    Toast.makeText(LoginActivity.this,
                            "Login is successful!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(LoginActivity.this, BasicActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(LoginActivity.this,
                            "Please Login!", Toast.LENGTH_LONG).show();
                }
            }
        };

        btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString();
                String ps = pass.getText().toString();
                if(em.isEmpty()){
                    email.setError("Please enter email!");
                    email.requestFocus();
                }
                else if(ps.isEmpty()){
                    pass.setError("Please enter password!");
                    pass.requestFocus();
                }
                else if(ps.isEmpty() && em.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Fields are empty!",
                            Toast.LENGTH_LONG).show();
                }
                else if(!(ps.isEmpty() && em.isEmpty())){
                    SingIn(em, ps);
                }
            }
        });

        tvUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


    }

    private void SingIn(String em, String ps) {
        fAuth.signInWithEmailAndPassword(em, ps).addOnCompleteListener(LoginActivity.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this,
                                            "Email Or Password Incorrect! Please Try Again",
                                            Toast.LENGTH_LONG).show();
                                }
                                else {
                                    startActivity(new Intent(LoginActivity.this,
                                            BasicActivity.class));
                                }

                            }
                        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }
}
