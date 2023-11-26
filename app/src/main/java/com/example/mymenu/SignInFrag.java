package com.example.mymenu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

    public class SignInFrag extends Fragment {

    private DatabaseHelper myDb;
    private EditText name, surename;
    private Button btnCheck;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in, container, false);

        myDb = new DatabaseHelper(requireContext());

        name = view.findViewById(R.id.etName);
        surename = view.findViewById(R.id.etSure);

        btnCheck = view.findViewById(R.id.btnCheck);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myDb.dataExists(name.getText().toString(), surename.getText().toString()))
                    Toast.makeText(getContext(), "Data Exists", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getContext(), "Data Not Exists", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}