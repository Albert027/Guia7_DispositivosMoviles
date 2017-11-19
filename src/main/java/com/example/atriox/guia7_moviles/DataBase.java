package com.example.atriox.guia7_moviles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Oscar Campos 19/11/2017.
 */

public class DataBase {

    private DataBaseHelper dataBaseHelper;

    DataBase(Context context){
        dataBaseHelper = new DataBaseHelper(context,"DataBase_Inventario",null,1);
    }

    public Cursor getCursorCategoria(){
        return dataBaseHelper.getReadableDatabase().rawQuery("select * from Categorias",null);
    }

    public Cursor getCursorProducto(){
        return dataBaseHelper.getReadableDatabase().rawQuery("select p.id_producto ,p.nombre ,p.descripcion ,p.id_categoria " + "from producto p,categoria c where p.id_categoria=c.id_categoria",null);
    }

    public Cursor getCursorProducto(String id_categoria){
        return dataBaseHelper.getReadableDatabase().rawQuery("select p.id_producto ,p.nombre ,p.descripcion ,p.id_categoria " + "from Productos p,Categorias c where p.id_categoria=c.id_categoria and " + "p.id_categoria=?",new String[]{id_categoria});
    }
    public ArrayList<Categorias> getArrayCategoria(Cursor cursor){
        cursor.moveToFirst();
        ArrayList<Categorias> lstCategoria = new ArrayList<>();
        if(cursor != null && cursor.getCount() > 0){//si hay datos
            do{
                lstCategoria.add(new Categorias(cursor.getString(0),cursor.getString(1)));
            }
            while (cursor.moveToNext());
            return lstCategoria;
        }
        return null;//si no entro en el if
    }
    public ArrayList<Productos> getArrayProducto(Cursor cursor){
        cursor.moveToFirst();//moverse al principio
        ArrayList<Productos> lstProducto = new ArrayList<>();
        if(cursor != null && cursor.getCount() > 0){//si hay datos
            do {
                lstProducto.add(new Productos(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }
            while (cursor.moveToNext());
            return lstProducto;
        }
        return null;
    }

    public boolean guardar_O_ActualizarCategoria(Categorias categoria) {
        ContentValues initialValues = new ContentValues();
        Log.d("Categoria","id "+categoria.getId_categoria());
        Log.d("Categoria","nombre "+categoria.getNombre());
        if(!categoria.getId_categoria().isEmpty())
            initialValues.put("id_categoria", Integer.parseInt(categoria.getId_categoria()));
        initialValues.put("nombre",categoria.getNombre());
        int id = (int) dataBaseHelper.getWritableDatabase().insertWithOnConflict(
                "Categorias",
                null,
                initialValues,
                SQLiteDatabase.CONFLICT_REPLACE);
        return id>0;
    }
    public boolean guardar_O_ActualizarProducto(Productos producto) {
        ContentValues initialValues = new ContentValues();
        if(!producto.getId_producto().isEmpty())
            initialValues.put("id_producto", Integer.parseInt(producto.getId_producto()));
        initialValues.put("nombre",producto.getNombre());
        initialValues.put("descripcion",producto.getDescripcion());
        initialValues.put("id_categoria",producto.getId_categoria());
        int id = (int) dataBaseHelper.getWritableDatabase().insertWithOnConflict(
                "Productos",
                null,
                initialValues,
                SQLiteDatabase.CONFLICT_REPLACE);
        return id>0;
    }
    public void  borrarCategoria(String id){
        dataBaseHelper.getWritableDatabase().execSQL(String.format("delete from Categorias where id_categoria='%s'",id));
    }
    public void  borrarProducto(String id){
        dataBaseHelper.getWritableDatabase().execSQL(String.format("delete from Productos where id_producto='%s'",id));
    }
}
