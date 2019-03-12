package com.cubContacts.metal_dent.testapp.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cubContacts.metal_dent.testapp.db.ContactContract;
import com.cubContacts.metal_dent.testapp.db.ContactDbHelper;
import com.example.metal_dent.testapp.R;


import java.io.IOException;

public class PasskeyActivity extends AppCompatActivity {

    EditText passcode;
    Button loginTx;
    TextView error;
    private ContactDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passkey);

        loginTx = findViewById(R.id.submit);
        passcode = findViewById(R.id.passcode);
        error = findViewById(R.id.errorView);
        dbHelper = new ContactDbHelper(this);

        getSupportActionBar().hide();

        loginTx.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login();
            }
        });
    }

    void login() {
        String passCode = passcode.getText().toString().trim();
        char[] pass = passCode.toCharArray();

        for(int i = 0; i < passCode.length(); i++) {
            if(!(pass[i] >= '0' && pass[i] <= '0'+9 || pass[i] == '-')) {
                error.setText("Enter a valid phone number!");
                passcode.setText("");
                return;
            }
        }

        try {
            dbHelper.createDatabase();
            dbHelper.openDatabase();

            if (isPhoneInDB(passCode)) {
                Intent homeIntent = new Intent(PasskeyActivity.this, MainActivity.class);
                startActivity(homeIntent);
            } else {
                error.setText("Phone number not found!");
                passcode.setText("");
            }

            dbHelper.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    boolean isPhoneInDB(String passcode) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.beginTransaction();

        final String query = "SELECT * FROM " + ContactContract.ContactEntry.TABLE_NAME
                + " WHERE " + ContactContract.ContactEntry.COL_PHONE + " = '" + passcode + "'";

        Cursor cursor = db.rawQuery(query, null);

        db.setTransactionSuccessful();
        db.endTransaction();

        if(cursor.getCount() <= 0) {
            cursor.close();
            db.close();

            return false;
        }

        cursor.close();
        db.close();

        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}
