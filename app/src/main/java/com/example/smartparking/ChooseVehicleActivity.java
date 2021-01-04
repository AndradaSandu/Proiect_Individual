package com.example.smartparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ChooseVehicleActivity extends AppCompatActivity {
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db=new DatabaseHelper(this);
        final String Owner  = getIntent().getStringExtra("Owner");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_vehicle2);

        TextView myaccount= (TextView) findViewById(R.id.MyAccount);
        myaccount.setText(Owner);



        Button Btn = (Button)findViewById(R.id.AccountBtn2);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AccountIntent = new Intent(getApplicationContext(),Account.class);
                AccountIntent.putExtra("Owner",Owner);
                startActivity(AccountIntent);
            }
        });

        Button AddBtn = (Button)findViewById(R.id.AddVehicleBtn);
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CreateVehicleIntent = new Intent(getApplicationContext(),CreateVehicle.class);
                CreateVehicleIntent.putExtra("Owner",Owner);
                startActivity(CreateVehicleIntent );
            }
        });

        final Spinner ChooseVehicle = (Spinner)findViewById(R.id.ChooseVehicleSpinner);
        String[] arraySpinner = db.VehiclesOwner(Owner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ChooseVehicle.setAdapter(adapter);

        Button GoToBtn = (Button)findViewById(R.id.GoToBtn);
        GoToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToIntent = new Intent(getApplicationContext(),YourParkingLot.class);
                GoToIntent.putExtra("LicencesPlate",db.getLicensePlate(Owner,ChooseVehicle.getSelectedItemPosition()));
                GoToIntent.putExtra("VehicleType",db.getType(Owner,ChooseVehicle.getSelectedItemPosition()));
                GoToIntent.putExtra("Owner",Owner);
                startActivity(GoToIntent);
            }
        });


    }

}
