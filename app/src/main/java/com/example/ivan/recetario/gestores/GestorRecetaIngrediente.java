package com.example.ivan.recetario.gestores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ivan.recetario.bd.Ayudante;
import com.example.ivan.recetario.bd.Contrato;
import com.example.ivan.recetario.clasespojo.Receta;
import com.example.ivan.recetario.clasespojo.RecetaIngrediente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 11/23/2015.
 */
public class GestorRecetaIngrediente {
    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorRecetaIngrediente(Context c){
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

    public long insert(RecetaIngrediente p) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaRecetaIngredientes.IDINGREDIENTE, p.getIdingrediente());
        valores.put(Contrato.TablaRecetaIngredientes.CANTIDAD, p.getCantidad());
        valores.put(Contrato.TablaRecetaIngredientes.IDRECETA, p.getIdreceta());
        long id = bd.insert(Contrato.TablaRecetaIngredientes.TABLA, null, valores);

        Log.v("añ2 definitivo","Se ha añadido la con la id"+id);
        return id;

    }

    public int delete(RecetaIngrediente p) {
        return delete(p.getId());
    }

    public int delete(long id){
        String condicion = Contrato.TablaRecetaIngredientes._ID + " = ?";
        String[] argumentos = { id + "" };
        int cuenta = bd.delete(
                Contrato.TablaRecetaIngredientes.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int update(RecetaIngrediente p){
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaRecetaIngredientes.IDINGREDIENTE, p.getIdingrediente());
        valores.put(Contrato.TablaRecetaIngredientes.CANTIDAD, p.getCantidad());
        valores.put(Contrato.TablaRecetaIngredientes.IDRECETA, p.getIdreceta());
        String condicion = Contrato.TablaRecetaIngredientes._ID + " = ?";
        String[] argumentos = { p.getId() + "" };
        int cuenta = bd.update(Contrato.TablaRecetaIngredientes.TABLA, valores,
                condicion, argumentos);
        return cuenta;
    }

    public RecetaIngrediente getRow(Cursor c) {
        RecetaIngrediente p = new RecetaIngrediente();
        p.setId(c.getLong(c.getColumnIndex(Contrato.TablaRecetaIngredientes._ID)));
        p.setIdreceta(c.getLong(c.getColumnIndex(Contrato.TablaRecetaIngredientes.IDRECETA)));
        p.setIdingrediente(c.getLong(c.getColumnIndex(Contrato.TablaRecetaIngredientes.IDINGREDIENTE)));
        p.setCantidad(c.getLong(c.getColumnIndex(Contrato.TablaRecetaIngredientes.CANTIDAD)));
        return p;
    }

    public RecetaIngrediente getRow(long id) {
        Cursor c = getCursor("_id = ?", new String[]{id+""});
        Log.v("añ2",c.getString(c.getColumnIndex(Contrato.TablaRecetaIngredientes.IDINGREDIENTE)));
        return getRow(c);
    }

    public Cursor getCursor(){
        return getCursor(null, null);
    }

    public Cursor getCursor(String condicion, String[] parametros) {
        Cursor cursor = bd.query(
                Contrato.TablaRecetaIngredientes.TABLA, null, condicion, parametros, null,
                null, Contrato.TablaRecetaIngredientes.IDINGREDIENTE+", "+Contrato.TablaRecetaIngredientes.CANTIDAD);
        return cursor;
    }

    public List<RecetaIngrediente> select(String condicion, String[] parametros) {
        List<RecetaIngrediente> la = new ArrayList<>();
        Cursor cursor = getCursor(condicion, parametros);
        RecetaIngrediente p;
        while (cursor.moveToNext()) {
            p = getRow(cursor);
            la.add(p);
        }
        cursor.close();
        return la;
    }

    public List<RecetaIngrediente> select() {
        return select(null,null);
    }
}

