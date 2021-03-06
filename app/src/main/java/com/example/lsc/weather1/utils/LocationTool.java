package com.example.lsc.weather1.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;

import static android.content.Context.LOCATION_SERVICE;
import static android.location.LocationManager.GPS_PROVIDER;

/**
 * Created by lsc19 on 2018/1/2.
 */

public class LocationTool {
    LocationManager lm;
    Location location;

    public LocationTool(Context context) {
        this.lm =(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
    }
    public  boolean isGPSAble(){
        return lm.isProviderEnabled(GPS_PROVIDER);
    }
    public void openGPS(Context context){
        Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }

    @SuppressLint("MissingPermission")
    public void getLocation(){
        this.location= lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    public void close(){ }
}
