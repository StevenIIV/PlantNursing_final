package com.example.lsc.weather1.entities;

import com.example.lsc.weather1.entities.HourlyWeather;

/**
 * Created by lsc19 on 2017/12/29.
 */



public class MyWeather {
    private String status;
    private HourlyWeather[] weathers ;

    public MyWeather() {
    }

    public MyWeather(String status, HourlyWeather[] weathers) {
        this.weathers = weathers;
        this.status = status;
    }

    public HourlyWeather[] getWeathers() {
        return weathers;
    }

    public void setWeathers(HourlyWeather[] weathers) {
        this.weathers = weathers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public HourlyWeather getWeathersAt(int index) {
        return weathers[index];
    }

    public void setWeathersAt(HourlyWeather weather,int index) {
        this.weathers[index] = weather;
    }

}

