package com.example.lsc.weather1.activities;
import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lsc.weather1.R;
import com.example.lsc.weather1.fragments.PlantFragment;
import com.example.lsc.weather1.utils.FontUtils;
import com.example.lsc.weather1.utils.PlantUtil;
import com.example.lsc.weather1.utils.StringUtil;
import com.example.lsc.weather1.apps.MyApp;
import com.example.lsc.weather1.utils.HttpRequest;
import com.example.lsc.weather1.utils.LocationUtils;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView wh_text,wh_code,wh_temperature,wh_time, tx_longitude, tx_latitude, tx_city,tx_district,tx_citycode;
    private Button button ,displayBtn;
    private FloatingActionButton fab;
    private static final int PERMISSION_REQUESTCODE = 1;
    private HttpRequest weatherConn,locationConn;
    private Location location;
    private MyApp app;
    private double temperature=20.0;
    private int nowHour=12;
    public Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if(app.myWeather!=null) {
                        wh_text.setText(app.myWeather.getWeathersAt(nowHour).text);
                        wh_code.setText(String.valueOf(app.myWeather.getWeathersAt(nowHour).code));
                        wh_temperature.setText("气温  "+String.valueOf(app.myWeather.getWeathersAt(nowHour).temperature)+"℃");
                        wh_time.setText(app.myWeather.getWeathersAt(nowHour).time);
                        temperature=Double.valueOf(app.myWeather.getWeathersAt(nowHour).temperature.toString());
                    }
                    else Log.i("weather","is null!!!!!");
                    break;
                case 2:
                    if(app.myAddress!=null) {
                        tx_city.setText("城市  "+app.myAddress.city);
                        tx_district.setText(app.myAddress.district);
                        String districtName = StringUtil.nameTrim(app.myAddress.district);
                        String cityCode = StringUtil.parseXML(MainActivity.this, districtName);
                        if (cityCode == null) {
                            String cityName = StringUtil.nameTrim(app.myAddress.city);
                            cityCode = StringUtil.parseXML(MainActivity.this, cityName);
                        }
                        if (cityCode != null){
                            Log.i("name!!!", cityCode);
                            tx_citycode.setText(cityCode);
                            weatherConn = new HttpRequest(MainActivity.this);
                            weatherConn.sendWeatherRequest(cityCode);
                        }else{
                            tx_citycode.setText("null");
                        }
                    }
                    else {
                        tx_city.setText("null");
                        tx_district.setText("null");
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT){
            //实现透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //实现透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        ActionBar actionBar=getSupportActionBar();
        actionBar.setCustomView(R.layout.actionbar_title);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        bindView();
        app=(MyApp) getApplication();
        Time time=new Time();
        time.setToNow();
        nowHour=time.hour;
        Log.i("hour",String.valueOf(nowHour));
        permission();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.showContextMenu();
            }
        });
    }
    public void bindView(){
        button = (Button) findViewById(R.id.button);
        wh_text=(TextView) findViewById(R.id.tx_weather_stat);
        wh_code=(TextView) findViewById(R.id.wh_code);
        wh_temperature=(TextView) findViewById(R.id.tx_temperature);
        wh_time=(TextView) findViewById(R.id.wh_time);
        tx_longitude =(TextView) findViewById(R.id.longitude);
        tx_latitude =(TextView) findViewById(R.id.latitude);
        tx_city =(TextView)findViewById(R.id.tx_city);
        tx_district =(TextView)findViewById(R.id.tx_district);
        tx_citycode=(TextView)findViewById(R.id.citycode);
        fab= (FloatingActionButton) findViewById(R.id.fab_add);
        registerForContextMenu(fab);
    }
    @Override
    public void registerForContextMenu(View view) {
        super.registerForContextMenu(view);
        view.setLongClickable(false);
    }
    //重写上下文菜单的创建方法
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflator = new MenuInflater(this);
        inflator.inflate(R.menu.menu_plant, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.i("Title:",item.getTitle().toString());
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        PlantFragment plantFragment = new PlantFragment();
        plantFragment.setPlant(PlantUtil.findPlant(MainActivity.this,item.getTitle().toString(),temperature));
        plantFragment.setTemperature(temperature);
        transaction.add(R.id.line_down,plantFragment);
        transaction.commit();
        return true;
    }

    private void permission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){//没有授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUESTCODE);
        }else{//已经授权
             showLocation();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUESTCODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){//用户点击了同意授权
                    showLocation();
                }else{//用户拒绝了授权
                    Toast.makeText(this,"权限被拒绝",Toast.LENGTH_SHORT).show();
                }
                break;
            default: break;
        }
    }

    public void showLocation(){
        location= LocationUtils.getBestLocation(this);
        if(location==null){
            tx_city.setText("null");
        }else {
            double longit=location.getLongitude();
            double latit=location.getLatitude();
            String longi=String.valueOf(longit);
            String lati=String.valueOf(latit);
            locationConn=new HttpRequest(MainActivity.this);
            locationConn.sendLocationRequest(longit,latit);
            tx_longitude.setText(longi);
            tx_latitude.setText(lati);
        }
    }
}
