package com.example.ivan.recetario;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ivan.recetario.adaptadores.CursorAdaptador;
import com.example.ivan.recetario.clasespojo.Receta;
import com.example.ivan.recetario.gestores.GestorReceta;
import com.example.ivan.recetario.otros.Dialogo;
import com.example.ivan.recetario.otros.OnDialogoListener;

public class MainActivity extends AppCompatActivity {


    private static final int REQUEST_IMAGE_GET =1 ;
    private GestorReceta gp;
    private CursorAdaptador adp;
    private ListView lv;
    private Cursor c;
    private final static int ALTA=1;
    private final static int A_RECETA=2;
    private ImageView iv;
    private Button btElegir;

    /***************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gp = new GestorReceta(this);
        lv = (ListView)findViewById(R.id.lv);
        init();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        long id = item.getItemId();
        AdapterView.AdapterContextMenuInfo vistaInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int posicion = vistaInfo.position;

        if (id == R.id.menu_borrar) {
            borrar(c);
            return true;
        }
        return super.onContextItemSelected(item);
    }
    public void borrar(Cursor cu){
        gp.open();
        Receta r = new Receta();
        r.set(cu);
        gp.delete(r);

        this.c = gp.getCursor();
        adp = new CursorAdaptador(this, c);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                change(c);
            }
        });
        gp.close();
    }
    @Override
    protected void onResume() {
        gp.open();
        c = gp.getCursor();
        adp = new CursorAdaptador(this, c);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                change(c);
            }
        });
        gp.close();
        super.onResume();
    }
    public void change(Cursor c){
        Receta r = new Receta();
        r.set(c);
        Intent i=new Intent(this,A_Receta.class);
        i.putExtra("nombre",r.getNombre());
        i.putExtra("foto",r.getFoto());
        i.putExtra("instrucciones", r.getInstrucciones());
        i.putExtra("cat", r.getId_categoria());
        i.putExtra("id", r.getId());
        startActivity(i);
    }

    @Override
    protected void onPause() {
        gp.close();
        super.onPause();
    }


    /***************************************************/

    private void tostada(int i){
        tostada(i + "");
    }

    private void tostada(String i){
        Toast.makeText(this, i, Toast.LENGTH_LONG).show();
    }

    public void insertar(View v){
        Intent i=new Intent(this,Alta.class);
        startActivityForResult(i, ALTA);
    }

    private void init(){
        registerForContextMenu(lv);
        iv= (ImageView)findViewById(R.id.iv);
    }




}
