package com.example.ivan.recetario.clasespojo;

/**
 * Created by 2dam on 24/11/2015.
 */
public class Categoria {
    private String nombre;
    private long id;

    public Categoria() {
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "nombre='" + nombre + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Categoria categoria = (Categoria) o;

        if (id != categoria.id) return false;
        return !(nombre != null ? !nombre.equals(categoria.nombre) : categoria.nombre != null);

    }

    @Override
    public int hashCode() {
        int result = nombre != null ? nombre.hashCode() : 0;
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Categoria(String nombre, long id) {
        this.nombre = nombre;
        this.id = id;
    }
}
