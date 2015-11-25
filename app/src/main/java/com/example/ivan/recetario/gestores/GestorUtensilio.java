package com.example.ivan.recetario.gestores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ivan.recetario.bd.Ayudante;
import com.example.ivan.recetario.bd.Contrato;
import com.example.ivan.recetario.clasespojo.RecetaIngrediente;
import com.example.ivan.recetario.clasespojo.Utensilio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2dam on 24/11/2015.
 */
public class GestorUtensilio {
    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorUtensilio(Context c){
        Log.v("SQLAAD", "constructor del gestor de recetaingrediente");
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

    public long insert(Utensilio p) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaUtensilio._ID, p.getId());
        valores.put(Contrato.TablaUtensilio.NOMBRE, p.getNombre());
        long id = bd.insert(Contrato.TablaUtensilio.TABLA, null, valores);
        return id;
    }

    public int delete(Utensilio p) {
        return delete(p.getId());
    }

    public int delete(long id){
        String condicion = Contrato.TablaUtensilio._ID + " = ?";
        String[] argumentos = { id + "" };
        int cuenta = bd.delete(
                Contrato.TablaUtensilio.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int update(Utensilio p){
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaUtensilio._ID, p.getId());
        valores.put(Contrato.TablaUtensilio.NOMBRE, p.getNombre());
        String condicion = Contrato.TablaUtensilio._ID + " = ?";
        String[] argumentos = { p.getId() + "" };
        int cuenta = bd.update(Contrato.TablaUtensilio.TABLA, valores,
                condicion, argumentos);
        return cuenta;
    }

    public Utensilio getRow(Cursor c) {
        Utensilio p = new Utensilio();
        p.setId(c.getLong(c.getColumnIndex(Contrato.TablaUtensilio._ID)));
        p.setNombre(c.getString(c.getColumnIndex(Contrato.TablaUtensilio.NOMBRE)));
        return p;
    }

    public Utensilio getRow(long id) {
        Cursor c = getCursor("_id = ?", new String[]{id+""});
        return getRow(c);
    }

    public Cursor getCursor(){
        return getCursor(null, null);
    }

    public Cursor getCursor(String condicion, String[] parametros) {
        Cursor cursor = bd.query(
                Contrato.TablaUtensilio.TABLA, null, condicion, parametros, null,
                null, null);
        return cursor;
    }

    public List<Utensilio> select(String condicion, String[] parametros) {
        List<Utensilio> la = new ArrayList<>();
        Cursor cursor = getCursor(condicion, parametros);
        Utensilio p;
        while (cursor.moveToNext()) {
            p = getRow(cursor);
            la.add(p);
        }
        cursor.close();
        return la;
    }

    public List<Utensilio> select() {
        return select(null,null);
    }
}
