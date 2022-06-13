package com.example.baterina_firebase_authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnLogout;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

    }

    private void logout(){
        mAuth.signOut();
    }

    private void initComponents(){
        btnLogout = (Button) findViewById(R.id.btnLogout);

        mAuth = FirebaseAuth.getInstance();

    }
}