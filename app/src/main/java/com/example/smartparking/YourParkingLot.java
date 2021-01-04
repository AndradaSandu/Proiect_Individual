package com.example.smartparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class YourParkingLot extends AppCompatActivity {
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_parking_lot);

        final String Owner  = getIntent().getStringExtra("Owner");
        final String Type  = getIntent().getStringExtra("VehicleType");
        final String Licence = getIntent().getStringExtra("LicencesPlate");
        db=new DatabaseHelper(this);


        TextView floorpark=(TextView) findViewById(R.id.floorrpark);
        TextView zonepark=(TextView) findViewById(R.id.zonepark);
        TextView nrpark=(TextView) findViewById(R.id.nrpark);
        TextView errorpark=(TextView) findViewById(R.id.errorpark);

        final String Parcare;
        String temp;


        temp=db.GetParkedByPreference(Owner,Type);
        if(temp.equals("No Preferences")){
            temp = db.GetParkedWithoutPreference(Type);
        }else if(temp.equals("We could not find a parking lot according to your preferences."))
            {
                temp=db.GetParkedCloseToPreferences(Owner,Type);
                if(temp.equals("No free lot"))
                {
                    temp = db.GetParkedWithoutPreference(Type);
                }
            }

        Parcare=temp;
        Button GetParkedBtn = (Button)findViewById(R.id.GetParkedBtn);

        if(temp.equals("No free lot"))
        {
            errorpark.setText("Sorry,we don't have any free lots. Please try later");
            GetParkedBtn.setText("Try Later");
        }else{
            String bla;
            bla="Floor: " + db.getFloor(Parcare);
            floorpark.setText(bla);
            bla="Zone: " + db.getZone(Parcare);
            zonepark.setText(bla);
            bla="Nr: " + db.getNumber(Parcare);
            nrpark.setText(bla);

        }

        GetParkedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Parcare.equals("No free lot")){
                    Intent ParkingLotIntent = new Intent(getApplicationContext(),ChooseVehicleActivity.class);
                    ParkingLotIntent.putExtra("Owner",Owner);
                    startActivity(ParkingLotIntent);


                }else{
                    db.Park(Licence,Type,Parcare);
                    Intent ParkingLotIntent= new Intent(getApplicationContext(),Timer.class);
                    ParkingLotIntent.putExtra("Owner",Owner);
                    ParkingLotIntent.putExtra("LicensePlate",Licence);
                    ParkingLotIntent.putExtra("VehicleType",Type);
                    ParkingLotIntent.putExtra("LotID",Parcare);
                    startActivity(ParkingLotIntent);
                }
            }
        });





    }
}
