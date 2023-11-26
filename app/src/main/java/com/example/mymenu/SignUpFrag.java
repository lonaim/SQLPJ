package com.example.mymenu;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class SignUpFrag extends Fragment {

    private DatabaseHelper myDb;
    private EditText name, surename, email, etId, etPhone;
    private Button btnInsert, btnView, btnUpdate, btnDelete;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_up, container, false);

        myDb = new DatabaseHelper(requireContext());

        name = view.findViewById(R.id.etName);
        surename = view.findViewById(R.id.etSure);
        email = view.findViewById(R.id.etMail);
        etId = view.findViewById(R.id.etId);
        etPhone = view.findViewById(R.id.etPhone);

        btnInsert = view.findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(etId.getText().toString(),
                        name.getText().toString(),
                        surename.getText().toString(),
                        email.getText().toString(),
                        etPhone.getText().toString());

                if (isInserted)
                    Toast.makeText(requireContext(), "Data Inserted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(requireContext(), "Data not Inserted", Toast.LENGTH_LONG).show();
                clearFields();
            }
        });

        btnView = view.findViewById(R.id.btnView);
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
                showData("Data", buffer.toString());            }
        });

        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDb.updateData(etId.getText().toString(),
                        name.getText().toString(),
                        surename.getText().toString(),
                        email.getText().toString());
                if (isUpdated == true) {
                    Toast.makeText(getContext(), "Data Updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Data Not Updated", Toast.LENGTH_LONG).show();
                }            }
        });

        btnDelete = view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.deleteData(etId.getText().toString());
                if (myDb.deleteData(etId.getText().toString()) > 0) {
                    Toast.makeText(getContext(), "Data Deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Data Not Deleted", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private void clearFields() {
        etId.setText("");
        name.setText("");
        surename.setText("");
        email.setText("");
        etPhone.setText("");
    }

    public void showData(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}