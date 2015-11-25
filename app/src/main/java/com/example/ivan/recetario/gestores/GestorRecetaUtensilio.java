package com.example.ivan.recetario.gestores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ivan.recetario.bd.Ayudante;
import com.example.ivan.recetario.bd.Contrato;
import com.example.ivan.recetario.clasespojo.RecetaIngrediente;
import com.example.ivan.recetario.clasespojo.RecetaUtensilio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 11/25/2015.
 */
public class GestorRecetaUtensilio {
    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorRecetaUtensilio(Context c){
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

    public long insert(RecetaUtensilio p) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaRecetaUtensilio.IDUTENSILIO, p.getIdutensilio());
        valores.put(Contrato.TablaRecetaUtensilio.IDRECETA, p.getIdreceta());
        long id = bd.insert(Contrato.TablaRecetaUtensilio.TABLA, null, valores);

        Log.v("añ2 definitivo","Se ha añadido la con la id"+id);
        return id;

    }

    public int delete(RecetaUtensilio p) {
        return delete(p.getId());
    }

    public int delete(long id){
        String condicion = Contrato.TablaRecetaUtensilio._ID + " = ?";
        String[] argumentos = { id + "" };
        int cuenta = bd.delete(
                Contrato.TablaRecetaUtensilio.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int update(RecetaUtensilio p){
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaRecetaUtensilio.IDUTENSILIO, p.getIdutensilio());
        valores.put(Contrato.TablaRecetaUtensilio.IDRECETA, p.getIdreceta());
        String condicion = Contrato.TablaRecetaUtensilio._ID + " = ?";
        String[] argumentos = { p.getId() + "" };
        int cuenta = bd.update(Contrato.TablaRecetaUtensilio.TABLA, valores,
                condicion, argumentos);
        return cuenta;
    }

    public RecetaUtensilio getRow(Cursor c) {
        RecetaUtensilio p = new RecetaUtensilio();
        p.setId(c.getLong(c.getColumnIndex(Contrato.TablaRecetaUtensilio._ID)));
        p.setIdreceta(c.getLong(c.getColumnIndex(Contrato.TablaRecetaUtensilio.IDRECETA)));
        p.setIdutensilio(c.getLong(c.getColumnIndex(Contrato.TablaRecetaUtensilio.IDUTENSILIO)));
        return p;
    }

    public RecetaUtensilio getRow(long id) {
        Cursor c = getCursor("_id = ?", new String[]{id+""});
        Log.v("añ2",c.getString(c.getColumnIndex(Contrato.TablaRecetaUtensilio.IDUTENSILIO)));
        return getRow(c);
    }

    public Cursor getCursor(){
        return getCursor(null, null);
    }

    public Cursor getCursor(String condicion, String[] parametros) {
        Cursor cursor = bd.query(
                Contrato.TablaRecetaUtensilio.TABLA, null, condicion, parametros, null,
                null, Contrato.TablaRecetaUtensilio.IDUTENSILIO+", "+Contrato.TablaRecetaUtensilio.IDRECETA);
        return cursor;
    }

    public List<RecetaUtensilio> select(String condicion, String[] parametros) {
        List<RecetaUtensilio> la = new ArrayList<>();
        Cursor cursor = getCursor(condicion, parametros);
        RecetaUtensilio p;
        while (cursor.moveToNext()) {
            p = getRow(cursor);
            la.add(p);
        }
        cursor.close();
        return la;
    }

    public List<RecetaUtensilio> select() {
        return select(null,null);
    }
}
