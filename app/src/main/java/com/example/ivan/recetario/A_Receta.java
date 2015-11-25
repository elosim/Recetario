package com.example.ivan.recetario;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.media.IMediaBrowserServiceCompat;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.ivan.recetario.bd.Contrato;
import com.example.ivan.recetario.clasespojo.Receta;
import com.example.ivan.recetario.gestores.GestorCategoria;
import com.example.ivan.recetario.gestores.GestorIngrediente;
import com.example.ivan.recetario.gestores.GestorRecetaIngrediente;
import com.example.ivan.recetario.gestores.GestorRecetaUtensilio;
import com.example.ivan.recetario.gestores.GestorUtensilio;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class A_Receta extends AppCompatActivity {
    public static final String EXTRA_NAME = "cheese_name";
    private Receta r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        TextView tvInstruc = (TextView)findViewById(R.id.tvInstrucc);
        TextView tvCat = (TextView)findViewById(R.id.categoria);
        TextView tvIngr = (TextView)findViewById(R.id.tvIngre);
        TextView tvUten = (TextView)findViewById(R.id.tvUten);


        String nombre = getIntent().getExtras().getString("nombre").toUpperCase();
        String instruc = getIntent().getExtras().getString("instrucciones").toUpperCase();
        String ingred= buscarIngr(getIntent().getExtras().getLong("id")).toUpperCase();
        String ruta =getIntent().getExtras().getString("foto");
        String uten= buscarUten(getIntent().getExtras().getLong("id")).toUpperCase();

        nombre=(nombre.charAt(0)+"").toUpperCase()+nombre.substring(1);
        instruc=(instruc.charAt(0)+"").toUpperCase()+instruc.substring(1);
        ingred=(ingred.charAt(0)+"").toUpperCase()+ingred.substring(1);
        uten=(uten.charAt(0)+"").toUpperCase()+uten.substring(1);

        long cat = getIntent().getExtras().getLong("cat");

        tvCat.setText("Categoria " + getCategoria(cat) + "");
        tvInstruc.setText(instruc);
        tvIngr.setText(ingred);
        tvUten.setText(uten);

        Uri uri2 = Uri.parse(ruta);

        imageView.setImageURI(uri2);
        collapsingToolbar.setTitle(nombre);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_a__receta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public String buscarUten(long id){
        GestorRecetaUtensilio gri = new GestorRecetaUtensilio(this);
        GestorUtensilio gi =  new GestorUtensilio(this);
        Cursor c;
        List<Long> utensilios = new ArrayList<>();
        String out="";

        gri.open();
        String where = Contrato.TablaRecetaUtensilio.IDRECETA + " = ?";

        String[] parametros = new String[] { id+"" };
        c = gri.getCursor(where,parametros);

        while(c.moveToNext()) {
            utensilios.add(c.getLong(c.getColumnIndex("idingrediente")));

        }

        gri.close();

        gi.open();

        for(long i : utensilios){
            where = Contrato.TablaUtensilio._ID + " = ? ";
            parametros = new String[] { i+"" };
            c = gi.getCursor(where,parametros);
            while (c.moveToNext()){

                out += c.getString(1)+"\r\n";
            }
        }
        gi.close();
        return out;

    }
    public String buscarIngr(long id){
        GestorRecetaIngrediente gri = new GestorRecetaIngrediente(this);
        GestorIngrediente gi =  new GestorIngrediente(this);
        Cursor c;
        List<Long> ingredientes = new ArrayList<>();
        List<Long> cantidad = new ArrayList<>();
        String out="";

        gri.open();
        String where = Contrato.TablaRecetaIngredientes.IDRECETA + " = ?";

        String[] parametros = new String[] { id+"" };
        c = gri.getCursor(where,parametros);

        while(c.moveToNext()) {
            ingredientes.add(c.getLong(c.getColumnIndex("idingrediente")));
            cantidad.add(c.getLong(c.getColumnIndex("cantidad")));
        }

        gri.close();

        gi.open();
        int j=0;
        for(long i : ingredientes){
            where = Contrato.TablaIngredientes._ID + " = ? ";
            parametros = new String[] { i+"" };
            c = gi.getCursor(where,parametros);
           while (c.moveToNext()){

               out += c.getString(1)+" Cantidad: "+cantidad.get(j)+" g  \r\n";
           }
            j++;
        }
        gi.close();
        return out;

    }
    public String getCategoria(long idcat){

        GestorCategoria gc = new GestorCategoria(this);
        Cursor c;
        String cat="";

        gc.open();
        String where = Contrato.TablaCategoria._ID + " = ?";
        String[] parametros = new String[] { idcat+"" };
        c = gc.getCursor(where,parametros);

        while(c.moveToNext()){
            cat=c.getString(c.getColumnIndex(Contrato.TablaCategoria.NOMBRE));
        }

        gc.close();

        return cat;
    }
    public void update(View v){
        Intent i = new Intent(this, Update.class);

        r=new Receta();
        r.setNombre(getIntent().getExtras().getString("nombre"));
        r.setFoto(getIntent().getExtras().getString("foto"));
        r.setId_categoria(getIntent().getExtras().getLong("cat"));
        r.setInstrucciones(getIntent().getExtras().getString("instrucciones"));
        r.setId(getIntent().getExtras().getLong("id"));

        i.putExtra("nombre", r.getNombre());
        i.putExtra("foto", r.getFoto());
        i.putExtra("instrucciones", r.getInstrucciones());
        i.putExtra("cat", r.getId_categoria());
        i.putExtra("id", r.getId());

        startActivity(i);
    }
}
