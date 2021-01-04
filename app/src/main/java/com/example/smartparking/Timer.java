package com.example.smartparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Time;
import java.util.Calendar;

public class Timer extends AppCompatActivity {
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        db=new DatabaseHelper(this);

        final String Owner  = getIntent().getStringExtra("Owner");
        final String Type  = getIntent().getStringExtra("VehicleType");
        final String Licence = getIntent().getStringExtra("LicensePlate");
        final String LotID = getIntent().getStringExtra("LotID");

        Calendar calendar = Calendar.getInstance();
        final int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
        final int minutes = calendar.get(Calendar.MINUTE);

        final TextView CountDown = (TextView)findViewById(R.id.TimerID);
        final long TimeLeft;
        if(Integer.parseInt(db.getTime(LotID))-(hour24hrs-Integer.parseInt(db.getArrivingHour(LotID)))*60+minutes-Integer.parseInt(db.getArrivingMinutes(LotID))<=0)
        {
            TimeLeft=0;
        }else TimeLeft=(Integer.parseInt(db.getTime(LotID))-((hour24hrs-Integer.parseInt(db.getArrivingHour(LotID)))*60+minutes-Integer.parseInt(db.getArrivingMinutes(LotID))))*60000;

        final EditText MoreMinutes = (EditText)findViewById(R.id.AddMinutesID);

        new CountDownTimer(TimeLeft, 1000) {

            public void onTick(long l) {
                int hours = (int)l/3600000;
                int minutes = (int)l%3600000/60000;

                String afisare;

                if(hours<10 && minutes<10) {
                    afisare = "0" + hours + ":"+"0"+minutes;
                }else if(hours<10)
                {
                    afisare = "0" + hours + ":"+minutes;
                }else if(minutes<10)
                {
                    afisare = hours + ":"+"0"+minutes;
                }else afisare = hours + ":"+minutes;

                CountDown.setText(afisare);
            }

            public void onFinish() {
                CountDown.setText("Expired");
            }
        }.start();


        Button Add = (Button)findViewById(R.id.AddBtn);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int moreMinutes = Integer.parseInt(MoreMinutes.getText().toString());
                if (TimeLeft > 0) {
                    db.ADDTime(LotID,moreMinutes);
                }else {
                    db.ADDTimeFromZero(LotID,moreMinutes);
                }
                finish();
                startActivity(getIntent());
            }
        });

        Button LeaveParking = (Button)findViewById(R.id.LeaveParkingBtn);
        LeaveParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoodBye = new Intent(getApplicationContext(),GoodBye.class);
                GoodBye.putExtra("Owner",Owner);
                db.FreeParking(Licence,Type,LotID);
                startActivity(GoodBye);
            }
        });
    }



}
