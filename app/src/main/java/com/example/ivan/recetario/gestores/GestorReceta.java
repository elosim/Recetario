package com.example.ivan.recetario.gestores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.ivan.recetario.bd.Ayudante;
import com.example.ivan.recetario.bd.Contrato;
import com.example.ivan.recetario.clasespojo.Receta;

import java.util.ArrayList;
import java.util.List;

public class GestorReceta {

    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorReceta(Context c){
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

    public long insert(Receta p) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaReceta.NOMBRE, p.getNombre());
        valores.put(Contrato.TablaReceta.INSTRUCCIONES, p.getInstrucciones());
        valores.put(Contrato.TablaReceta.FOTO, p.getFoto());
        valores.put(Contrato.TablaReceta.ID_CATEGORIA, p.getId_categoria());
        long id = bd.insert(Contrato.TablaReceta.TABLA, null, valores);
        return id;
    }

    public int delete(Receta p) {
        return delete(p.getId());
    }

    public int delete(long id){
        String condicion = Contrato.TablaReceta._ID + " = ?";
        String[] argumentos = { id + "" };
        int cuenta = bd.delete(
                Contrato.TablaReceta.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int update(Receta p){
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaReceta.NOMBRE, p.getNombre());
        valores.put(Contrato.TablaReceta.INSTRUCCIONES, p.getInstrucciones());
        valores.put(Contrato.TablaReceta.FOTO, p.getFoto());
        valores.put(Contrato.TablaReceta.ID_CATEGORIA, p.getId_categoria());
        String condicion = Contrato.TablaReceta._ID + " = ?";
        String[] argumentos = { p.getId() + "" };
//        int cuenta = bd.update(Contrato.TablaReceta.TABLA, valores,
//                condicion, argumentos);
        bd.rawQuery("UPDATE "+Contrato.TablaReceta.TABLA+" SET '"+Contrato.TablaReceta.NOMBRE+"' = '"+p.getNombre()+"',  '"
                        +Contrato.TablaReceta.INSTRUCCIONES+"' = '"+p.getInstrucciones()+"', '"+
                        Contrato.TablaReceta.FOTO+"' = '"+p.getFoto()+"', '"+
                        Contrato.TablaReceta.ID_CATEGORIA+"' = "+p.getId_categoria()+" "+
                        "where '"+Contrato.TablaReceta._ID+ "' = "+p.getId()+";", null);


        return 1;
    }

    public Receta getRow(Cursor c) {
        Receta p = new Receta();
        p.setId(c.getLong(c.getColumnIndex(Contrato.TablaReceta._ID)));
        p.setNombre(c.getString(c.getColumnIndex(Contrato.TablaReceta.NOMBRE)));
        p.setFoto(c.getString(c.getColumnIndex(Contrato.TablaReceta.FOTO)));
        p.setInstrucciones(c.getString(c.getColumnIndex(Contrato.TablaReceta.INSTRUCCIONES)));
        p.setId_categoria(c.getInt(c.getColumnIndex(Contrato.TablaReceta.ID_CATEGORIA)));
        return p;
    }

    public Receta getRow(long id) {
        Cursor c = getCursor("_id = ?", new String[]{id+""});
        Log.v("añ2",c.getString(c.getColumnIndex(Contrato.TablaReceta.NOMBRE)));
        return getRow(c);
    }

    public Cursor getCursor(){
        return getCursor(null, null);
    }

    public Cursor getCursor(String condicion, String[] parametros) {
        Cursor cursor = bd.query(
                Contrato.TablaReceta.TABLA, null, condicion, parametros, null,
                null, Contrato.TablaReceta.NOMBRE+", "+Contrato.TablaReceta.ID_CATEGORIA);
        return cursor;
    }

    public List<Receta> select(String condicion, String[] parametros) {
        List<Receta> la = new ArrayList<>();
        Cursor cursor = getCursor(condicion, parametros);
        Receta p;
        while (cursor.moveToNext()) {
            p = getRow(cursor);
            la.add(p);
        }
        cursor.close();
        return la;
    }

    public List<Receta> select() {
        return select(null,null);
    }
}