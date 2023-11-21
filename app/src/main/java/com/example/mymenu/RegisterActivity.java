package com.example.mymenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText name, surename, email, etId, etPhone;
    Button btnInsert,btnView,btnUpdate,btnDelete;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        myDb = new DatabaseHelper(this);

        name = findViewById(R.id.etName);
        surename = findViewById(R.id.etSure);
        email = findViewById(R.id.etMail);
        etId = findViewById(R.id.etId);
        etPhone = findViewById(R.id.etPhone);

        btnInsert = findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(etId.getText().toString(),
                        name.getText().toString(),
                        surename.getText().toString(),
                        email.getText().toString(),
                        etPhone.getText().toString());

                if (isInserted == true)
                    Toast.makeText(RegisterActivity.this, "Data Inserted",
                            Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(RegisterActivity.this, "Data not Inserted",
                            Toast.LENGTH_LONG).show();
                etId.setText("");
                name.setText("");
                surename.setText("");
                email.setText("");
                etPhone.setText("");

            }
        });

        btnView = findViewById(R.id.btnView);
        btnView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
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

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                boolean isUpdated = myDb.updateData(etId.getText().toString(),
                        name.getText().toString(),
                        surename.getText().toString(),
                        email.getText().toString());
                if (isUpdated == true) {
                    Toast.makeText(RegisterActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Data Not Updated", Toast.LENGTH_LONG).show();
                }
            }

        });

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myDb.deleteData(etId.getText().toString());
                if (myDb.deleteData(etId.getText().toString()) > 0) {
                    Toast.makeText(RegisterActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Data Not Deleted", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


        public void showData(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}