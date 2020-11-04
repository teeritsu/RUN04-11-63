package com.example.apprunner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailEventUserActivity extends AppCompatActivity {
    TextView tv_event_name,tv_date_start,tv_detail,tv_date_end;
    String name_event,detail,type;
    String date_reg_start,date_reg_end;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event_user);

        tv_event_name = (TextView) findViewById(R.id.tv_event_name_user);
        tv_date_start = (TextView) findViewById(R.id.tv_date_start_user);
        tv_date_end = (TextView) findViewById(R.id.tv_date_end_user);
        tv_detail = (TextView) findViewById(R.id.tv_detail_user);
        name_event = getIntent().getExtras().getString("name_event");
        date_reg_start = getIntent().getExtras().getString("date_reg_start");
        date_reg_end = getIntent().getExtras().getString("date_reg_end");
        detail = getIntent().getExtras().getString("detail");
        type = getIntent().getExtras().getString("type");
        Toast.makeText(DetailEventUserActivity.this, detail, Toast.LENGTH_SHORT).show();
        tv_event_name.setText(name_event);
        tv_date_start.setText(date_reg_start);
        tv_date_end.setText(date_reg_end);
        tv_detail.setText(detail);
        btn_register = (Button) findViewById(R.id.btn_register);
        if(type != null && type.equals("นักวิ่ง")){
            btn_register.setText("ลงทะเบียนเข้าร่วม");
        }
        if(type != null && type.equals("ผู้จัดกิจกรรม")){
            btn_register.setText("ข้อมูลผู้เข้าร่วม");
        }
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}