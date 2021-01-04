package com.example.smartparking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db=new DatabaseHelper(this);

        Button Forgot = (Button)findViewById(R.id.ForgotPasswordID);
        Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ForgotIntent = new Intent(getApplicationContext(),ForgotPassword.class);
                startActivity(ForgotIntent);
            }
        });

        Button LoginBtn = (Button)findViewById(R.id.LoginBtn);
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText UserName = (EditText) findViewById(R.id.UserNameID);
                EditText Password = (EditText) findViewById(R.id.PasswordID);

                TextView error = (TextView)findViewById(R.id.ErrorLogin);

                if(UserName.getText().toString().equals("") || Password.getText().toString().equals("")){
                    error.setText("All the field must be complet");

                }else
                {
                    if(UserName.getText().toString().equals("Admin") && Password.getText().toString().equals("Admin")) {
                        Intent Admin = new Intent(getApplicationContext(), AdminPage.class);
                        startActivity(Admin);
                    }else {
                        String Username = UserName.getText().toString();
                        String password = Password.getText().toString();
                        if (db.autentificare(Username, password)) {
                            if(!db.isParked(Username).equals("no"))
                            {
                                String Lot = db.isParked(Username);
                                Intent ChooseVehicleIntent = new Intent(getApplicationContext(), Timer.class);
                                ChooseVehicleIntent.putExtra("Owner",Username);
                                ChooseVehicleIntent.putExtra("LicencesPlate",db.isParkedLicense(Lot));
                                ChooseVehicleIntent.putExtra("VehicleType",db.isParkedgetType(Lot));
                                ChooseVehicleIntent.putExtra("LotID",Lot);
                                startActivity(ChooseVehicleIntent);

                            }else {
                                Intent ChooseVehicleIntent = new Intent(getApplicationContext(), ChooseVehicleActivity.class);
                                ChooseVehicleIntent.putExtra("Owner",Username);
                                startActivity(ChooseVehicleIntent);
                            }
                        } else {
                            error.setText("Invalid user or password");

                        }
                    }
                }



            }
        });

    }
}


