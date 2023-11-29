package com.example.mymenu;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
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
    private EditText etname, etsurename, etemail, etId, etPhone;
    private Button btnInsert, btnView, btnUpdate, btnDelete;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_up, container, false);

        myDb = new DatabaseHelper(requireContext());

        etname = view.findViewById(R.id.etName);
        etsurename = view.findViewById(R.id.etSure);
        etemail = view.findViewById(R.id.etMail);
        etId = view.findViewById(R.id.etId);
        etPhone = view.findViewById(R.id.etPhone);

        btnInsert = view.findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted=false;

                if(isDataValid()){
                isInserted = myDb.insertData(etId.getText().toString(),
                        etname.getText().toString(),
                        etsurename.getText().toString(),
                        etemail.getText().toString(),
                        etPhone.getText().toString());
                }

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
                    buffer.append("EMAIL: " + res.getString(3) + "\n");
                    buffer.append("Phone: " + res.getString(4) + "\n\n");

                }
                showData("Data", buffer.toString());            }
        });

        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDb.updateData(etId.getText().toString(),
                        etname.getText().toString(),
                        etsurename.getText().toString(),
                        etemail.getText().toString(),etPhone.getText().toString());
                if (isUpdated == true) {
                    Toast.makeText(getContext(), "Data Updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Data Not Updated", Toast.LENGTH_LONG).show();
                }
                clearFields();}
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
            clearFields();}
        });

        return view;
    }

    private void clearFields() {
        etId.setText("");
        etname.setText("");
        etsurename.setText("");
        etemail.setText("");
        etPhone.setText("");
    }

    public void showData(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private boolean isDataValid() {
        // Perform data integrity checks

        // Check if the name is not empty and contains only letters
        String name = etname.getText().toString();
        if (etname.length()==0|| !name.matches("[a-zA-Z]+")) {
            return false;
        }

        // Check if the surname is not empty and contains only letters
        String surname = etsurename.getText().toString();
        if (etsurename.length()==0 || !surname.matches("[a-zA-Z]+")) {
            return false;
        }

        // Check if the email is valid
        String email = etemail.getText().toString();
        if (etemail.length()==0 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        }

        // Check if the phone number is valid (you might want to add more checks)
        String phone = etPhone.getText().toString();
        if (etPhone.length()==0 || !Patterns.PHONE.matcher(phone).matches()) {
            return false;
        }

        // Check if the ID is not empty and contains only alphanumeric characters
        String id = etId.getText().toString();
        if (etId.length()==0|| !id.matches("[a-zA-Z0-9]+")) {
            return false;
        }


        // If all checks pass, data is considered valid
        return true;
    }


}