package com.example.smartparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GoodBye extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_bye);

        final String Owner  = getIntent().getStringExtra("Owner");

        Button ParkAgain = (Button)findViewById(R.id.ParkAgainId);
        ParkAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ParkAgainIntent = new Intent(getApplicationContext(),ChooseVehicleActivity.class);
                ParkAgainIntent.putExtra("Owner",Owner);
                startActivity(ParkAgainIntent);
            }
        });
    }
}
