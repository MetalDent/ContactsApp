package com.App.metal_dent.testapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.metal_dent.testapp.R;

public class errorActivity extends AppCompatActivity {

    Button try_again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        try_again = (Button) findViewById(R.id.try_again);

        getSupportActionBar().hide();

        try_again.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent homeIntent = new Intent(errorActivity.this, PasskeyActivity.class);
                startActivity(homeIntent);
            }
        });
    }
}
