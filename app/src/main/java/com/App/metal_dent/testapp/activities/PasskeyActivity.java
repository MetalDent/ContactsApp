package com.App.metal_dent.testapp.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.App.metal_dent.testapp.db.ContactContract;
import com.App.metal_dent.testapp.db.ContactDbHelper;
import com.example.metal_dent.testapp.R;

import java.io.IOException;

public class PasskeyActivity extends AppCompatActivity {

    EditText passcode;
    Button submit;
    private ContactDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passkey);

        submit = (Button) findViewById(R.id.submit);
        dbHelper = new ContactDbHelper(this);

        getSupportActionBar().hide();

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                passcode = (EditText) findViewById(R.id.passcode);

                try {
                    dbHelper.createDatabase();
                    dbHelper.openDatabase();

                    if (isPhoneInDB(passcode.getText().toString().trim())) {
                        Intent homeIntent = new Intent(PasskeyActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                    } else {
                        Intent homeIntent = new Intent(PasskeyActivity.this, errorActivity.class);
                        startActivity(homeIntent);
                    }

                    dbHelper.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            }
        });
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
