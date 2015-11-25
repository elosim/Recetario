package com.example.ivan.recetario.clasespojo;

import android.database.Cursor;

import com.example.ivan.recetario.bd.Contrato;

/**
 * Created by ivan on 11/18/2015.
 */
public class Receta {
    private long id;
    private String nombre;
    private String foto;
    private String instrucciones;
    private long id_categoria;

    public Receta(long id, String nombre, String foto, String instrucciones, long id_categoria) {
        this.id = id;
        this.nombre = nombre;
        this.foto = foto;
        this.instrucciones = instrucciones;
        this.id_categoria = id_categoria;
    }
    public Receta(){

    }


    public void set(Cursor c){
        setId(c.getLong(c.getColumnIndex(Contrato.TablaReceta._ID)));
        setNombre(c.getString(c.getColumnIndex(Contrato.TablaReceta.NOMBRE)));
        setFoto(c.getString(c.getColumnIndex(Contrato.TablaReceta.FOTO)));
        setInstrucciones(c.getString(c.getColumnIndex(Contrato.TablaReceta.INSTRUCCIONES)));
        setId_categoria(c.getInt(c.getColumnIndex(Contrato.TablaReceta.ID_CATEGORIA)));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(long id_categoria) {
        this.id_categoria = id_categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receta receta = (Receta) o;
        return id == receta.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Receta{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", foto='" + foto + '\'' +
                ", instrucciones='" + instrucciones + '\'' +
                ", id_categoria=" + id_categoria +
                '}';
    }
}
