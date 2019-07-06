package com.example.firebase_auth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    private static final int CHOOSE_IAMGE = 101;
    ImageView profile_pic;
    EditText profile_name;
    Button button_saveChanges;
    Uri uri_profileImage;
    ProgressBar progressBar;
    String profileImageURL;
    FirebaseAuth mAuth;
    Button signOut;
    TextView verifiedtext;
    CheckBox checkDoctor;
    String role;
    Button skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profille);

        mAuth = FirebaseAuth.getInstance();
        profile_pic = findViewById(R.id.image_view);
        profile_name = findViewById(R.id.edit_text_name);
        button_saveChanges = findViewById(R.id.button_save);
        progressBar = findViewById(R.id.progressbar_pic);
        verifiedtext = findViewById(R.id.emailVerified);
        signOut = findViewById(R.id.button_logoOut);
        checkDoctor = findViewById(R.id.check_doctor);
        skip = findViewById(R.id.button_skip);

        loadUserInformation();

        button_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();
            }
        });

        checkDoctor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    role = "DOCTOR";

                }
                else {
                    role = "PAITENT";
                }
            }
        });

        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageChooser();
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutAccount();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentMainActivity();
            }
        });

    }

    private void signOutAccount() {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
    }

    private void intentMainActivity(){
        startActivity(new Intent(ProfileActivity.this,MainActivity.class));
    }

    private void loadUserInformation() {
        final FirebaseUser user = mAuth.getCurrentUser();


        if (user != null) {
            if (user.getPhotoUrl() != null) {

                String photoUrl = user.getPhotoUrl().toString();
                Glide.with(this).load(photoUrl).into(profile_pic);
            } else {
                profile_pic.setImageDrawable(getResources().getDrawable(R.drawable.ic_boy));
            }
            if (user.getDisplayName() != null) {
                String displayName = user.getDisplayName();
                profile_name.setText(displayName);
            }

            if (user.isEmailVerified()) {
                verifiedtext.setText("Verified");
            }
            else {
                verifiedtext.setText("Not Verified(click to verify)");
                verifiedtext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Send Email succefully", Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void saveUserInformation() {
        String profileName = profile_name.getText().toString();
        if (profileName.isEmpty()) {
            profile_name.setError("Enter the Name");
            profile_name.requestFocus();
        }

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null && uri_profileImage != null) {
            progressBar.setVisibility(View.GONE);
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setDisplayName(profileName+getResources().getString(R.string.space)+role).setPhotoUri(Uri.parse(String.valueOf(uri_profileImage))).build();


            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Successfull upload every thing", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }

    }

    public void ImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose Image"), CHOOSE_IAMGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uri_profileImage = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri_profileImage);
            profile_pic.setImageBitmap(bitmap);
            uploadProfilePic();
            Toast.makeText(getApplicationContext(), "Succesfull", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Some thing Wrong", Toast.LENGTH_SHORT).show();
        }


    }

    private void uploadProfilePic() {
        progressBar.setVisibility(View.VISIBLE);
        final StorageReference profileImageRef = FirebaseStorage.getInstance().getReference("profilePics/" + System.currentTimeMillis() + ".jpg");

        if (uri_profileImage != null) {
            profileImageRef.putFile(uri_profileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);

                    profileImageURL = profileImageRef.getDownloadUrl().toString();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
