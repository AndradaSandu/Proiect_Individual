package com.example.smartparking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAccount extends AppCompatActivity {

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        db=new DatabaseHelper(this);



        Button ContinueBtn = (Button)findViewById(R.id.ContinueBtn);
        ContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText FirstName = (EditText) findViewById(R.id.FirstName);
                EditText LastName = (EditText) findViewById(R.id.LastName);
                EditText UserName= (EditText) findViewById(R.id.UserName);
                EditText Password=(EditText) findViewById(R.id.Password);
                EditText Confirm=(EditText) findViewById(R.id.ConfirmID);
                EditText Email = (EditText) findViewById(R.id.Email);
                EditText Phone = (EditText) findViewById(R.id.Phone);
                EditText PostalAdress= (EditText) findViewById(R.id.PostalAdress);


                TextView error = (TextView)findViewById(R.id.ErrorCreateAccount);

                 if(FirstName.getText().toString().equals("") || LastName.getText().toString().equals("") || UserName.getText().toString().equals("") || Email.getText().toString().equals("") || Phone.getText().toString().equals("") || PostalAdress.getText().toString().equals(""))
                 {
                     error.setText("All fields must be complet");

                 }else if(!db.kmail(Email.getText().toString())){
                                error.setText("The email already used");

                            }else if(!db.kuser(UserName.getText().toString()))
                                 {
                                     error.setText("The name already used");
                                 }else
                                     {
                                         if(Password.getText().toString().equals(Confirm.getText().toString())) {
                                             String firstname = FirstName.getText().toString();
                                             String lastname = LastName.getText().toString();
                                             String username = UserName.getText().toString();
                                             String email = Email.getText().toString();
                                             String password = Password.getText().toString();
                                             int phone = Integer.parseInt(Phone.getText().toString());
                                             String postaladress = PostalAdress.getText().toString();
                                             boolean insert = db.insertUser(username, password, firstname, lastname, email, phone, postaladress);

                                             Intent CreateVehicleIntent = new Intent(getApplicationContext(), CreateVehicle.class);
                                             CreateVehicleIntent.putExtra("Owner", username);
                                             startActivity(CreateVehicleIntent);
                                         }else error.setText("Password Confirmation don't match with Password");
                                     }

                }



        });
    }

}
