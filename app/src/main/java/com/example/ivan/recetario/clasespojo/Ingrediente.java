package com.example.ivan.recetario.clasespojo;

import android.database.Cursor;

import com.example.ivan.recetario.bd.Contrato;

/**
 * Created by ivan on 11/18/2015.
 */
public class Ingrediente {
    private long id;
    private String nombre;

    public Ingrediente(String nombre, long id) {
        this.nombre = nombre;
        this.id = id;
    }
    public Ingrediente(){}
    public void set(Cursor c){
        setId(c.getLong(c.getColumnIndex(Contrato.TablaIngredientes._ID)));
        setNombre(c.getString(c.getColumnIndex(Contrato.TablaIngredientes.NOMBRE)));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingrediente that = (Ingrediente) o;

        if (id != that.id) return false;
        return !(nombre != null ? !nombre.equals(that.nombre) : that.nombre != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }
}
