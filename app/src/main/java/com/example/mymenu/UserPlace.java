package com.example.mymenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UserPlace extends AppCompatActivity {
    Intent in;
    TextView tvUser;
    String name="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_place);
        tvUser = findViewById(R.id.tvUser);
        in=getIntent();
        if(in!=null&&in.getExtras()!=null){
            Bundle xtras = in.getExtras();
            name = xtras.getString("UName");
        }

        tvUser.setText("Hello "+name+"!");
    }
}