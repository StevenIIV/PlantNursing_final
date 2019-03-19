package com.example.lsc.weather1.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lsc.weather1.R;
import com.example.lsc.weather1.activities.MainActivity;
import com.example.lsc.weather1.apps.MyApp;
import com.example.lsc.weather1.entities.Plant;
import com.example.lsc.weather1.utils.FontUtils;
import com.example.lsc.weather1.utils.PlantUtil;

import java.io.InputStream;
/**
 * Created by lsc19 on 2018/3/28.
 */
public class PlantFragment extends Fragment {
    private Context context;
    private double temperature;
    private TextView tx_name,tx_subject,tx_hp,tx_intro,tx_advice,tx_jianyi,tx_jianjie;
    private ImageView img;
    private Button btn_edit,btn_delete;
    private Plant plant;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plant_frag, null);
        bindView(view);
        context=getActivity();
        if(plant!=null)refresh();
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "编辑功能暂未开启！", Toast.LENGTH_LONG).show();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert = null;
                builder = new AlertDialog.Builder(context);
                alert = builder
                        .setTitle("系统提示：")
                        .setMessage("确认删除吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.remove(PlantFragment.this);
                                transaction.commit();
                                Toast.makeText(context, "删除成功！", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "你取消了操作", Toast.LENGTH_SHORT).show();
                            }
                        }).create();             //创建AlertDialog对象
                alert.show();
            }
        });
        return view;
    }
    public void bindView(View view){
        tx_jianjie=(TextView)view.findViewById(R.id.textView4);
        tx_jianyi=(TextView)view.findViewById(R.id.textView5);
        tx_name=(TextView)view.findViewById(R.id.tx_plant_name);
        tx_subject=(TextView)view.findViewById(R.id.tx_plant_subject);
        tx_hp=(TextView)view.findViewById(R.id.tx_health_point);
        tx_intro=(TextView)view.findViewById(R.id.tx_plant_intro);
        tx_advice=(TextView)view.findViewById(R.id.tx_plant_advice);
        btn_edit = (Button) view.findViewById(R.id.btn_plant_edit);
        btn_delete = (Button) view.findViewById(R.id.btn_plant_delete);
        img=(ImageView)view.findViewById(R.id.img_plant);
    }

    public void refresh(){
        Integer hp=PlantUtil.HeathPointCalculator(temperature,plant.getK());
        Integer pos=1;
        if(hp>=80) {
            pos = 1;
            tx_hp.setTextColor(getResources().getColor(R.color.colorHPGreen));
        }
        else if(hp>=60){
            pos=2;
            tx_hp.setTextColor(getResources().getColor(R.color.colorHPOrange));
        }
        else {
            pos=3;
            tx_hp.setTextColor(getResources().getColor(R.color.colorHPRed));
        }
        Typeface tf=Typeface.createFromAsset(getResources().getAssets(), "fonts/yegentoute.ttf");
        tx_jianjie.setTypeface(tf);
        tx_jianyi.setTypeface(tf);
        tx_name.setText("名称 "+plant.getName().toString());
        tx_subject.setText(plant.getSubject().toString());
        tx_hp.setText("舒适度 "+ hp.toString());
        tx_intro.setText(plant.getIntro());
        tx_advice.setText((plant.getAdvice())[pos]);
        try{
            InputStream in=getResources().getAssets().open(plant.getImageUrl());
            Bitmap bitmap= BitmapFactory.decodeStream(in);
            img.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    public Plant getPlant() {
        return plant;
    }
    public void setPlant(Plant plant) {
        this.plant = plant;
    }
    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
