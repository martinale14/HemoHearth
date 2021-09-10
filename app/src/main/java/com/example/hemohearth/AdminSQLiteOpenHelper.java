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
        db.execSQL("create table pacientesAnemia (correo text primary key, nombre text, apellido text, sexo text, edad text, hemoglobina real, tieneAnemia integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists pacientesDiabetes");
        db.execSQL("drop table if exists pacientesAnemia");
        db.execSQL("create table pacientesDiabetes (cedula integer primary key, nombre text, apellido text, eps text, tieneDiabetes integer)");
        db.execSQL("create table pacientesAnemia (correo text primary key, nombre text, apellido text, sexo text, edad text, hemoglobina real, tieneAnemia integer)");

    }
}
