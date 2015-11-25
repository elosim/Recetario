package com.example.ivan.recetario.clasespojo;

/**
 * Created by ivan on 11/23/2015.
 */
public class RecetaIngrediente {
    private long id;
    private long idreceta;
    private long idingrediente;
    private long cantidad;

    public RecetaIngrediente() {
    }

    public RecetaIngrediente(long id, long idingrediente, long idreceta, long cantidad) {
        this.id = id;
        this.idingrediente = idingrediente;
        this.idreceta = idreceta;
        this.cantidad = cantidad;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdreceta() {
        return idreceta;
    }

    public void setIdreceta(long idreceta) {
        this.idreceta = idreceta;
    }

    public long getIdingrediente() {
        return idingrediente;
    }

    public void setIdingrediente(long idingrediente) {
        this.idingrediente = idingrediente;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "RecetaIngrediente{" +
                "id=" + id +
                ", idreceta=" + idreceta +
                ", idingrediente=" + idingrediente +
                ", cantidad=" + cantidad +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecetaIngrediente that = (RecetaIngrediente) o;

        if (id != that.id) return false;
        if (idreceta != that.idreceta) return false;
        if (idingrediente != that.idingrediente) return false;
        return cantidad == that.cantidad;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (idreceta ^ (idreceta >>> 32));
        result = 31 * result + (int) (idingrediente ^ (idingrediente >>> 32));
        result = 31 * result + (int) (cantidad ^ (cantidad >>> 32));
        return result;
    }
}
