package com.lfc.textx5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lfc.textx5.text1.Textx5;
import com.lfc.textx5.text2.TextX5_Two;
import com.lfc.textx5.utils.LgU;
import com.lfc.textx5.utils.PreferencesUtils;
import com.lfc.textx5.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private Button mBtnWeb1;
    private Button mBtnWeb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dff.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String ee = dff.format(new Date());
        LgU.i("当前时间： " + ee);
        long nowtime = TimeUtils.dateToStamp(ee);
        PreferencesUtils.putLong(MainActivity.this, "APPStartTime", nowtime);
        initView();
    }

    private void initView() {
        mBtnWeb1 = (Button) findViewById(R.id.btn_web1);
        mBtnWeb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Textx5.class));
            }
        });
        mBtnWeb1.setVisibility(View.GONE);
//        正确的方法 gogogo
        mBtnWeb2 = (Button) findViewById(R.id.btn_web2);
        mBtnWeb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TextX5_Two.class));
            }
        });
    }
}
