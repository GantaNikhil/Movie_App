package com.example.movie_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {
    private TextInputLayout emailEt, passwordEt;
    private Button SignInButton;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private TextView SignUpOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        firebaseAuth = FirebaseAuth.getInstance();

        emailEt = findViewById(R.id.email);
        passwordEt = findViewById(R.id.password);
        SignInButton = findViewById(R.id.logIn);
        SignUpOne = findViewById(R.id.signUp1);

        progressDialog = new ProgressDialog(this);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        SignUpOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void login() {
        String email = Objects.requireNonNull(emailEt.getEditText()).getText().toString();
        String password = Objects.requireNonNull(passwordEt.getEditText()).getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEt.setError("Enter your Mail");
            return;
        } else if (TextUtils.isEmpty(password)) {
            passwordEt.setError("Enter your Password");
            return;
        }

        progressDialog.setMessage("Please wait.....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignInActivity.this, "Successfully Signed In", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignInActivity.this, DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignInActivity.this, "Login Failed!", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}