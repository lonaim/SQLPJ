package com.example.mymenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class SignInFrag extends Fragment {

    private DatabaseHelper myDb;
    private EditText name, surename;
    private CheckBox rememberMeCheckBox;
    private Button btnCheck;
    public static final String SHARED_PREFS = "sharedPrefs";

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in, container, false);

        myDb = new DatabaseHelper(requireContext());

        // Check if the user is remembered when the fragment is created
        onCheck();

        name = view.findViewById(R.id.etName);
        surename = view.findViewById(R.id.etSure);
        rememberMeCheckBox = view.findViewById(R.id.boxCheck);

        btnCheck = view.findViewById(R.id.btnCheck);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myDb.dataExists(name.getText().toString(), surename.getText().toString())) {
                    Toast.makeText(getContext(), "Data Exists", Toast.LENGTH_LONG).show();

                    // Save the username in SharedPreferences when "Remember Me" is checked
                    if (rememberMeCheckBox.isChecked()) {
                        SharedPreferences.Editor editor = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE).edit();
                        editor.putString("username", name.getText().toString());
                        editor.apply();
                    }

                    // Continue with the rest of your logic
                    Cursor res = myDb.getDataCursor(name.getText().toString(), surename.getText().toString());
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer.append(res.getString(1).toString());
                    }
                    String userName = buffer.toString();
                    Intent go = new Intent(view.getContext(), UserPage.class);
                    go.putExtra("UName", userName);
                    startActivity(go);
                } else {
                    Toast.makeText(getContext(), "Data Not Exists", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    private void onCheck() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username", "");

        if (!savedUsername.isEmpty()) {
            // If the username is saved, navigate to UserPage
            Intent go = new Intent(getContext(), UserPage.class);
            go.putExtra("UName", savedUsername);
            startActivity(go);
        }

    }
}
