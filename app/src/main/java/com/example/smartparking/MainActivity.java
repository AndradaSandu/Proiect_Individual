package com.example.smartparking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button AutentificareBtn = (Button)findViewById(R.id.AutentificareBtn);
        AutentificareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(getApplicationContext(),Login.class);
                startActivity(LoginIntent);
            }
        });

        Button CreateAccountBtn = (Button)findViewById(R.id.CreateAccountBtn);
        CreateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CreateAccountIntent = new Intent(getApplicationContext(),CreateAccount.class);
                startActivity(CreateAccountIntent);
            }
        });
    }
}
