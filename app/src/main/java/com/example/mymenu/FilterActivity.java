package com.example.mymenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FilterActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText etFilter;
    Button btSearch, btBack;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        myDb = new DatabaseHelper(this);
        etFilter = findViewById(R.id.etFilter);
        btSearch = findViewById(R.id.btSearch);

        btSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Cursor res = myDb.getDataNamed(etFilter.getText().toString());
                if (res.getCount() == 0) {
                    //show message
                    showData("Error", "No Data Found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("NAME: " + res.getString(1) + "\n");
                    buffer.append("SURNAME: " + res.getString(2) + "\n");
                    buffer.append("EMAIL: " + res.getString(3) + "\n\n");

                }
                showData("Data", buffer.toString());
            }
        });

        intent = new Intent(this,MainActivity.class);

        btBack = findViewById(R.id.btBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }

    public void showData(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}