package com.example.ivan.recetario;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ivan.recetario.adaptadores.AdaptadorIngredientes;
import com.example.ivan.recetario.adaptadores.AdaptadorUtensilios;
import com.example.ivan.recetario.adaptadores.CursorAdaptador;
import com.example.ivan.recetario.clasespojo.Ingrediente;
import com.example.ivan.recetario.clasespojo.Receta;
import com.example.ivan.recetario.clasespojo.RecetaIngrediente;
import com.example.ivan.recetario.gestores.GestorIngrediente;
import com.example.ivan.recetario.gestores.GestorReceta;
import com.example.ivan.recetario.gestores.GestorRecetaIngrediente;
import com.example.ivan.recetario.gestores.GestorUtensilio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 11/24/2015.
 */
public class Update extends AppCompatActivity {
    private static final int REQUEST_IMAGE_GET = 1;
    private android.widget.TextView tvTexto;
    private GestorReceta gp;
    private GestorIngrediente gpi;
    private GestorRecetaIngrediente gri;
    private GestorUtensilio gpu;
    private CursorAdaptador adp;
    private CursorAdaptador adpu;
    private EditText etNombre, etInstruccion, etCat;
    private Button btAdd;
    private ImageView img;
    private Uri uriImg;
    private Cursor c,c2;
    private ListView lv, lvu;
    private RadioGroup rg;
    private String ingr;
    private List<Cursor> listaingre;
    private RadioButton rbCarne, rbPescado, rbPostre;
    private Receta r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta);
        init();
    }

    private void init() {
        this.btAdd = (Button) findViewById(R.id.btAdd);
        this.etNombre = (EditText) findViewById(R.id.etNombre);
        this.etInstruccion = (EditText) findViewById(R.id.etInstruccion);
        this.img = (ImageView) findViewById(R.id.iv);
        this.lv = (ListView) findViewById(R.id.lvAlta);
        this.rg = (RadioGroup) findViewById(R.id.radiogroup);
        this.rbCarne = (RadioButton) findViewById(R.id.rbCarne);
        this.rbPescado = (RadioButton) findViewById(R.id.rbPescado);
        this.rbPostre = (RadioButton) findViewById(R.id.rbPostre);
        this.lvu = (ListView) findViewById(R.id.lvUtensilios);

        btAdd = (Button) findViewById(R.id.btElegir);
        listaingre = new ArrayList<>();

        gp = new GestorReceta(this);
        gpi = new GestorIngrediente(this);
        gri = new GestorRecetaIngrediente(this);
        gpu = new GestorUtensilio(this);

        r=new Receta();
        r.setNombre(getIntent().getExtras().getString("nombre"));
        r.setFoto(getIntent().getExtras().getString("foto"));
        r.setId_categoria(getIntent().getExtras().getLong("cat"));
        r.setInstrucciones(getIntent().getExtras().getString("instrucciones"));
        r.setId(getIntent().getExtras().getLong("id"));

        img.setImageURI(Uri.parse(r.getFoto()));
        etNombre.setText(r.getNombre());
        etInstruccion.setText(r.getInstrucciones());



        gpi.open();

        c = gpi.getCursor();

        adp = new AdaptadorIngredientes(this, c);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tostada(c.getString(1) + " ha sido añadido");
                listaingre.add(c);

            }
        });

        gpi.close();
        gpu.open();

        c2 = gpu.getCursor();
        adpu = new AdaptadorUtensilios(this, c2);
        lvu.setAdapter(adpu);
        lvu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


        gpu.close();
    }

    @Override
    protected void onResume() {

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

    public void add(View v) {
        long r = 0;
        Receta p = new Receta();
        p.setNombre(etNombre.getText().toString().trim());
        p.setInstrucciones(etInstruccion.getText().toString().trim());
        p.setId_categoria(comprobarCat());
        p.setFoto(uriImg.getPath());
        Log.v("añ2", uriImg.getPath() + "");

        gp.open();

        if (!p.getNombre().isEmpty()) {

            r = gp.update(p);
                Intent i = new Intent();
                Bundle bundle = new Bundle();
                bundle.putLong("id", r);
                i.putExtras(bundle);
                setResult(Activity.RESULT_OK, i);
                finish();

        } else {
            tostada("El Nombre es obligatorio");
        }

        gp.close();
        añadiringr(listaingre, r);
    }

    public void añadiringr(List<Cursor> c, long id) {
        gri.open();
        Ingrediente i = new Ingrediente();
        for (Cursor cc : c) {
            i.set(cc);
            ingr += "- " + i.getNombre() + "\r\n";
            RecetaIngrediente ri = new RecetaIngrediente();
            ri.setCantidad(1);
            ri.setIdingrediente(i.getId());
            ri.setIdreceta(id);
            gri.insert(ri);
        }
        gri.close();
    }

    private void tostada(int i) {
        tostada(i + "");
    }

    private void tostada(String i) {
        Toast.makeText(this, i, Toast.LENGTH_LONG).show();
    }


    private void view() {
        List<Receta> l = gp.select();
        tvTexto.setText("");
        for (Receta p : l) {
            tvTexto.append(p.toString());
        }
    }

//    public void borrar(View v){
//        int id = Integer.parseInt(etId.getText().toString());
//        int r = gestor.delete(id);
//        tostada(r);
//        view();
//    }

    public void editar(View v) {
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
        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_GET) {
            Uri uri = data.getData();
            if (uri != null) {
                uriImg = uri;
                String convierte = uriImg.toString();
                Uri uri2 = Uri.parse(convierte);
                Log.v("añ2 definitivo", uri2.getPath() + "");
                img.setImageURI(uri2);
            }
        }
    }

    public void addIn(View v) {
        Intent i = new Intent(this, Alta_Ingrediente.class);
        startActivityForResult(i, 1);
    }

    public long comprobarCat() {

        int op = rg.getCheckedRadioButtonId();
        switch (op) {
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
    public void marcarCat(long cat){
        switch ((int)cat){
            case 1:
                rbCarne.setChecked(true);
                break;
            case 2:
                rbPescado.setChecked(true);
                break;
            case 3:
                rbPostre.setChecked(true);
                break;

            default:
                Toast.makeText(this, "No se pudo seleccionar la categoría, hazlo manualmente", Toast.LENGTH_SHORT).show();
        }
    }
}
