package com.example.metal_dent.testapp.db;

import android.provider.BaseColumns;

public class ContactContract {
    static final String DB_NAME = "com.example.metal_dent.testapp.db";
    static final int DB_VERSION = 1;

    private ContactContract() {}

    public static class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "contacts";

        public static final String COL_NAME = "name";
        public static final String COL_PHONE = "phone";
        public static final String COL_CITY = "city";
    }
}
