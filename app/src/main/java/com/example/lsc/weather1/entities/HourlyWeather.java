package com.example.lsc.weather1.entities;

/**
 * Created by lsc19 on 2018/3/28.
 */

public class HourlyWeather{
    public String text;
    public String code;
    public String temperature;
    public String time;

    public HourlyWeather(String text, String code, String temperature, String time) {
        this.text = text;
        this.code = code;
        this.temperature = temperature;
        this.time = time;
    }

}
