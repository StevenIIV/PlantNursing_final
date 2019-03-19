package com.example.lsc.weather1.utils;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.example.lsc.weather1.activities.MainActivity;
import com.example.lsc.weather1.apps.MyApp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lsc19 on 2018/1/6.
 */
public class HttpRequest {

    private StringBuilder response;
    private Context context;

    public HttpRequest(Context context){
        this.context=context;
    }

    public void sendHttpRequest(final String address,final int type) {
        new Thread(new Runnable() {
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();// 请求方式
                    connection.setRequestMethod("GET");// 连接超时
                    connection.setConnectTimeout(8000);// 读取超时
                    connection.setReadTimeout(8000);
                    connection.connect();// 获取输入流
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                    response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("response",response.toString());
                    MyApp app=(MyApp)context.getApplicationContext();
                    app.tmp=response.toString();
                    if(type==1){
                        app.myWeather= StringUtil.ParseWeatherJson(app.tmp);
                    }else if(type==2){
                        app.myAddress= StringUtil.ParseLocationJson(app.tmp);
                    }
                    Message message = new Message();
                    message.what = type;
                    ((MainActivity)context).myHandler.sendMessage(message);
                } catch (Exception e) {
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    public void sendWeatherRequest(String cityCode){
        String url="http://tj.nineton.cn/Heart/index/future24h/?city="+cityCode;
        sendHttpRequest(url,1);
    }

    public void sendLocationRequest(double longitude ,double latitude){
        String url="http://api.map.baidu.com/geocoder/v2/?&location="+latitude+","+longitude+
                "&output=json&pois=0&ak=fX9KKc5BqGuY7K8Ve9dvKAe3kMlwiYrz" +
                "&mcode=D1:94:62:01:73:A1:0A:43:E2:5B:80:AE:DC:4C:AF:28:12:50:CE:EA;com.example.lsc.weather1";
        sendHttpRequest(url,2);
    }
}
