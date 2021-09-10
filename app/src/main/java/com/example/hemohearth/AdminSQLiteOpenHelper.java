package com.example.hemohearth;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table pacientesDiabetes (cedula integer primary key, nombre text, apellido text, eps text, tieneDiabetes integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists pacientesDiabetes");
        db.execSQL("create table pacientesDiabetes (cedula integer primary key, nombre text, apellido text, eps text, tieneDiabetes integer)");
    }
}
