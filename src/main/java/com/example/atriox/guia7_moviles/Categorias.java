package com.example.atriox.guia7_moviles;

/**
 * Created by Oscar Campos on 19/11/2017.
 */

public class Categorias {

    String id_categoria;
    String nombre;

    public Categorias(String id_categoria, String nombre) {
        this.id_categoria = id_categoria;
        this.nombre = nombre;
    }

    public String getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(String id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}