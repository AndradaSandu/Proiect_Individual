package com.example.smartparking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CreateVehicle<adapter> extends AppCompatActivity {
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vehicle);

        db=new DatabaseHelper(this);

        final RadioGroup Vehicle = (RadioGroup) findViewById(R.id.VehicleID);
        final Spinner CarType = (Spinner) findViewById(R.id.CarTypeID);
        CarType.setVisibility(View.INVISIBLE);

        Vehicle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged (RadioGroup group, int checkedId){
                int selectedID = group.getCheckedRadioButtonId();
                RadioButton VehicleChoosed = (RadioButton) findViewById(selectedID);
                if(VehicleChoosed.getText().toString().equals("Car")){
                    CarType.setVisibility(View.VISIBLE);
                }else CarType.setVisibility(View.INVISIBLE);
            }
        });

        Button CreateVehicleBtn;
        CreateVehicleBtn = (Button) findViewById(R.id.CreateVehicleBtn);
        CreateVehicleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText LicensePlates = (EditText) findViewById(R.id.LicensePlates);
                int selectedID = Vehicle.getCheckedRadioButtonId();
                RadioButton VehicleChoosed = (RadioButton) findViewById(selectedID);


                String Owner  = getIntent().getStringExtra("Owner");

                TextView error=(TextView) findViewById(R.id.ErrorCreateAccount);

                if(LicensePlates.getText().toString().equals("") || VehicleChoosed == null){
                    error.setText("All fields are obligatory");
                }else {

                    String licenseplates=LicensePlates.getText().toString();
                    String CarTypeString;
                    if(!VehicleChoosed.getText().equals("Car")){
                        CarTypeString ="Motocycle";
                    }else {
                        CarTypeString= CarType.getSelectedItem().toString();
                    }
                    db.insertVehicle(Owner,licenseplates,CarTypeString,"no");
                    Intent ChooseVehicleIntent = new Intent(getApplicationContext(),ChooseVehicleActivity.class);
                    ChooseVehicleIntent.putExtra("Owner",Owner);
                    startActivity(ChooseVehicleIntent );

                }










            }
        });
    }
}
