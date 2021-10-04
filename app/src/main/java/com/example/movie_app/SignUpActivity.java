package com.example.movie_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private TextInputLayout emailEt, passwordEt1, passwordEt2;
    private Button SignUpButton;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        firebaseAuth = FirebaseAuth.getInstance();
        emailEt = findViewById(R.id.email);
        passwordEt1 = findViewById(R.id.password1);
        passwordEt2 = findViewById(R.id.password2);
        SignUpButton = findViewById(R.id.register);
        progressDialog = new ProgressDialog(this);
        SignUpButton.setOnClickListener(v -> register());

    }

    private void register() {
        String email = Objects.requireNonNull(emailEt.getEditText()).getText().toString();
        String password1 = Objects.requireNonNull(passwordEt1.getEditText()).getText().toString();
        String password2 = Objects.requireNonNull(passwordEt2.getEditText()).getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEt.setError("Enter your Mail");
            return;
        } else if (TextUtils.isEmpty(password1)) {
            passwordEt1.setError("Enter your Password");
            return;
        } else if (TextUtils.isEmpty(password2)) {
            passwordEt2.setError("Confirm the password");
            return;
        } else if (!password1.equals(password2)) {
            passwordEt2.setError("Different passwords");
            return;
        } else if (password1.length() < 4) {
            passwordEt1.setError("Password is too short");
            return;
        } else if (!isValidEmail(email)) {
            emailEt.setError("Invalid Email");
            return;
        }
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this, DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this, "Registration Failed!", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}
