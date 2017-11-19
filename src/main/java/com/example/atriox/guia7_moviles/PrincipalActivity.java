package com.example.atriox.guia7_moviles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PrincipalActivity extends AppCompatActivity {

    public static String ID_CATEGORIA = "id";
    private DataBase dataBase;
    private AdaptadorCategorias adaptadorCategorias;
    private ListView listView;
    private TextView lblId_Cat;
    private EditText txtNombre_Cat;
    private Button btnGuardar,btnAcceder,btnEliminar;
    //lista de datos (categoria)
    private ArrayList<Categorias> lstCategoria;
    //sirve para manejar la eliminacion
    private Categorias categoria_temp=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        lblId_Cat = (TextView) findViewById(R.id.IdCat);
        txtNombre_Cat = (EditText) findViewById(R.id.Categoria);
        btnGuardar = (Button)   findViewById(R.id.BtnGuardar);
        btnEliminar = (Button)   findViewById(R.id.BtnEliminar);
        btnAcceder = findViewById(R.id.BtnAcceder);
        listView = (ListView) findViewById(R.id.LstCategorias);

        dataBase = new DataBase(this);
        lstCategoria = dataBase.getArrayCategoria(dataBase.getCursorCategoria());
        if(lstCategoria==null) {
            lstCategoria = new ArrayList<>();
        }
        adaptadorCategorias = new AdaptadorCategorias(this,lstCategoria);
        listView.setAdapter(adaptadorCategorias);
        //listeners
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });

        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lblId_Cat.getText().toString() == ""){
                    Toast.makeText(PrincipalActivity.this,"Seleccione una Categoria", Toast.LENGTH_SHORT).show();
                }
                else {
                    ID_CATEGORIA = lblId_Cat.getText().toString();
                    Intent intent = new Intent(PrincipalActivity.this, ProductosActivity.class);
                    startActivity(intent);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seleccionar(lstCategoria.get(position));
            }
        });
        //limpiando
        limpiar();
    }
    private void guardar(){
        Categorias categoria = new Categorias(lblId_Cat.getText().toString(),txtNombre_Cat.getText().toString());
        categoria_temp=null;
        if(dataBase.guardar_O_ActualizarCategoria(categoria)){
            Toast.makeText(this,"Se guardo categoria",Toast.LENGTH_SHORT).show();
            //TODO limpiar los que existen y agregar los nuevos
            lstCategoria.clear();
            lstCategoria.addAll(dataBase.getArrayCategoria(
                    dataBase.getCursorCategoria()
            ));

            adaptadorCategorias.notifyDataSetChanged();
            limpiar();
        }else{
            Toast.makeText(this,"Ocurrio un error al guardar",Toast.LENGTH_SHORT).show();
        }
    }
    private void eliminar(){
        if(categoria_temp!=null){
            dataBase.borrarCategoria(categoria_temp.getId_categoria());
            lstCategoria.remove(categoria_temp);
            adaptadorCategorias.notifyDataSetChanged();
            categoria_temp=null;
            Toast.makeText(this,"Se elimino categoria",Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }
    private void seleccionar(Categorias categoria){
        categoria_temp = categoria;
        lblId_Cat.setText(categoria_temp.getId_categoria());
        txtNombre_Cat.setText(categoria_temp.getNombre());
    }
    private void limpiar(){
        lblId_Cat.setText(null);
        txtNombre_Cat.setText(null);
    }
}
