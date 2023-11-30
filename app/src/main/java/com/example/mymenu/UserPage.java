package com.example.mymenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserPage extends AppCompatActivity {

    TextView tvUser;
    Button logoutBt;
    Intent intent;
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        tvUser = findViewById(R.id.tvUser);

        Intent in = getIntent();
        if (in != null && in.getExtras() != null) {
            Bundle xtras = in.getExtras();
            String name = xtras.getString("UName");
            tvUser.setText("Welcome " + name);
        }
    }
}
