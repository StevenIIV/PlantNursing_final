package com.example.lsc.weather1.utils;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import java.io.IOException;
import java.math.*;

import com.example.lsc.weather1.R;
import com.example.lsc.weather1.entities.*;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import static android.content.ContentValues.TAG;
public class PlantUtil {

    public static int HeathPointCalculator(double temperature ,double k[]) {
        return (int)(k[1]/(1 + Math.pow(Math.E, k[2] - k[3] * (temperature + k[4])))/ k[5] * 100);
    }

    public static Plant findPlant(Context context,String title, double temperature){
        Plant plant =new Plant();
        double[] tmp=new double[6];
        String[] advice=new String[4];
        XmlResourceParser xmlParser =context.getResources().getXml(R.xml.plant_info);
        try {
            int flag=0;
            int event = xmlParser.getEventType();   //先获取当前解析器光标在哪
            while (event != XmlPullParser.END_DOCUMENT){    //如果还没到文档的结束标志，那么就继续往下处理
                switch (event){
                    case XmlPullParser.START_DOCUMENT:
                        Log.i(TAG,"xml解析开始");
                        break;
                    case XmlPullParser.START_TAG:
                        String name=xmlParser.getName();//一般都是获取标签的属性值，所以在这里数据你需要的数据
                        Log.i(TAG,"当前标签是："+xmlParser.getName());
                        if (xmlParser.getName().equals(title)){
                            event=xmlParser.next();
                            name=xmlParser.getName();
                         //   flag=1;
                            while(!title.equals(xmlParser.getName())){
                               // event=xmlParser.next();
                                name=xmlParser.getName();
                                Log.i(TAG, "Name:" + xmlParser.getName());
                                event=xmlParser.next();
                               // name=xmlParser.getName();
                                Log.i(TAG, "Text:" + xmlParser.getText());
                                if("名称".equals(name)) plant.setName(xmlParser.getText());
                                if("科目".equals(name))plant.setSubject(xmlParser.getText());
                                if("图片位置".equals(name)) plant.setImageUrl(xmlParser.getText());
                                if(("公式参数".equals(name))){
                                    for(int i=1;i<=5;i++) {
                                        event=xmlParser.next();
                                        tmp[i] = Double.valueOf(xmlParser.getText());
                                        event = xmlParser.next();
                                        if(i!=5)event=xmlParser.next();
                                    }
                                    plant.setK(tmp);
                                }
                                if("简介".equals(name))  plant.setIntro(xmlParser.getText());
                                if("建议".equals(name)){
                                    for(int i=1;i<=3;i++) {
                                        event=xmlParser.next();
                                        advice[i]=xmlParser.getText();
                                        event = xmlParser.next();
                                        if(i!=3)event=xmlParser.next();
                                    }
                                    plant.setAdvice(advice);
                                }
                                event=xmlParser.next();
                                if(xmlParser.getName()!=title)event=xmlParser.next();
                            }
                            return plant;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        Log.i(xmlParser.getName(), "Textnxt:" + xmlParser.getText());
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
