package com.example.metal_dent.testapp.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.metal_dent.testapp.R;
import com.example.metal_dent.testapp.adapters.RecyclerViewAdapter;
import com.example.metal_dent.testapp.db.ContactDbHelper;
import com.example.metal_dent.testapp.models.Contact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Contact> contactList;
    private SQLiteDatabase mDatabase;
    private ContactDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_id);
        contactList = new ArrayList<>();
        dbHelper = new ContactDbHelper(this);

        Contact contact1 = new Contact("Priya");
        Contact contact2 = new Contact("Swaraj");

        contactList.add(contact1);
        contactList.add(contact2);

        setupRecyclerView(contactList);

        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    dbHelper.createDatabase();
                    dbHelper.openDatabase();
                    mDatabase = dbHelper.getReadableDatabase();
                    mDatabase.close();
                    dbHelper.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(r, 100);
    }

    private void setupRecyclerView(List<Contact> contactList) {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, contactList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}
