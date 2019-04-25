package net.unadeca.galeria.Activities.activities;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.unadeca.galeria.Activities.database.models.Imagenes;
import net.unadeca.galeria.R;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Imagenes> {

    private List<Imagenes> dataSet;
    Context mContext;
    CoordinatorLayout view;

    // View lookup cache
    private static class ViewHolder {
        TextView titulo;
        TextView descripcion;
        TextView comentario;
        ImageView imagen;
    }

    public CustomAdapter(List<Imagenes> data, Context context, CoordinatorLayout l) {
        super(context, R.layout.imagen, data);
        this.dataSet = data;
        this.mContext = context;
        this.view = l;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Imagenes dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.imagen, parent, false);
            viewHolder.imagen = convertView.findViewById(R.id.imagen);
            viewHolder.titulo = convertView.findViewById(R.id.titulo);
            viewHolder.descripcion = convertView.findViewById(R.id.descripcion);
            viewHolder.comentario = convertView.findViewById(R.id.comentarios);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        //Glide.with(mContext).load("https://i.pinimg.com/originals/75/00/30/7500302b182070761e3ac8269a8c4443.jpg").into(viewHolder.imagen); //Carga una imagen de un url
        Glide.with(mContext).load(dataModel.imagen).error(Glide.with(mContext).load(R.drawable.ic_add_a_photo_black_24dp)).fitCenter().into(viewHolder.imagen); //Carga imagen dimanica
        viewHolder.titulo.setText(dataModel.titulo);
        viewHolder.descripcion.setText(dataModel.descripcion);
        viewHolder.comentario.setText(dataModel.imagen); //Cargar url En la imagen


        // Return the completed view to render on screen
        return convertView;
    }

}