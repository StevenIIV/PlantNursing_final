package com.example.lsc.weather1.utils;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.example.lsc.weather1.R;
import com.example.lsc.weather1.entities.HourlyWeather;
import com.example.lsc.weather1.entities.MyAddress;
import com.example.lsc.weather1.entities.MyWeather;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Created by lsc19 on 2017/12/29.
 */

public class StringUtil {

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
                //temperature="10";
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

    public static MyAddress ParseLocationJson(String json ){
        MyAddress myAddress=new MyAddress();
        try{
            JSONObject jsonObject=new JSONObject(json);
            myAddress.setStatus(jsonObject.getInt("status"));
            JSONObject result=jsonObject.getJSONObject("result");
            myAddress.setFormatted_address(result.getString("formatted_address"));
            JSONObject addressComponent=result.getJSONObject("addressComponent");
            myAddress.setCountry(addressComponent.getString("country"));
            myAddress.setProvince(addressComponent.getString("province"));
            myAddress.setCity(addressComponent.getString("city"));
            myAddress.setDistrict(addressComponent.getString("district"));
            return myAddress;
        }catch (Exception e){
            e.printStackTrace();
            return myAddress;
        }
    }

    public static String nameTrim(String name){
        if(name.endsWith("市")||name.endsWith("县")||name.endsWith("区"))name=name.substring(0,name.length()-1);
        return name;
    }
    public static String parseXML(Context context, String cityName){
        XmlResourceParser xmlParser =context.getResources().getXml(R.xml.cityname);
        try {
            int flag=0;
            int event = xmlParser.getEventType();   //先获取当前解析器光标在哪
            while (event != XmlPullParser.END_DOCUMENT){    //如果还没到文档的结束标志，那么就继续往下处理
                switch (event){
                    case XmlPullParser.START_DOCUMENT:
                        Log.i(TAG,"xml解析开始");
                        break;
                    case XmlPullParser.START_TAG:
                        //一般都是获取标签的属性值，所以在这里数据你需要的数据
                        //   Log.d(TAG,"当前标签是："+xmlParser.getName());
                        if (xmlParser.getName().equals(cityName)){
                            event=xmlParser.next();
                            flag=1;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        //     Log.d(TAG, "Text:" + xmlParser.getText());
                        if(flag==1)return xmlParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                event = xmlParser.next();   //将当前解析器光标往下一步移
            }
            return null;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
