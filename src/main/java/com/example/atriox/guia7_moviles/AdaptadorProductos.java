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

public class AdaptadorProductos extends ArrayAdapter<Productos>{

    public AdaptadorProductos(Context context, List<Productos> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obteniendo el dato
        Productos productos = getItem(position);
        //TODO inicializando el layout correspondiente al item (Categoria)
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_producto, parent, false);
        }
        TextView lblNombre = (TextView) convertView.findViewById(R.id.TxtNomPro);
        TextView lblId_pro = (TextView) convertView.findViewById(R.id.TxtIdPro);
        TextView lvlDescripcion = convertView.findViewById(R.id.TxtDesPro);

        lblNombre.setText(productos.getNombre());
        lblId_pro.setText(productos.getId_producto());
        lvlDescripcion.setText(productos.getDescripcion());

        return convertView;
    }

}