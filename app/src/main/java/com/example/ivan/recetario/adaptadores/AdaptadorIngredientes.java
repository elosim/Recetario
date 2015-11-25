package com.example.ivan.recetario.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.ivan.recetario.R;
import com.example.ivan.recetario.clasespojo.Ingrediente;
import com.example.ivan.recetario.clasespojo.Receta;

/**
 * Created by ivan on 11/23/2015.
 */
public class AdaptadorIngredientes extends CursorAdaptador {
        public AdaptadorIngredientes(Context co, Cursor cu) {
            super(co, cu);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater i = LayoutInflater.from(parent.getContext());
            View v = i.inflate(R.layout.item2, parent, false);
            return v;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView tv2 = (TextView)view.findViewById(R.id.tv2);
            Ingrediente p = new Ingrediente();
            p.set(cursor);
            tv2.setText(p.getNombre());

        }
}


