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
    Button logoutBtn;
    Intent intent;
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        tvUser = findViewById(R.id.tvUser);
        logoutBtn = findViewById(R.id.logoutbtn);

        Intent in = getIntent();
        if (in != null && in.getExtras() != null) {
            Bundle xtras = in.getExtras();
            String name = xtras.getString("UName");
            tvUser.setText("Hello " + name + "!");
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear remember-me preference when logging out
                SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE).edit();
                editor.remove("rememberMe");
                editor.remove("username");
                editor.apply();

                // Redirect to FragHubActivity after logout
                Intent go = new Intent(view.getContext(), FragHubActivity.class);
                startActivity(go);
                finish(); // Close the UserPage activity
            }
        });
    }
}
