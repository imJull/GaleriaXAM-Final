package net.unadeca.galeria.Activities.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.raizlabs.android.dbflow.sql.language.Case;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.unadeca.galeria.Activities.database.models.Imagenes;
import net.unadeca.galeria.R;

import java.util.List;

import static net.unadeca.galeria.Activities.database.models.Imagenes_Table.descripcion;
import static net.unadeca.galeria.Activities.database.models.Imagenes_Table.imagen;

public class MainActivity extends AppCompatActivity {

    private RecyclerView lista;
    private CoordinatorLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lista = findViewById(R.id.lista);
        lista.setLayoutManager(new LinearLayoutManager(this));
        view = findViewById(R.id.coordinador);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogo();
                establecerAdaptador();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

            }
        });

        establecerAdaptador();

        fab2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                borrarImagenes();
                establecerAdaptador();
                Snackbar.make(view, "Eliminado!!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void testBaseDatos() {
        Delete.table(Imagenes.class);
        Imagenes imagen;
        for (int a = 0; a < 6; a++) {
            imagen = new Imagenes();
            if (a <= 0){
            imagen.imagen = "https://i.pinimg.com/originals/75/00/30/7500302b182070761e3ac8269a8c4443.jpg";
            imagen.titulo = "AntiHero";}
            else if (a == 1){
            imagen.imagen = "https://steamuserimages-a.akamaihd.net/ugc/785162250415551722/518EEB89F420FCB94D620D6E500C127D482B878B/";
            imagen.titulo = "Santa Cruz";}
            else if (a == 2){
                imagen.imagen ="https://sohimages.com/images/images_soh/wrldindsflameboy1-2.jpg";
                imagen.titulo = "World Industries";}
                else if ( a == 3){
                    imagen.imagen = "https://acclaimmag.com/wp-content/uploads/2012/07/163801_500_375.jpeg";
                    imagen.titulo = "Girl Skateboards";}
                    else {imagen.imagen = "findViewById(R.drawable.ic_add_a_photo_black_24dp)";
                    imagen.titulo = "Desconocido";}


            imagen.descripcion = "Empresa top en la industria "; //+ (a + 1);
            //imagen.titulo = "AntiHero " + (a + 1);
            imagen.save();


        }
    }

    //Adaptador
    private void establecerAdaptador() {
        lista.setAdapter(new CustomAdapterRecycler(getImagenes(), this, view));
    }

    //Lista de imagenes
    private List<Imagenes> getImagenes() {
        return SQLite.select().from(Imagenes.class).queryList();
    }

    private void borrarImagenes(){
        Delete.table(Imagenes.class);
    }

    public void mostrarDialogo() {
        //Inflador
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View agreFormulario = layoutInflater.inflate(R.layout.agreform, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(agreFormulario);

        //VARIABLES
        final TextInputLayout titulo = agreFormulario.findViewById(R.id.frTitulo);
        final TextInputLayout comentario = agreFormulario.findViewById(R.id.frcomentario);
        final TextInputLayout descripcion = agreFormulario.findViewById(R.id.frDescripcion);
        //final TextInputLayout imagen = agreFormulario.findViewById(R.id.frImagen);


        builder.setMessage("Complete la informacion")
                .setTitle("Añadido")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            validacionBD(titulo, descripcion, comentario);
                            guardarBD(titulo, descripcion);



                        } catch (Exception e) {
                            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialogo = builder.create();
        dialogo.show();

    }

    private void validacionBD(TextInputLayout t, TextInputLayout d, TextInputLayout c) throws Exception{
        if(t.getEditText().getText().toString().isEmpty()) throw  new Exception("Se ocupa el Titulo");
        if(d.getEditText().getText().toString().isEmpty()) throw  new Exception("Se ocupa el Descripcion");
        //if(c.getEditText().getText().toString().isEmpty()) throw  new Exception("Se ocupa la Comentario");

        establecerAdaptador();


    }

    private void guardarBD (TextInputLayout t, TextInputLayout d){
        Imagenes img = new Imagenes();
        img.titulo = t.getEditText().getText().toString();
        img.descripcion = d.getEditText().getText().toString();
        img.imagen = "https://sohimages.com/images/images_soh/wrldindsflameboy1-2.jpg";
        img.save();

        Snackbar.make(view, "Guardado con éxito", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        establecerAdaptador();

    }


}
