package com.example.metal_dent.testapp.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.metal_dent.testapp.db.ContactContract.ContactEntry;
import com.example.metal_dent.testapp.models.Contact;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ContactDbHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "";
    private SQLiteDatabase mDatabase;
    private Context mContext;

//    private static final String SQL_CREATE_ENTRIES =
//            "CREATE TABLE " + ContactEntry.TABLE_NAME + " (" +
//                    ContactEntry._ID + " INTEGER PRIMARY KEY, " +
//                    ContactEntry.COL_NAME + " TEXT NOT NULL, " +
//                    ContactEntry.COL_PHONE + " TEXT NOT NULL, " +
//                    ContactEntry.COL_CITY + " TEXT NOT NULL)";
//
//    private static final String SQL_DELETE_ENTRIES =
//            "DROP TABLE IF EXISTS " + ContactEntry.TABLE_NAME;


    public ContactDbHelper(Context context) {
        super(context, ContactContract.DB_NAME, null, ContactContract.DB_VERSION);
        Log.d("db", "Database constructor called...");

        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
    }

    public void createDatabase() throws IOException {
        File dbFile = new File(DB_PATH + ContactContract.DB_NAME);
        if(!dbFile.exists()) {
            this.getReadableDatabase();
            this.close();

            copyDatabase();
            Log.d("db", "Database Copied.");
        }
    }

    private void copyDatabase() throws IOException {
        InputStream in = mContext.getAssets().open(ContactContract.DB_NAME);
        String outFileName = DB_PATH + ContactContract.DB_NAME;
        OutputStream out = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while((length = in.read(buffer)) > 0)
            out.write(buffer, 0, length);

        out.flush();
        out.close();
        in.close();
    }

    public boolean openDatabase() throws SQLException {
        String dbFile = DB_PATH + ContactContract.DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(dbFile, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return (mDatabase != null);
    }

    @Override
    public synchronized void close() {
        if(mDatabase != null)
            mDatabase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(SQL_CREATE_ENTRIES);
        //Log.d("db", "Table Created...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //db.execSQL(SQL_DELETE_ENTRIES);
        //Log.d("db", "Table Upgraded...");
        //onCreate(db);

//        File dbFile = new File(DB_PATH + ContactContract.DB_NAME);
//        if(dbFile.exists()) {
//            mDatabase.close();
//            mContext.deleteDatabase(DB_PATH + ContactContract.DB_NAME);
//            dbFile.delete();
//        }

        mContext.deleteDatabase(ContactContract.DB_NAME);
        mDatabase.close();
        new ContactDbHelper(mContext);


        Log.d("db", "Database Upgraded.");
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
