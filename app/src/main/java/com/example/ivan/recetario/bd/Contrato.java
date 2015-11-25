package com.example.ivan.recetario.bd;

import android.provider.BaseColumns;

/**
 * Created by ivan on 11/18/2015.
 */
public class Contrato {

    private Contrato (){
    }

    public static abstract class TablaReceta implements BaseColumns {
        public static final String TABLA = "receta";
        public static final String NOMBRE = "nombre";
        public static final String FOTO = "foto";
        public static final String INSTRUCCIONES = "instrucciones";
        public static final String ID_CATEGORIA = "id_categoria";
    }

    public static abstract class TablaIngredientes implements BaseColumns {
        public static final String TABLA = "ingrediente";
        public static final String NOMBRE = "nombre";
    }

    public static abstract class TablaRecetaIngredientes implements BaseColumns {
        public static final String TABLA = "receta_ingrediente";
        public static final String IDRECETA = "idreceta";
        public static final String IDINGREDIENTE = "idingrediente";
        public static final String CANTIDAD = "cantidad";
    }
    public static abstract class TablaCategoria implements BaseColumns {
        public static final String TABLA= "categoria";
        public static final String NOMBRE = "nombre";
    }
    public static abstract class TablaUtensilio implements BaseColumns {
        public static final String TABLA= "utensilio";
        public static final String NOMBRE = "nombre";
    }
    public static abstract class TablaRecetaUtensilio implements BaseColumns {
        public static final String TABLA = "receta_utensilio";
        public static final String IDRECETA = "idreceta";
        public static final String IDUTENSILIO = "idingrediente";
    }
}
