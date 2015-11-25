package com.example.ivan.recetario.clasespojo;

import android.database.Cursor;

import com.example.ivan.recetario.bd.Contrato;

/**
 * Created by 2dam on 24/11/2015.
 */
public class Utensilio {
    private long id;
    private String nombre;

    public Utensilio() {

    }

    public Utensilio(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    public void set(Cursor c){
        setId(c.getLong(c.getColumnIndex(Contrato.TablaUtensilio._ID)));
        setNombre(c.getString(c.getColumnIndex(Contrato.TablaUtensilio.NOMBRE)));
    }

    @Override
    public String toString() {
        return "Utensilio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utensilio utensilio = (Utensilio) o;

        if (id != utensilio.id) return false;
        return !(nombre != null ? !nombre.equals(utensilio.nombre) : utensilio.nombre != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }
}
