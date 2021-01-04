package com.example.smartparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AdminPage extends AppCompatActivity {

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        db=new DatabaseHelper(this);

        Button Btn = (Button)findViewById(R.id.Btn);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText floor = (EditText)findViewById(R.id.FloorID);
                EditText zone = (EditText)findViewById(R.id.ZoneID);
                EditText nr = (EditText)findViewById(R.id.NrID);
                Spinner type =(Spinner)findViewById(R.id.TypeID);

                TextView error = (TextView)findViewById(R.id.erroradmin);

                boolean test = db.insertParking(Integer.parseInt(floor.getText().toString()),zone.getText().toString(),Integer.parseInt(nr.getText().toString()),type.getSelectedItem().toString());
                if(test)
                {
                    error.setText("Lot Added");
                }else {
                    error.setText("Error");
                }
            }
        });

        Button Btn2 = (Button)findViewById(R.id.Clear);
        Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.ClearParking();
            }
        });
    }
}
