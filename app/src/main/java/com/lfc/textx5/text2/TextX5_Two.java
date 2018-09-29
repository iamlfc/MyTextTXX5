package com.lfc.textx5.text2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lfc.textx5.R;

public class TextX5_Two extends AppCompatActivity {


    private static final String KEY_DOC = "https://raw.githubusercontent.com/iamlfc/MyTextLayout/master/app/src/main/assets/%E6%96%B0%E5%91%98%E5%B7%A5%E5%91%A8%E8%BE%85%E5%AF%BC%E8%80%83%E6%A0%B8%E8%A1%A8.docx";
    private static final String KEY_TXT = "https://raw.githubusercontent.com/iamlfc/MyTextLayout/master/app/src/main/assets/%E6%8A%A5%E8%8F%9C%E5%90%8D.txt";
    private static final String KEY_PPT = "https://raw.githubusercontent.com/iamlfc/MyTextLayout/master/app/src/main/assets/%E4%BC%98%E7%A7%80%E8%AE%BA%E6%96%87%E7%AD%94%E8%BE%A9PPT%E8%8C%83%E4%BE%8B.ppt";
//    private static final String KEY_PDF = "http://www.cals.uidaho.edu/edComm/curricula/CustRel_curriculum/content/sample.pdf";
    private static final String KEY_PDF = "http://app.zixianmed.com/upload/pdf/1077221537061287.pdf";
    private static final String KEY_EXCEL = "https://raw.githubusercontent.com/iamlfc/MyTextLayout/master/app/src/main/assets/%E9%9C%80%E6%B1%82%E5%8F%98%E6%9B%B4%E5%8D%95.xlsx";
    private Button mBtn01;
    private Button mBtn02;
    private Button mBtn03;
    private Button mBtn04;
    private Button mBtn05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_x5_two);
        initView();
    }

    private void initView() {
        mBtn01 = (Button) findViewById(R.id.btn_01);
        mBtn02 = (Button) findViewById(R.id.btn_02);
        mBtn03 = (Button) findViewById(R.id.btn_03);
        mBtn04 = (Button) findViewById(R.id.btn_04);
        mBtn05 = (Button) findViewById(R.id.btn_05);
        mBtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileDetail_A.EnterThis(TextX5_Two.this, KEY_DOC);
            }
        });
        mBtn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileDetail_A.EnterThis(TextX5_Two.this, KEY_TXT);

            }
        });
        mBtn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileDetail_A.EnterThis(TextX5_Two.this, KEY_PPT);

            }
        });
        mBtn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileDetail_A.EnterThis(TextX5_Two.this, KEY_PDF);

            }
        });
        mBtn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileDetail_A.EnterThis(TextX5_Two.this, KEY_EXCEL);

            }
        });
    }
}
