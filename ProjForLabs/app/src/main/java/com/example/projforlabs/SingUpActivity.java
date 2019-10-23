package com.example.projforlabs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

public class SingUpActivity extends AppCompatActivity {

    private EditText name;
    private EditText phone;
    private EditText email;
    private EditText pass;
    private Button btn;
    private TextView tv;
    private FirebaseAuth fAuth;

    static final String EM_PATT = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    static final String PS_PATT = ".{8,}";
    static final String PN_PATT = "[+]380[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        btn = findViewById(R.id.singUpButton);
        tv = findViewById(R.id.swapToLoginPage);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonToSingUp();
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SingUpActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

    }


    private void addUserName(String nm) {
        FirebaseUser user = fAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(nm).build();
        if (user != null) {
            user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(SingUpActivity.this,
                                RvActivity.class));
                    }
                }
            });
        }
    }


    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }

    private void createUser(String em, String ps, final String nm) {
        fAuth.createUserWithEmailAndPassword(em, ps)
                .addOnCompleteListener(SingUpActivity.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SingUpActivity.this,
                                            R.string.emailAlreeadyExist,
                                            LENGTH_LONG).show();
                                } else {
                                    addUserName(nm);
                                }
                            }
                        });
    }

    private void buttonToSingUp() {
        final String nm = name.getText().toString();
        final String pn = phone.getText().toString();
        final String em = email.getText().toString();
        final String ps = pass.getText().toString();

        if (nm.isEmpty()) {
            name.setError("Please enter name!");
            name.requestFocus();
        } else if (pn.isEmpty()) {
            phone.setError("Please enter phone!");
            phone.requestFocus();
        } else if (em.isEmpty()) {
            email.setError("Please enter your email!");
            email.requestFocus();
        } else if (ps.isEmpty()) {
            pass.setError("Please enter password!");
            pass.requestFocus();
        } else if (!pn.matches(PN_PATT)) {
            Toast.makeText(SingUpActivity.this, R.string.correctPhone,
                    LENGTH_SHORT).show();
        } else if (!em.matches(EM_PATT)) {
            Toast.makeText(SingUpActivity.this, R.string.invalidEmail,
                    LENGTH_SHORT).show();
        } else if (!ps.matches(PS_PATT)) {
            Toast.makeText(SingUpActivity.this, R.string.correctPassword,
                    LENGTH_SHORT).show();
        } else if (!(em.isEmpty() && ps.isEmpty())) {
            createUser(em, ps, nm);
        } else {
            Toast.makeText(SingUpActivity.this, R.string.somethingGoneWrong,
                    LENGTH_SHORT).show();
        }
    }

}