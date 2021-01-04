package com.example.smartparking;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable CreateAccount context) {
        super((Context) context, "CreateAccount.db", null, 9);
    }

    public DatabaseHelper(@Nullable Login context) {
        super((Context) context, "CreateAccount.db", null, 9);
    }

    public DatabaseHelper(@Nullable CreateVehicle context) {
        super((Context) context, "CreateAccount.db", null, 9);
    }

    public DatabaseHelper(@Nullable AdminPage context) {
        super((Context) context, "CreateAccount.db", null, 9);
    }

    public DatabaseHelper(@Nullable ChooseVehicleActivity context) {
        super((Context) context, "CreateAccount.db", null, 9);
    }

    public DatabaseHelper(@Nullable Account context) {
        super((Context) context, "CreateAccount.db", null, 9);
    }

    public DatabaseHelper(@Nullable YourParkingLot context) {
        super((Context) context, "CreateAccount.db", null, 9);
    }
    public DatabaseHelper(@Nullable Timer context) {
        super((Context) context, "CreateAccount.db", null, 9);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Users(Username text primary key,Password text,FirstName text,LastName text,Email text,Phone integer,PostalAdress text)");
        db.execSQL("CREATE TABLE Vehicles(Owner text, LicensePlate text, VehicleType text,Parked text)");
        db.execSQL("CREATE TABLE ParkingLots(LotID text primary key, Floor integer , Zone text , Nr integer,VehicleType text , Free text , Time integer , ArrivingHour integer, ArrivingMinutes integer)");
        db.execSQL("CREATE TABLE Preferece(Owner text primary key,Floor integer , Zone text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 9) {
            db.execSQL("DROP TABLE IF EXISTS Users");
            db.execSQL("DROP TABLE IF EXISTS Vehicles");
            db.execSQL("DROP TABLE IF EXISTS ParkingLots");
            db.execSQL("DROP TABLE IF EXISTS Preferece");
            onCreate(db);
        }
    }

    public boolean insertUser(String Username, String Password, String FirstName, String LastName, String Email, int Phone, String PostalAdress) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Username", Username);
        cv.put("Password", Password);
        cv.put("FirstName", FirstName);
        cv.put("LastName", LastName);
        cv.put("Email", Email);
        cv.put("Phone", Phone);
        cv.put("PostalAdress", PostalAdress);
        long ins = db.insert("Users", null, cv);
        if (ins < 0) return false;
        else return true;

    }

    public boolean insertVehicle(String Owner, String LicensePlate, String VehicleType, String Parked) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Owner", Owner);
        cv.put("LicensePlate", LicensePlate);
        cv.put("VehicleType", VehicleType);
        cv.put("Parked", Parked);
        long ins = db.insert("Vehicles", null, cv);
        if (ins < 0) return false;
        else return true;

    }

    public boolean insertParking(int Floor, String Zone, int Nr, String VehicleType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String id = String.valueOf(Floor) + Zone + String.valueOf(Nr);
        cv.put("LotID", id);
        cv.put("Floor", Floor);
        cv.put("Zone", Zone);
        cv.put("Nr", Nr);
        cv.put("Vehicletype", VehicleType);
        cv.put("Free", "yes");
        long ins = db.insert("ParkingLots", null, cv);
        if (ins < 0) return false;
        else return true;

    }

    public Boolean kmail(String Email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Users where Email=?", new String[]{Email});
        if (cursor.getCount() > 0) return false;
        else return true;
    }

    public Boolean kuser(String UserName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Users where UserName=?", new String[]{UserName});
        if (cursor.getCount() > 0) return false;
        else return true;
    }

    public Boolean autentificare(String UserName, String Password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Users where UserName=?", new String[]{UserName});
        if (cursor.getCount() > 0) {
            Cursor cursor1 = db.rawQuery("Select * from Users where UserName=?", new String[]{UserName});
            cursor1.moveToFirst();
            if (cursor1.getString(cursor1.getColumnIndex("Password")).equals(Password)) {

                return true;
            } else {
                return false;
            }
        } else return false;
    }

    public String isParked(String UserName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Vehicles where Owner=? and Parked!='no'", new String[]{UserName});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex("Parked"));
        } else {
            return "no";
        }
    }

    public String isParkedgetType(String LotID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Vehicles where Parked=?", new String[]{LotID});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex("VehicleType"));
        } else {
            return "no";
        }
    }

    public String isParkedLicense(String LotID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Vehicles where Parked=?", new String[]{LotID});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex("Parked"));
        } else {
            return "no";
        }
    }

    public String[] VehiclesOwner(String Owner) {
        String[] sir = new String[10];
        String[] error = {"No car"};
        String temp;
        int i = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select LicensePlate,VehicleType from Vehicles where Owner=?", new String[]{Owner});
        if (cursor.getCount() <= 0) {
            return error;
        } else {
            try {
                while (cursor.moveToNext()) {
                    temp = cursor.getString(cursor.getColumnIndex("LicensePlate")) + " " + cursor.getString(cursor.getColumnIndex("VehicleType"));
                    sir[i] = temp;
                    i++;
                }
                String[] sirfinal = new String[i];
                for (int j = 0; j < i; j++) {
                    sirfinal[j] = sir[j];
                }
                return sirfinal;
            } finally {
                cursor.close();
            }
        }
    }

    public String getFirstName(String User) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Users where UserName=?", new String[]{User});
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("FirstName"));
    }

    public String getLastName(String User) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select LastName from Users where UserName=?", new String[]{User});
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("LastName"));
    }

    public String getEmail(String User) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select Email from Users where UserName=?", new String[]{User});
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("Email"));
    }

    public String getPhone(String User) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select Phone from Users where UserName=?", new String[]{User});
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("Phone"));
    }

    public String getPostalAdress(String User) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select PostalAdress from Users where UserName=?", new String[]{User});
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("PostalAdress"));
    }

    public String getFloor(String LotID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from ParkingLots where LotID=?", new String[]{LotID});
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("Floor"));
    }

    public String getZone(String LotID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from ParkingLots where LotID=?", new String[]{LotID});
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("Zone"));
    }

    public String getNumber(String LotID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from ParkingLots where LotID=?", new String[]{LotID});
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("Nr"));
    }

    public boolean insertPreferece(String Owner, int Floor, String Zone) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Preferece", "Owner=?", new String[]{Owner});
        ContentValues cv = new ContentValues();
        cv.put("Owner", Owner);
        cv.put("Floor", Floor);
        cv.put("Zone", Zone);
        long ins = db.insert("Preferece", null, cv);
        if (ins < 0) return false;
        else return true;
    }

    public int VehicleCount(String User) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Vehicles where Owner=?", new String[]{User});
        return cursor.getCount();
    }

    public String getLicensePlate(String Owner, int nr) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select LicensePlate from Vehicles where Owner=?", new String[]{Owner});
        cursor.move(nr + 1);
        return cursor.getString(cursor.getColumnIndex("LicensePlate"));
    }

    public String getType(String Owner, int nr) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select VehicleType from Vehicles where Owner=?", new String[]{Owner});
        cursor.move(nr + 1);
        return cursor.getString(cursor.getColumnIndex("VehicleType"));
    }

    public String getTime(String LotID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from ParkingLots where LotID=?", new String[]{LotID});
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("Time"));
    }

    public String getArrivingHour(String LotID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from ParkingLots where LotID=?", new String[]{LotID});
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("ArrivingHour"));
    }

    public String getArrivingMinutes(String LotID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from ParkingLots where LotID=?", new String[]{LotID});
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("ArrivingMinutes"));
    }

    public String getParked(String Owner, int nr) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select Parked from Vehicles where Owner=?", new String[]{Owner});
        cursor.move(nr + 1);
        return cursor.getString(cursor.getColumnIndex("Parked"));
    }

    public String[] FloorStrings() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select max(Floor) from ParkingLots", null);
        cursor.moveToNext();
        int nr = cursor.getInt(cursor.getColumnIndex("max(Floor)"));
        String[] sir = new String[nr + 1];
        for (int i = 0; i <= nr; i++) {
            sir[i] = String.valueOf(i);
        }
        return sir;
    }

    public String[] ZoneString() {
        String[] sir = new String[100];
        String[] error = {"No Zone"};
        String temp;
        int i = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select DISTINCT Zone from ParkingLots", null);
        if (cursor.getCount() <= 0) {
            return error;
        } else {
            try {
                while (cursor.moveToNext()) {
                    temp = cursor.getString(cursor.getColumnIndex("Zone"));
                    sir[i] = temp;
                    i++;
                }
                String[] sirfinal = new String[i];
                for (int j = 0; j < i; j++) {
                    sirfinal[j] = sir[j];
                }
                return sirfinal;
            } finally {
                cursor.close();
            }
        }
    }

    public String GetParkedByPreference(String User,String Type) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor preference = db.rawQuery("Select * from Preferece where Owner =?", new String[]{User});
        if (preference.getCount() > 0) {
            preference.moveToFirst();
            String Floor = preference.getString(preference.getColumnIndex("Floor"));
            String Zone = preference.getString(preference.getColumnIndex("Zone"));
            Cursor parking = db.rawQuery("Select * from ParkingLots where Floor =? and Zone=? and VehicleType=? and Free=?", new String[]{Floor, Zone,Type,"yes"});
            if (parking.getCount() > 0) {
                    parking.moveToFirst();
                    return parking.getString(parking.getColumnIndex("LotID"));
            }else return "We could not find a parking lot according to your preferences.";
        } else return "No Preferences";
    }

    public String GetParkedCloseToPreferences(String User,String Type) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor preference = db.rawQuery("Select * from Preferece where Owner =?", new String[]{User});
        preference.moveToFirst();
        String Floor = preference.getString(preference.getColumnIndex("Floor"));
        Cursor parking = db.rawQuery("Select * from ParkingLots where Floor =? and VehicleType=? and Free=? ", new String[]{Floor,Type,"yes"});
        if(parking.getCount()>0) {
            parking.moveToFirst();
            return parking.getString(parking.getColumnIndex("LotID"));
        }
        String Down = String.valueOf(Integer.parseInt(Floor) - 1);
        Cursor parking2 = db.rawQuery("Select * from ParkingLots where Floor =? and VehicleType=? and Free=? ", new String[]{Down,Type,"yes"});
        if(parking2.getCount()>0) {
            parking2.moveToFirst();
            return parking2.getString(parking2.getColumnIndex("LotID"));
        }
        String Up = String.valueOf(Integer.parseInt(Floor) + 1);
        Cursor parking3 = db.rawQuery("Select * from ParkingLots where Floor =? and VehicleType=? and Free=? ", new String[]{Up,Type,"yes"});
        if(parking3.getCount()>0) {
            parking3.moveToFirst();
            return parking3.getString(parking3.getColumnIndex("LotID"));
        }
        return "No free lot";
    }

    public String GetParkedWithoutPreference(String Type)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor parking = db.rawQuery("Select LotID from ParkingLots where VehicleType=? and Free=?", new String[]{Type,"yes"});
        if(parking.getCount()>0)
        {
            parking.moveToFirst();
            return parking.getString(parking.getColumnIndex("LotID"));
        }
        return "No free lot";
    }

    public void Park(String LicencePlate,String Type,String LotID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Calendar calendar = Calendar.getInstance();
        int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        db.execSQL("UPDATE ParkingLots SET Free=? WHERE LotID=?",new String[]{LicencePlate,LotID});
        db.execSQL("UPDATE ParkingLots SET Time =? WHERE LotID=?",new String[]{"60",LotID});
        db.execSQL("UPDATE ParkingLots SET ArrivingHour =? WHERE LotID=?",new String[]{String.valueOf(hour24hrs),LotID});
        db.execSQL("UPDATE ParkingLots SET ArrivingMinutes =? WHERE LotID=?",new String[]{String.valueOf(minutes),LotID});
        db.execSQL("UPDATE Vehicles SET Parked=? WHERE LicensePlate=? and VehicleType=?",new String[] {LotID,LicencePlate,Type});
    }

    public void FreeParking(String LicencePlate,String Type,String LotID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE ParkingLots SET Free=? WHERE LotID=?",new String[]{"yes",LotID});
        db.execSQL("UPDATE ParkingLots SET Time =? WHERE LotID=?",new String[]{"0",LotID});
        db.execSQL("UPDATE ParkingLots SET ArrivingHour =? WHERE LotID=?",new String[]{"0",LotID});
        db.execSQL("UPDATE ParkingLots SET ArrivingMinutes =? WHERE LotID=?",new String[]{"0",LotID});
        db.execSQL("UPDATE Vehicles SET Parked=? WHERE Parked=?",new String[] {"no",LotID});
    }

    public void ADDTime(String LotID,int Minutes)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor Lot = db.rawQuery("select * from ParkingLots where LotID=? ",new String[] {LotID});
        Lot.moveToFirst();
        int CurrentTime = Integer.parseInt(Lot.getString(Lot.getColumnIndex("Time")));
        String newTime= String.valueOf(CurrentTime + Minutes);
        db.execSQL("UPDATE ParkingLots SET Time =? WHERE LotID=?",new String[]{newTime,LotID});
    }

    public void ADDTimeFromZero(String LotID,int Minutes)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Calendar calendar = Calendar.getInstance();
        int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        db.execSQL("UPDATE ParkingLots SET Time =? WHERE LotID=?",new String[]{String.valueOf(Minutes),LotID});
        db.execSQL("UPDATE ParkingLots SET ArrivingHour =? WHERE LotID=?",new String[]{String.valueOf(hour24hrs),LotID});
        db.execSQL("UPDATE ParkingLots SET ArrivingMinutes =? WHERE LotID=?",new String[]{String.valueOf(minutes),LotID});

    }

    public void ClearParking()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("update ParkingLots set Free='yes',Time=0,ArrivingHour=0,ArrivingMinutes=0");
    }
}
