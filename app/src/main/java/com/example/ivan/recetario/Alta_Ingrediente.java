package com.example.ivan.recetario;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ivan.recetario.adaptadores.AdaptadorIngredientes;
import com.example.ivan.recetario.adaptadores.CursorAdaptador;
import com.example.ivan.recetario.clasespojo.Ingrediente;
import com.example.ivan.recetario.clasespojo.Receta;
import com.example.ivan.recetario.clasespojo.RecetaIngrediente;
import com.example.ivan.recetario.gestores.GestorIngrediente;
import com.example.ivan.recetario.gestores.GestorReceta;

public class Alta_Ingrediente extends AppCompatActivity {
    private EditText etNom;
    private Button bt;
    private GestorIngrediente gp;
    private Toast tostada;
    private AdaptadorIngredientes adp;
    private Cursor c;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta__ingrediente);
        etNom = (EditText)this.findViewById(R.id.etNomIngr);
        bt = (Button)findViewById(R.id.btañ);
        lv= (ListView)findViewById(R.id.lvAlta);

        gp = new GestorIngrediente(this);
        gp.open();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alta__ingrediente, menu);
        return true;
    }
    @Override
    protected void onResume() {

        super.onResume();
    }
    @Override
    protected void onPause(){

        gp.close();
        super.onPause();
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
    private void tostada(String i){
        Toast.makeText(this, i, Toast.LENGTH_LONG).show();
    }
    public void addIngre(View v){
        Ingrediente p = new Ingrediente();
        p.setNombre(etNom.getText().toString().trim());
        if(!p.getNombre().isEmpty()) {
            long r = gp.insert(p);
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
    }
}
