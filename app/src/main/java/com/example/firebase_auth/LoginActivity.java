package com.example.firebase_auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.firebase_auth.RegisterActivity.isEmailValid;

public class LoginActivity extends AppCompatActivity {

    TextView registerText;
    EditText editText_email, editText_password;
    Button button_signIn;
    FirebaseAuth mAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_email = findViewById(R.id.text_email);
        editText_password = findViewById(R.id.edit_text_password);
        registerText = findViewById(R.id.text_view_register);
        button_signIn = findViewById(R.id.button_sign_in);
        progressBar = findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();


        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        button_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                userLogin();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void userLogin() {


        String email = editText_email.getText().toString().trim();
        String password = editText_password.getText().toString().trim();

        if (email.isEmpty()) {
            editText_email.setError("Email should be provided");
            editText_email.requestFocus();
        }

        if (!isEmailValid(email)) {
            editText_email.setError("Correct email should be provided");
            editText_email.requestFocus();
        }

        if (password.isEmpty()) {
            editText_password.setError("Password should be provided");
            editText_password.requestFocus();
        }

        if (password.length() < 6) {
            editText_password.setError("Minimum lenght of password is 6");
            editText_password.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Sucessfully Login", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
