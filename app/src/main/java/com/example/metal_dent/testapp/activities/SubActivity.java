package com.example.metal_dent.testapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;

import com.example.metal_dent.testapp.R;

import org.w3c.dom.Text;

public class SubActivity extends AppCompatActivity {

    TextView name, phone, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        //phone = (TextView) findViewById(R.id.phone);

        //Intent intent = getIntent();
    }
}
