package com.lfc.textx5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lfc.textx5.text1.Textx5;
import com.lfc.textx5.text2.TextX5_Two;

public class MainActivity extends AppCompatActivity {

    private Button mBtnWeb1;
    private Button mBtnWeb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        mBtnWeb2 = (Button) findViewById(R.id.btn_web2);
        mBtnWeb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TextX5_Two.class));
            }
        });
    }
}
