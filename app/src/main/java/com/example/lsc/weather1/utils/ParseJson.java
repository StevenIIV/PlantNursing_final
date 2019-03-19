package com.example.lsc.weather1.utils;

import com.example.lsc.weather1.entities.HourlyWeather;
import com.example.lsc.weather1.entities.MyWeather;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by lsc19 on 2017/12/29.
 */

public class ParseJson {

    public static MyWeather ParseWeatherJson(String json ){
        MyWeather myWeather=new MyWeather();
        try{
            HourlyWeather[] weathers=new HourlyWeather[24];
            JSONObject jsonObject=new JSONObject(json);
            String status=jsonObject.getString("status");
            JSONArray jsonHourly=jsonObject.getJSONArray("hourly");
            for(int i=0;i<jsonHourly.length();i++){
                JSONObject tmpHourly=(JSONObject)jsonHourly.get(i);
                String text=tmpHourly.getString("text");
                String code=tmpHourly.getString("code");
                String temperature=tmpHourly.getString("temperature");
                String time=tmpHourly.getString("time");
                weathers[i]=new HourlyWeather(text,code,temperature,time);
            }
            myWeather.setStatus(status);
            myWeather.setWeathers(weathers);
            return myWeather;

        }catch (Exception e){
            e.printStackTrace();
            return myWeather;
        }
    }

}
