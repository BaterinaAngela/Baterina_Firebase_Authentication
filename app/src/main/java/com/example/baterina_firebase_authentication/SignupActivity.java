package com.example.baterina_firebase_authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Locale;

public class SignupActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editPassword;
    private EditText editCPassword;
    private Button btnRegister;

    private FirebaseAuth mAuth;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initComponents();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    private void initComponents(){
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editCPassword = (EditText) findViewById(R.id.editCPassword);
        btnRegister = (Button) findViewById(R.id.buttonRegister);

        mAuth = FirebaseAuth.getInstance();

        mProgress = mProgress = new ProgressDialog(this);
    }

    private void registerUser() {
        String email = editEmail.getText().toString().trim().toLowerCase();
        String password = editPassword.getText().toString();
        String cpassword = editCPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            editEmail.setError("Required field!");
            mProgress.dismiss();
            return;
        }
        if (!email.contains("@")) {
            editEmail.setError("Invalid email!");
            mProgress.dismiss();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editPassword.setError("Required field!");
            mProgress.dismiss();
            return;
        }
        if (TextUtils.isEmpty(cpassword)) {
            editCPassword.setError("Required field!");
            mProgress.dismiss();
            return;
        }
        if (!password.equals(cpassword)) {
            editCPassword.setError("Password should be equal to confirm password!");
            mProgress.dismiss();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    mProgress.dismiss();
                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Successful Registration", Toast.LENGTH_LONG).show();

                    FirebaseUser user = mAuth.getCurrentUser();

                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                            else{
                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            }

                        }
                    });
                }
            }
        });
    }
}
