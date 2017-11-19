package com.example.atriox.guia7_moviles;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Oscar Campos 19/11/2017.
 */

public class AdaptadorCategorias extends ArrayAdapter<Categorias>{

    public AdaptadorCategorias(Context context, List<Categorias> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obteniendo el dato
        Categorias categorias = getItem(position);
        //TODO inicializando el layout correspondiente al item (Categoria)
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_categoria, parent, false);
        }
        TextView lblNombre = (TextView) convertView.findViewById(R.id.TxtNomCat);
        TextView lblId_cat = (TextView) convertView.findViewById(R.id.TxtIdCat);

        lblNombre.setText(categorias.getNombre());
        lblId_cat.setText(categorias.getId_categoria());

        return convertView;
    }

}
