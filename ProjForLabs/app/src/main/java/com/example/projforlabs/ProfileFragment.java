package com.example.projforlabs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private FirebaseAuth auth;
    private TextInputLayout nameLayout;
    private TextInputEditText nameInput;
    private TextInputLayout emailLayout;
    private TextInputEditText emailInput;
    private Button logout;
    private Button nameRefresh;
    private Button emailRefresh;
    private Button photoUpload;
    private TextView name;
    private TextView email;
    private ImageView avatar;
    private FirebaseUser user;
    private StorageReference Folder;
    private StorageReference ImageName;
    private final int codeStatus = 1;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();

        View root = inflater.inflate(R.layout.fragment_profile,
                container, false);
        nameLayout = root.findViewById(R.id.name_layout);
        nameInput = root.findViewById(R.id.new_name);
        emailLayout = root.findViewById(R.id.email_layout);
        emailInput = root.findViewById(R.id.new_email);
        name = root.findViewById(R.id.current_name);
        email = root.findViewById(R.id.current_email);
        logout = root.findViewById(R.id.logout_btn);
        nameRefresh = root.findViewById(R.id.refresh_name);
        emailRefresh = root.findViewById(R.id.refresh_email);
        photoUpload = root.findViewById(R.id.upload_avatar);
        avatar = root.findViewById(R.id.user_avatar);
        Folder = FirebaseStorage
                .getInstance()
                .getReference()
                .child("ImageFolder");

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        user = auth.getCurrentUser();
        if (user != null) {
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());
            ImageName = Folder.child(user.getUid());
            ImageName.getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri.toString()).into(avatar);
                        }
                    });
        } else {
            Toast.makeText(getActivity(), getString(R.string.somethingGoneWrong),
                    Toast.LENGTH_SHORT).show();
        }

        updateName();
        updateEmail();
        uploadAvatar();
        exit();
    }

    private void exit() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(getActivity(),
                        LoginActivity.class);
                startActivity(intent);
                Objects.requireNonNull(getActivity())
                        .overridePendingTransition(0, 0);
            }
        });
    }

    private void uploadAvatar() {
        photoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadProfilePicture();
            }
        });
    }

    private void updateEmail() {
        emailRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEmail = Objects
                        .requireNonNull(emailInput.getText())
                        .toString()
                        .trim();
                if (checkEmail(newEmail)) {
                    updateEmailData(user, newEmail);
                }
            }
        });
    }

    private void updateName() {
        nameRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUsername = Objects
                        .requireNonNull(nameInput.getText())
                        .toString()
                        .trim();
                if (checkUsername(newUsername)) {
                    name.setText(newUsername);
                    updateUsernameData(user, newUsername);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == codeStatus) {
            if (resultCode == RESULT_OK) {
                Uri ImageData = Objects.requireNonNull(data).getData();
                ImageName.putFile(Objects.requireNonNull(ImageData))
                        .addOnSuccessListener(new OnSuccessListener<UploadTask
                                .TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getActivity(), getString(R.string.image_uploaded),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                ImageName.getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get()
                                        .load(uri.toString())
                                        .into(avatar);
                            }
                        });
            }
        }
    }


    private void uploadProfilePicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, codeStatus);
    }

    private void updateUsernameData(final FirebaseUser user, final String newName) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(),
                                    getString(R.string.username_updated),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

    private void updateEmailData(FirebaseUser user, final String newEmail) {
        user.updateEmail(newEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getActivity(),
                        getString(R.string.email_updated),
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private boolean checkUsername(final String username) {
        if (username.isEmpty()) {
            nameLayout.setError(getString(R.string.enter_username));
            nameLayout.requestFocus();
            return false;
        } else {
            nameLayout.setError(null);
            return true;
        }
    }

    private boolean checkEmail(final String email) {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError(getString(R.string.invalidEmail));
            emailLayout.requestFocus();
            return false;
        } else {
            emailLayout.setError(null);
            return true;
        }
    }
}