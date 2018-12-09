package com.example.metal_dent.testapp.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.metal_dent.testapp.R;
import com.example.metal_dent.testapp.adapters.RecyclerViewAdapter;
import com.example.metal_dent.testapp.db.ContactContract;
import com.example.metal_dent.testapp.db.ContactContract.ContactEntry;
import com.example.metal_dent.testapp.db.ContactDbHelper;
import com.example.metal_dent.testapp.models.City;
import com.example.metal_dent.testapp.models.Contact;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubActivity extends AppCompatActivity {

    private String cityName;
    private RecyclerView recyclerView;
    private List<Contact> contactList;
    private ContactDbHelper dbHelper;
    private ProgressBar bar;

    /*** For search***/
    EditText edt;
    /*******/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        cityName = getIntent().getStringExtra("cityName");
        recyclerView = findViewById(R.id.recycler_view_sub);
        contactList = new ArrayList<>();
        dbHelper = new ContactDbHelper(this);
        bar = findViewById(R.id.progress_bar_sub);

        /*** for search ***/
        edt = (EditText)findViewById(R.id.editSearchText);
        /*******/

        bar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    dbHelper.createDatabase();
                    dbHelper.openDatabase();

                    getContactList();
                    setupRecyclerView(contactList);

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

    private void getContactList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.beginTransaction();

        final String query = "SELECT * FROM " + ContactEntry.TABLE_NAME
                + " WHERE " + ContactEntry.COL_CITY + "='" + cityName
                + "' ORDER BY " + ContactEntry.COL_NAME;

        Cursor cursor = db.rawQuery(query, null);

        while(cursor != null && cursor.moveToNext()) {
            Contact contact = new Contact();
            contact.setName(cursor.getString(cursor.getColumnIndexOrThrow(ContactEntry.COL_NAME)));
            contact.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(ContactEntry.COL_PHONE)));
            contactList.add(contact);
        }

        Log.d("db", "Fetching Contact data...");

        if(cursor != null) {
            cursor.close();
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    private void setupRecyclerView(List<Contact> contactList) {
        bar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        class ContactViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            TextView phone;

            public ContactViewHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.contact_name);
                phone = itemView.findViewById(R.id.contact_phone);
            }
        }

        RecyclerViewAdapter<Contact> adapter = new RecyclerViewAdapter<Contact>(this, contactList) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(SubActivity.this).inflate(R.layout.model_contact, parent, false);
                ContactViewHolder viewHolder = new ContactViewHolder(view);
                return viewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder1, Contact model) {
                ContactViewHolder holder = (ContactViewHolder) holder1;
                holder.name.setText(model.getName());
                holder.phone.setText(model.getPhone());
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}
