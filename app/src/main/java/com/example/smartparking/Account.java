package com.example.smartparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Account extends AppCompatActivity {
    DatabaseHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        final String Owner  = getIntent().getStringExtra("Owner");
        db=new DatabaseHelper(this);

        TextView firstnameid=(TextView) findViewById(R.id.FirstNameId);
        TextView lastnameId=(TextView) findViewById(R.id.LastNameId);
        TextView emailid=(TextView) findViewById(R.id.EmailId);
        TextView phoneeid=(TextView) findViewById(R.id.PhoneId);
        TextView postaladressid=(TextView) findViewById(R.id.PostaAdressId);
        final TextView licenseplateid=(TextView) findViewById(R.id.LicensePlateId);
        final TextView vehicletype=(TextView) findViewById(R.id.VehicleTypeId);
        final Spinner vehicle = (Spinner)findViewById(R.id.spinnerid);
        final CheckBox Parked = (CheckBox)findViewById(R.id.ParkedID);
        final Spinner Floor = (Spinner)findViewById(R.id.FloorSpinnerId);
        final Spinner Zone=(Spinner)findViewById(R.id.ZoneSpinnerId);


        firstnameid.setText(db.getFirstName(Owner));
        lastnameId.setText(db.getLastName(Owner));
        emailid.setText(db.getEmail(Owner));
        phoneeid.setText(db.getPhone(Owner));
        postaladressid.setText(db.getPostalAdress(Owner));

        int nr = db.VehicleCount(Owner);
        String[] sir = new String[nr];
        String temp;
        for(int i=0;i<nr;i++)
        {
            temp=Integer.toString(i+1);
            sir[i]=temp;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sir);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicle.setAdapter(adapter);

        vehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int VehicleNr = vehicle.getSelectedItemPosition();
                licenseplateid.setText(db.getLicensePlate(Owner,VehicleNr));
                vehicletype.setText(db.getType(Owner,VehicleNr));

                String Park = db.getParked(Owner,VehicleNr);
                if(Park.equals("no"))
                {
                    Parked.setChecked(false);
                }else Parked.setChecked(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });

        String[] arrayFloor = db.FloorStrings();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayFloor);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Floor.setAdapter(adapter2);

        String[] arrayZone = db.ZoneString();
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayZone);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Zone.setAdapter(adapter3);

        final TextView PreferenceAdded = (TextView)findViewById(R.id.PreferenceSaveID) ;
        Button AddPreferenceBtn = (Button)findViewById(R.id.SaveyourpreferencesBtn);
        AddPreferenceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.insertPreferece(Owner,Floor.getSelectedItemPosition(),Zone.getSelectedItem().toString());
                PreferenceAdded.setText("Your preferences have been saved");
            }
        });

        Button AddVehicleBtn = (Button)findViewById(R.id.AddVehicleBtn2);
        AddVehicleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CreateVehicleIntent = new Intent(getApplicationContext(),CreateVehicle.class);
                CreateVehicleIntent.putExtra("Owner",Owner);
                startActivity(CreateVehicleIntent );
            }
        });
    }
}
