package com.example.ivan.recetario.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.ivan.recetario.R;
import com.example.ivan.recetario.clasespojo.Receta;


public class CursorAdaptador extends CursorAdapter{

    public CursorAdaptador(Context co, Cursor cu) {
        super(co, cu, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.item, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv1 = (TextView)view.findViewById(R.id.tv1);
        Receta p = new Receta();
        p.set(cursor);
        tv1.setText(p.getNombre());

    }
}
