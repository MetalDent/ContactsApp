package com.example.metal_dent.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {

    TextView name, phone, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        phone = (EditText) findViewById(R.id.phone);

        Intent intent = getIntent();
    }
}
