package com.example.mymenu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserPage extends AppCompatActivity {

    TextView tvUser;
    Intent in;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        tvUser = findViewById(R.id.tvUser);
        in = getIntent();
        if (in != null && in.getExtras() != null) {
            Bundle xtras = in.getExtras();
            name = xtras.getString("UName");
        }
        tvUser.setText("Welcome " + name);
    }
}