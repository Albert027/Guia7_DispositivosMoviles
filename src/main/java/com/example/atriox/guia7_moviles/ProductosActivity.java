package com.example.atriox.guia7_moviles;

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

public class ProductosActivity extends AppCompatActivity {

    private DataBase dataBase;
    private AdaptadorProductos adaptadorProductos;
    private ListView listView;
    private TextView lblId_Pro;
    private EditText txtNombre_Pro, txtDescripcion_Pro;
    private Button btnGuardar, btnEliminar;
    //lista de datos (categoria)
    private ArrayList<Productos> lstProducto;
    //sirve para manejar la eliminacion
    private Productos producto_temp=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        lblId_Pro = (TextView) findViewById(R.id.IdPro);
        txtNombre_Pro = (EditText) findViewById(R.id.Producto);
        txtDescripcion_Pro = findViewById(R.id.Descripcion);
        btnGuardar = (Button)   findViewById(R.id.BtnGuardarPro);
        btnEliminar = (Button)   findViewById(R.id.BtnEliminarPro);
        listView = (ListView) findViewById(R.id.LstProductos);

        dataBase = new DataBase(this);
        lstProducto = dataBase.getArrayProducto(dataBase.getCursorProducto(PrincipalActivity.ID_CATEGORIA));
        if(lstProducto == null) {
            lstProducto = new ArrayList<>();
        }
        adaptadorProductos = new AdaptadorProductos(this,lstProducto);
        listView.setAdapter(adaptadorProductos);

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seleccionar(lstProducto.get(position));
            }
        });
        //limpiando
        limpiar();
    }
    private void guardar(){
        Productos productos = new Productos(lblId_Pro.getText().toString(),txtNombre_Pro.getText().toString(),txtDescripcion_Pro.getText().toString(),PrincipalActivity.ID_CATEGORIA);
        producto_temp=null;
        if(dataBase.guardar_O_ActualizarProducto(productos)){
            Toast.makeText(this,"Se guardo categoria",Toast.LENGTH_SHORT).show();
            //TODO limpiar los que existen y agregar los nuevos
            lstProducto.clear();
            lstProducto.addAll(dataBase.getArrayProducto(dataBase.getCursorProducto(PrincipalActivity.ID_CATEGORIA)));

            adaptadorProductos.notifyDataSetChanged();
            limpiar();
        }else{
            Toast.makeText(this,"Ocurrio un error al guardar",Toast.LENGTH_SHORT).show();
        }
    }
    private void eliminar(){
        if(producto_temp!=null){
            dataBase.borrarProducto(producto_temp.getId_producto());
            lstProducto.remove(producto_temp);
            adaptadorProductos.notifyDataSetChanged();
            producto_temp=null;
            Toast.makeText(this,"Se elimino producto",Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }
    private void seleccionar(Productos productos){
        producto_temp = productos;
        lblId_Pro.setText(producto_temp.getId_producto());
        txtNombre_Pro.setText(producto_temp.getNombre());
        txtDescripcion_Pro.setText(producto_temp.getDescripcion());
    }
    private void limpiar(){
        lblId_Pro.setText(null);
        txtNombre_Pro.setText(null);
        txtDescripcion_Pro.setText(null);
    }
}
