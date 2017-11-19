package com.example.atriox.guia7_moviles;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Oscar Campos on 19/11/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private String CrearTablaCategoria = "create table Categorias" + "(" + "id_categoria INTEGER PRIMARY KEY AUTOINCREMENT,"+ "nombre VARCHAR(250)"+ ")";
    private String CrearTablaProducto = "create table Productos" + "("+ "id_producto INTEGER PRIMARY KEY AUTOINCREMENT," + "nombre VARCHAR(250),"+ "descripcion TEXT," + "id_categoria INTEGER,"+ "foreign key (id_categoria) references categoria (id_categoria)" + ")";

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CrearTablaCategoria);
        db.execSQL(CrearTablaProducto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS producto");
        db.execSQL("DROP TABLE IF EXISTS categoria");
    }
}
