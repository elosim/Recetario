package com.example.ivan.recetario.gestores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ivan.recetario.bd.Ayudante;
import com.example.ivan.recetario.bd.Contrato;
import com.example.ivan.recetario.clasespojo.Categoria;
import com.example.ivan.recetario.clasespojo.Ingrediente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2dam on 24/11/2015.
 */
public class GestorCategoria {
    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorCategoria(Context c){
        Log.v("SQLAAD", "constructor del gestor de películas");
        abd = new Ayudante(c);
    }
    public void open() {
        bd = abd.getWritableDatabase();
    }
    public void openRead() {
        bd = abd.getReadableDatabase();
    }
    public void close() {
        abd.close();
    }

    public long insert(Categoria p) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaCategoria.NOMBRE, p.getNombre());
        long id = bd.insert(Contrato.TablaCategoria.TABLA, null, valores);
        return id;
    }

    public int delete(Categoria p) {
        return delete(p.getId());
    }

    public int delete(long id){
        String condicion = Contrato.TablaCategoria._ID + " = ?";
        String[] argumentos = { id + "" };
        int cuenta = bd.delete(
                Contrato.TablaCategoria.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int update(Categoria p){
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaCategoria.NOMBRE, p.getNombre());
        valores.put(Contrato.TablaCategoria._ID, p.getId());
        String condicion = Contrato.TablaCategoria._ID + " = ?";
        String[] argumentos = { p.getId() + "" };
        int cuenta = bd.update(Contrato.TablaCategoria.TABLA, valores,
                condicion, argumentos);
        return cuenta;
    }

    public Categoria getRow(Cursor c) {
        Categoria p = new Categoria();
        p.setId(c.getLong(c.getColumnIndex(Contrato.TablaCategoria._ID)));
        p.setNombre(c.getString(c.getColumnIndex(Contrato.TablaCategoria.NOMBRE)));
        return p;
    }

    public Categoria getRow(long id) {
        Cursor c = getCursor("_id = ?", new String[]{id+""});
        return getRow(c);
    }

    public Cursor getCursor(){
        return getCursor(null, null);
    }

    public Cursor getCursor(String condicion, String[] parametros) {
        Cursor cursor = bd.query(
                Contrato.TablaCategoria.TABLA, null, condicion, parametros, null,
                null, Contrato.TablaCategoria.NOMBRE+", "+Contrato.TablaCategoria._ID);
        return cursor;
    }

    public List<Categoria> select(String condicion, String[] parametros) {
        List<Categoria> la = new ArrayList<>();
        Cursor cursor = getCursor(condicion, parametros);
        Categoria p;
        while (cursor.moveToNext()) {
            p = getRow(cursor);
            la.add(p);
        }
        cursor.close();
        return la;
    }

    public List<Categoria> select() {
        return select(null,null);
    }
}
