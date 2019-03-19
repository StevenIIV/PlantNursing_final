package com.example.lsc.weather1.apps;

import android.app.Application;

import com.example.lsc.weather1.entities.MyAddress;
import com.example.lsc.weather1.entities.MyWeather;


/**
 * Created by lsc19 on 2018/1/25.
 */

public class MyApp extends Application {
    public MyWeather myWeather;
    public MyAddress myAddress;
    public String tmp;
}
