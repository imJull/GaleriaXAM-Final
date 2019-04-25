package net.unadeca.galeria.Activities.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import net.unadeca.galeria.Activities.database.models.Imagenes;
import net.unadeca.galeria.Activities.subclase.ImagenViewHolder;
import net.unadeca.galeria.R;

import java.util.List;

public class CustomAdapterRecycler extends RecyclerView.Adapter<ImagenViewHolder> {

    private List<Imagenes> dataSet;
    Context mContext;
    CoordinatorLayout view;
    private LayoutInflater layoutInflater;


    public CustomAdapterRecycler(List<Imagenes> data, Context context, CoordinatorLayout l) {
        this.dataSet = data;
        this.mContext = context;
        this.view = l;
        this.layoutInflater = LayoutInflater.from(context);

    }


    @NonNull
    @Override
    public ImagenViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista = layoutInflater.inflate(R.layout.imagen,viewGroup, false);
        ImagenViewHolder imagen = new ImagenViewHolder(vista);
        return imagen;
    }

    @Override
    public void onBindViewHolder(@NonNull ImagenViewHolder viewHolder, int i) {
        final Imagenes dataModel = dataSet.get(i);
        viewHolder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirImagen(dataModel);
            }
        });

        Glide.with(mContext).load(dataModel.imagen).error(Glide.with(mContext).load(R.drawable.ic_add_a_photo_black_24dp)).fitCenter().into(viewHolder.imagen);
        viewHolder.titulo.setText(dataModel.titulo);
        viewHolder.descripcion.setText(dataModel.descripcion);
        viewHolder.comentario.setText(dataModel.imagen); //Cargar url En la imagen

    }

    private void abrirImagen(Imagenes imagenes){
        try{
            Intent abrirImagen = new Intent(mContext, DetalleActivity.class);
            abrirImagen.putExtra("imagen", imagenes.id);
            mContext.startActivity(abrirImagen);
        }catch (Exception e){
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_INDEFINITE).show();
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}