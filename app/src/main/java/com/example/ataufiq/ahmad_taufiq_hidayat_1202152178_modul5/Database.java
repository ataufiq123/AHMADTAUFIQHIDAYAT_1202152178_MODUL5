package com.example.ataufiq.ahmad_taufiq_hidayat_1202152178_modul5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by ataufiq on 25/03/18.
 */

public class Database extends SQLiteOpenHelper {

    Context context;
    SQLiteDatabase db;

    public static final String nama_db = "listtodo.db";
    public static final String nama_tabel = "daftartodo";
    public static final String kolom_1 = "todo";
    public static final String kolom_2 = "description";
    public static final String kolom_3 = "priority";

    //kontruktor
    public Database(Context context) {
        super(context, nama_db, null, 1);
        this.context = context;
        db = this.getWritableDatabase();
        db.execSQL("create table if not exists " + nama_tabel +
                " (todo varchar(35) primary key, " +
                "description varchar(50), " +
                "priority varchar(3))");
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists " + nama_tabel +
                " (todo varchar(35) primary key, " +
                "description varchar(50), " +
                "priority varchar(3))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + nama_tabel);
        onCreate(sqLiteDatabase);
    }

    public boolean createData(ListItem list) {
        //mencocokkan kolom beserta nilainya
        ContentValues val = new ContentValues();
        val.put(kolom_1, list.getTodo());
        val.put(kolom_2, list.getDesc());
        val.put(kolom_3, list.getPrior());
        long hasil = db.insert(nama_tabel, null, val);
        if (hasil == -1) {
            return false;
        } else {
            return true;
        }
    }


    public boolean deleteData(String ToDo) {
        return db.delete(nama_tabel, kolom_1 + "=\"" + ToDo + "\"", null) > 0;
    }


    public void readData(ArrayList<ListItem> daftar) {
        Cursor cursor = this.getReadableDatabase().rawQuery(
                "select todo, description, priority from " + nama_tabel, null);

        while (cursor.moveToNext()) {
            daftar.add(new ListItem(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)));
        }
    }
}