package com.example.ivan.recetario;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.ivan.recetario.adaptadores.AdaptadorIngredientes;
import com.example.ivan.recetario.adaptadores.AdaptadorUtensilios;
import com.example.ivan.recetario.adaptadores.CursorAdaptador;
import com.example.ivan.recetario.bd.Contrato;
import com.example.ivan.recetario.clasespojo.Ingrediente;
import com.example.ivan.recetario.clasespojo.Receta;
import com.example.ivan.recetario.clasespojo.RecetaIngrediente;
import com.example.ivan.recetario.clasespojo.RecetaUtensilio;
import com.example.ivan.recetario.clasespojo.Utensilio;
import com.example.ivan.recetario.gestores.GestorCategoria;
import com.example.ivan.recetario.gestores.GestorIngrediente;
import com.example.ivan.recetario.gestores.GestorReceta;
import com.example.ivan.recetario.gestores.GestorRecetaIngrediente;
import com.example.ivan.recetario.gestores.GestorRecetaUtensilio;
import com.example.ivan.recetario.gestores.GestorUtensilio;
import com.example.ivan.recetario.otros.Dialogo;
import com.example.ivan.recetario.otros.OnDialogoListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Alta extends AppCompatActivity {

    private static final int REQUEST_IMAGE_GET = 1 ;
    private android.widget.TextView tvTexto;
    private GestorReceta gp;
    private GestorIngrediente gpi;
    private GestorRecetaIngrediente gri;
    private GestorUtensilio gpu;
    private GestorRecetaUtensilio gru;
    private CursorAdaptador adp;
    private CursorAdaptador adpu;
    private EditText etNombre, etInstruccion, etCat;
    private Button btAdd;
    private ImageView img;
    private Uri uriImg;
    private Cursor c, c2;
    private ListView lv,lvu;
    private RadioGroup rg;
    private String ingr;
    private List<Ingrediente> listaingre;
    private List<Utensilio> listauten;
    private List<Long> cantidadingre, cantidaduten;
    private RadioButton rbCarne, rbPescado, rbPostre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta);
        init();
    }

    private void init(){
        this.btAdd = (Button) findViewById(R.id.btAdd);
        this.etNombre = (EditText) findViewById(R.id.etNombre);
        this.etInstruccion = (EditText)findViewById(R.id.etInstruccion);
        this.img= (ImageView)findViewById(R.id.iv);
        this.lv = (ListView)findViewById(R.id.lvAlta);
        this.rg = (RadioGroup)findViewById(R.id.radiogroup);
        this.rbCarne = (RadioButton)findViewById(R.id.rbCarne);
        this.rbPescado =(RadioButton)findViewById(R.id.rbPescado);
        this.rbPostre = (RadioButton)findViewById(R.id.rbPostre);
        this.lvu = (ListView)findViewById(R.id.lvUtensilios);

        btAdd = (Button)findViewById(R.id.btElegir);
        listaingre= new ArrayList<>();
        cantidadingre= new ArrayList<>();
        listauten =  new ArrayList<>();
        cantidaduten = new ArrayList<>();

        gp = new GestorReceta(this);
        gpi = new GestorIngrediente(this);
        gri = new GestorRecetaIngrediente(this);
        gpu = new GestorUtensilio(this);
        gru= new GestorRecetaUtensilio(this);



    }

    @Override
    protected void onResume() {
        gpi.open();

        this.c = gpi.getCursor();
        Log.v("añ2 df", c.toString() + "");
        adp = new AdaptadorIngredientes(this, c);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dialogCantidad(c);
            }
        });

        gpi.close();
        gpu.open();

        c2= gpu.getCursor();
        adpu = new AdaptadorUtensilios(this,c2);
        lvu.setAdapter(adpu);
        lvu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogUtensilio(c2);
            }
        });


        gpu.close();
        super.onResume();
        Log.v("APLICACION", "Resume Alta Open");
    }

    @Override
    protected void onPause() {
        super.onPause();
        gpi.close();
        gp.close();
        gri.close();
        Log.v("APLICACION", "Resume Alta Close");
    }

    public void add(View v){
        long r=0;
        Receta p = new Receta();
        p.setNombre(etNombre.getText().toString().trim());
        p.setInstrucciones(etInstruccion.getText().toString().trim());
        p.setId_categoria(comprobarCat());
        p.setFoto(uriImg.toString());
        Log.v("añ2", uriImg.getPath() + "");

        gp.open();

        if(!p.getNombre().isEmpty()) {

            r = gp.insert(p);
            if (r>0) {
                Intent i = new Intent();
                Bundle bundle = new Bundle();
                bundle.putLong("id", r);
                i.putExtras(bundle);
                setResult(Activity.RESULT_OK, i);
                finish();
            }else {
                tostada("No se ha podido insertar");
            }
        } else{
            tostada("El Nombre es obligatorio");
        }

        gp.close();
        añadiringr(listaingre, r);
        añadiruten(listauten,r);
    }
   public void añadiringr(List<Ingrediente> ce, long id){
       gri.open();
        Ingrediente i = new Ingrediente();
        int j=0;
        for(Ingrediente cc : ce) {

                ingr += "- " + cc.getNombre() + "\r\n";
                RecetaIngrediente ri = new RecetaIngrediente();
                ri.setCantidad(cantidadingre.get(j));
                ri.setIdingrediente(cc.getId());
                ri.setIdreceta(id);
                gri.insert(ri);
                j++;
        }
       gri.close();
    }
    public void añadiruten(List<Utensilio> ce, long id){
        gru.open();
        Utensilio i = new Utensilio();
        int j=0;
        for(Utensilio cc : ce) {
            ingr += "- " + cc.getNombre() + "\r\n";
            RecetaUtensilio ri = new RecetaUtensilio();
            ri.setIdutensilio(cc.getId());
            ri.setIdreceta(id);
            gru.insert(ri);
            j++;
        }
        gru.close();
    }
    public void dialogCantidad(Cursor cc){
        c=cc;
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_cantidad, null);
        final EditText etCantidadD;

        etCantidadD = (EditText) vista.findViewById(R.id.etCantidadD);
        final Ingrediente i = new Ingrediente();
        i.set(c);
        alert.setView(vista);
        alert.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        listaingre.add(i);
                        cantidadingre.add(Long.parseLong(String.valueOf(etCantidadD.getText())));
                        tostada(i.getNombre() + " ha sido añadido");
                    }
                });

        alert.setView(vista);
        alert.setNegativeButton("Cancelar", null);
        alert.show();

    }
    public void dialogUtensilio(Cursor cc){
        c=cc;
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_cantidad, null);
        final EditText etCantidadD;

        etCantidadD = (EditText) vista.findViewById(R.id.etCantidadD);
        final Utensilio u = new Utensilio();
        u.set(c);
        alert.setView(vista);
        alert.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        listauten.add(u);
                        cantidaduten.add(Long.parseLong(String.valueOf(etCantidadD.getText())));
                        tostada(u.getNombre() + " ha sido añadido");
                    }
                });

        alert.setView(vista);
        alert.setNegativeButton("Cancelar", null);
        alert.show();

    }
    private void tostada(int i){
        tostada(i + "");
    }
    private void tostada(String i){
        Toast.makeText(this,i,Toast.LENGTH_LONG).show();
    }


    private void view(){
        List<Receta> l = gp.select();
        tvTexto.setText("");
        for(Receta p: l){
            tvTexto.append(p.toString());
        }
    }

    public void editar(View v){
        Receta p = new Receta();
        p.setNombre(etNombre.getText().toString().trim());
        p.setInstrucciones(etInstruccion.getText().toString().trim());


        int r = gp.update(p);
        tostada(r);
        view();
    }
    public void buscarFoto(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==REQUEST_IMAGE_GET){
            Uri uri = data.getData();
            if(uri!=null){
                File f = new File(getImagePath(uri));
                Uri uri3= Uri.fromFile(f);
                uriImg=uri3;
                img.setImageURI(uri3);
            }
        }
    }
    public void addIn(View v) {
        Intent i=new Intent(this,Alta_Ingrediente.class);
        startActivityForResult(i, 1);
    }
    public long comprobarCat(){

        int op = rg.getCheckedRadioButtonId();
        switch (op){
            case R.id.rbCarne:
                return 1;


            case R.id.rbPescado:
                return 2;



            case R.id.rbPostre:
                return 3;



            default:
                Toast.makeText(this, "La categoría no fue seleccionada, receta sin categoría(0)", Toast.LENGTH_SHORT).show();
        }
        return 0;
    }
    public String getImagePath(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }
}