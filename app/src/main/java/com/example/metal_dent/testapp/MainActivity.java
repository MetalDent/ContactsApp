package com.example.metal_dent.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button c1;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c1 = (Button) findViewById(R.id.c1);
        text = (EditText) findViewById(R.id.title);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.c1){
                    Intent intent = new Intent(this, SubActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
