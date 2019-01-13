package com.App.metal_dent.testapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.metal_dent.testapp.R;

public class PasskeyActivity extends AppCompatActivity {

    EditText passcode;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passkey);

        submit = (Button) findViewById(R.id.submit);

        getSupportActionBar().hide();

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                passcode = (EditText) findViewById(R.id.passcode);
                if (passcode.getText().toString().equals("1234")) {
                    Intent homeIntent = new Intent(PasskeyActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                }

                else {
                    Intent homeIntent = new Intent(PasskeyActivity.this, errorActivity.class);
                    startActivity(homeIntent);
                }
            }
        });
    }
}
