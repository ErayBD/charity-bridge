package com.eraybd.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // User tablosu oluşturma
        String createUserTable = "CREATE TABLE IF NOT EXISTS users (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "password TEXT NOT NULL);";
        db.execSQL(createUserTable);

        // Donation tablosu oluşturma
        String createDonationTable = "CREATE TABLE IF NOT EXISTS donations (" +
                "donation_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER NOT NULL," +
                "donation_type TEXT NOT NULL," +
                "price REAL NOT NULL," +
                "description TEXT," +
                "quantity INTEGER NOT NULL," +
                "location TEXT NOT NULL," +
                "contact_number TEXT NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES users (user_id));";
        db.execSQL(createDonationTable);

        // Diğer tabloları buraya ekleyebilirsiniz.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Veritabanı güncelleme işlemleri burada yapılabilir.
    }
}
