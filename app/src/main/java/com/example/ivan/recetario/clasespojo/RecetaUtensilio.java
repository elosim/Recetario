package com.example.ivan.recetario.clasespojo;

/**
 * Created by 2dam on 24/11/2015.
 */
public class RecetaUtensilio {
    private long id;
    private long idreceta;
    private long idutensilio;

    public RecetaUtensilio() {
    }

    public RecetaUtensilio(long id, long idreceta, long idutensilio) {
        this.id = id;
        this.idreceta = idreceta;
        this.idutensilio = idutensilio;
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

    public long getIdutensilio() {
        return idutensilio;
    }

    public void setIdutensilio(long idutensilio) {
        this.idutensilio = idutensilio;
    }

    @Override
    public String toString() {
        return "RecetaUtensilio{" +
                "id=" + id +
                ", idreceta=" + idreceta +
                ", idutensilio=" + idutensilio +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecetaUtensilio that = (RecetaUtensilio) o;

        if (id != that.id) return false;
        if (idreceta != that.idreceta) return false;
        return idutensilio == that.idutensilio;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (idreceta ^ (idreceta >>> 32));
        result = 31 * result + (int) (idutensilio ^ (idutensilio >>> 32));
        return result;
    }

}
