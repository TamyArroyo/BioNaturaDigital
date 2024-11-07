package com.example.prueba2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLOpenHelper extends SQLiteOpenHelper {

    public AdminSQLOpenHelper(@Nullable Context context,
                              @Nullable String name,
                              @Nullable SQLiteDatabase.CursorFactory factory,
                              int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE usuarios(id INTEGER PRIMARY KEY AUTOINCREMENT, " +"nombre text, apellido TEXT, usuario TEXT, contraseña TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE productos(codigo INTEGER PRIMARY KEY, nombre_producto text, precio real)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS usuarios");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS productos");
        onCreate(sqLiteDatabase);
    }
}
