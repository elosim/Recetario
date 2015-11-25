package com.example.ivan.recetario.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Ayudante extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "recetario.sqlite";
    public static final int DATABASE_VERSION = 2;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null,
                DATABASE_VERSION);
        Log.v("SQLAAD", "constructor del ayudante");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        Log.v("SQLAAD","on upgrade");
        String sql="drop table if exists "
                + Contrato.TablaReceta.TABLA;
        db.execSQL(sql);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//Cuando se baja la aplicación y se crea por primera vez(no hay versión previa de la aplicación)
        Log.v("SQLAAD","on create");
        String sql;
        sql="create table "+Contrato.TablaReceta.TABLA+
                " ("+
                  Contrato.TablaReceta._ID + " integer primary key autoincrement, "+
                  Contrato.TablaReceta.NOMBRE+" text, "+
                  Contrato.TablaReceta.FOTO+" text, "+
                  Contrato.TablaReceta.INSTRUCCIONES +" text," +
                  Contrato.TablaReceta.ID_CATEGORIA+" integer "+
                ")";

        Log.v("añ2", sql);
        Log.v("añ2", "se ha creado la tabla recetas");
        db.execSQL(sql);

        sql="create table "+Contrato.TablaIngredientes.TABLA+
                " ("+
                  Contrato.TablaIngredientes._ID + " integer primary key autoincrement, "+
                  Contrato.TablaIngredientes.NOMBRE+" text "+
                ")";

        Log.v("añ2", sql);
        Log.v("añ2", "se ha creado la tabla Ingrediente");
        db.execSQL(sql);

        sql="create table "+Contrato.TablaRecetaIngredientes.TABLA+
                " ("+
                Contrato.TablaRecetaIngredientes._ID + " integer primary key autoincrement, "+
                Contrato.TablaRecetaIngredientes.IDINGREDIENTE+" integer, "+
                Contrato.TablaRecetaIngredientes.IDRECETA+" integer, "+
                Contrato.TablaRecetaIngredientes.CANTIDAD+" text "+
                ")";

        Log.v("añ2", sql);
        Log.v("añ2", "se ha creado la tabla RecetaIngrediente");
        db.execSQL(sql);

        sql="create table "+Contrato.TablaCategoria.TABLA+
                " ("+
                Contrato.TablaCategoria._ID + " integer primary key autoincrement, "+
                Contrato.TablaCategoria.NOMBRE+" text "+
                ")";

        Log.v("añ2", sql);
        Log.v("añ2", "se ha creado la tabla CATEGORIA");
        db.execSQL(sql);

        sql="insert into "+Contrato.TablaCategoria.TABLA+" ('"+Contrato.TablaCategoria._ID+"', '"+Contrato.TablaCategoria.NOMBRE+"') values (null, 'Carne');";
        Log.v("añ2", sql);
        db.execSQL(sql);
        sql="insert into "+Contrato.TablaCategoria.TABLA+" ('"+Contrato.TablaCategoria._ID+"', '"+Contrato.TablaCategoria.NOMBRE+"') values (null, 'Pescado');";
        db.execSQL(sql);
        sql="insert into "+Contrato.TablaCategoria.TABLA+" ('"+Contrato.TablaCategoria._ID+"', '"+Contrato.TablaCategoria.NOMBRE+"') values (null, 'Postre');";
        db.execSQL(sql);

        sql="create table "+Contrato.TablaRecetaUtensilio.TABLA+
                " ("+
                Contrato.TablaRecetaUtensilio._ID + " integer primary key autoincrement, "+
                Contrato.TablaRecetaUtensilio.IDRECETA+" long, "+
                Contrato.TablaRecetaUtensilio.IDUTENSILIO+" long "+
                ")";
        db.execSQL(sql);
        sql="create table "+Contrato.TablaUtensilio.TABLA+
                " ("+
                Contrato.TablaUtensilio._ID + " integer primary key autoincrement, "+
                Contrato.TablaUtensilio.NOMBRE+" text "+
                ")";

        db.execSQL(sql);
        sql="insert into "+Contrato.TablaUtensilio.TABLA+" ('"+Contrato.TablaUtensilio._ID+"', '"+Contrato.TablaUtensilio.NOMBRE+"') values (null, 'Batidora');";
        db.execSQL(sql);
        sql="insert into "+Contrato.TablaUtensilio.TABLA+" ('"+Contrato.TablaUtensilio._ID+"', '"+Contrato.TablaUtensilio.NOMBRE+"') values (null, 'Cucharon');";
        db.execSQL(sql);
        sql="insert into "+Contrato.TablaUtensilio.TABLA+" ('"+Contrato.TablaUtensilio._ID+"', '"+Contrato.TablaUtensilio.NOMBRE+"') values (null, 'Cuchillo');";
        db.execSQL(sql);
        sql="insert into "+Contrato.TablaUtensilio.TABLA+" ('"+Contrato.TablaUtensilio._ID+"', '"+Contrato.TablaUtensilio.NOMBRE+"') values (null, 'Manga pastelera');";
        db.execSQL(sql);
        sql="insert into "+Contrato.TablaUtensilio.TABLA+" ('"+Contrato.TablaUtensilio._ID+"', '"+Contrato.TablaUtensilio.NOMBRE+"') values (null, 'Olla express');";
        db.execSQL(sql);
        sql="insert into "+Contrato.TablaUtensilio.TABLA+" ('"+Contrato.TablaUtensilio._ID+"', '"+Contrato.TablaUtensilio.NOMBRE+"') values (null, 'Plato hondo');";
        db.execSQL(sql);
        sql="insert into "+Contrato.TablaUtensilio.TABLA+" ('"+Contrato.TablaUtensilio._ID+"', '"+Contrato.TablaUtensilio.NOMBRE+"') values (null, 'Rayador');";
        db.execSQL(sql);
        sql="insert into "+Contrato.TablaUtensilio.TABLA+" ('"+Contrato.TablaUtensilio._ID+"', '"+Contrato.TablaUtensilio.NOMBRE+"') values (null, 'Sarten');";
        db.execSQL(sql);


        sql="insert into "+Contrato.TablaIngredientes.TABLA+" ('"+Contrato.TablaIngredientes._ID+"', '"+Contrato.TablaIngredientes.NOMBRE+"') values (null, 'Agua');";
        db.execSQL(sql);
        sql="insert into "+Contrato.TablaIngredientes.TABLA+" ('"+Contrato.TablaIngredientes._ID+"', '"+Contrato.TablaIngredientes.NOMBRE+"') values (null, 'Almendras');";
        db.execSQL(sql);
        sql="insert into "+Contrato.TablaIngredientes.TABLA+" ('"+Contrato.TablaIngredientes._ID+"', '"+Contrato.TablaIngredientes.NOMBRE+"') values (null, 'Azucar');";
        db.execSQL(sql);
        sql="insert into "+Contrato.TablaIngredientes.TABLA+" ('"+Contrato.TablaIngredientes._ID+"', '"+Contrato.TablaIngredientes.NOMBRE+"') values (null, 'Huevo');";
        db.execSQL(sql);
        sql="insert into "+Contrato.TablaIngredientes.TABLA+" ('"+Contrato.TablaIngredientes._ID+"', '"+Contrato.TablaIngredientes.NOMBRE+"') values (null, 'Lomo');";
        db.execSQL(sql);
        sql="insert into "+Contrato.TablaIngredientes.TABLA+" ('"+Contrato.TablaIngredientes._ID+"', '"+Contrato.TablaIngredientes.NOMBRE+"') values (null, 'Mantequilla');";
        db.execSQL(sql);
        sql="insert into "+Contrato.TablaIngredientes.TABLA+" ('"+Contrato.TablaIngredientes._ID+"', '"+Contrato.TablaIngredientes.NOMBRE+"') values (null, 'Nata');";
        db.execSQL(sql);
        sql="insert into "+Contrato.TablaIngredientes.TABLA+" ('"+Contrato.TablaIngredientes._ID+"', '"+Contrato.TablaIngredientes.NOMBRE+"') values (null, 'Sal');";
        db.execSQL(sql);
        sql="insert into "+Contrato.TablaIngredientes.TABLA+" ('"+Contrato.TablaIngredientes._ID+"', '"+Contrato.TablaIngredientes.NOMBRE+"') values (null, 'Tomate');";
        db.execSQL(sql);

    }
}