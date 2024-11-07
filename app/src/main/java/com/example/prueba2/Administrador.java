package com.example.prueba2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Administrador extends AppCompatActivity {
    EditText Codigo, NombreProducto, PrecioProducto;
    Button Ingresar, Buscar, Modificar, Eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.administrador);
        Codigo=findViewById(R.id.etCodigoProducto);
        NombreProducto=findViewById(R.id.etNombreProducto);
        PrecioProducto=findViewById(R.id.etPrecioProducto);
        Ingresar=findViewById(R.id.btnIngresarProducto);
        Buscar=findViewById(R.id.btnBuscarProducto);
        Modificar=findViewById(R.id.btnModificarProducto);
        Eliminar=findViewById(R.id.btnEliminarProducto);

        Ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQLOpenHelper admin=new AdminSQLOpenHelper(getApplicationContext(),
                        "administracion",
                        null,
                        1);
                SQLiteDatabase sqLiteDatabase=admin.getWritableDatabase();
                String cod=Codigo.getText().toString();
                String nomPro=NombreProducto.getText().toString();
                String prePro=PrecioProducto.getText().toString();
                if(!cod.isEmpty() && !nomPro.isEmpty() && !prePro.isEmpty()) {
                    ContentValues registro=new ContentValues();
                    registro.put("codigo", cod);
                    registro.put("nombre_producto", nomPro);
                    registro.put("precio", prePro);
                    sqLiteDatabase.insert("productos", null, registro);
                    sqLiteDatabase.close();
                    Codigo.setText("");
                    NombreProducto.setText("");
                    PrecioProducto.setText("");
                    Toast.makeText(getApplicationContext(),"Producto Ingresado", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Ingrese la informacion solicitada", Toast.LENGTH_LONG).show();
                }

            }
        });

        Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQLOpenHelper admin=new AdminSQLOpenHelper(getApplicationContext(),
                        "administracion",
                        null,
                        1);
                SQLiteDatabase sqLiteDatabase=admin.getWritableDatabase();
                String cod=Codigo.getText().toString();
                if(!cod.isEmpty()){
                    Cursor fila=sqLiteDatabase.rawQuery("SELECT nombre_producto, precio from productos where codigo="+cod,null);
                    if(fila.moveToFirst()){
                        NombreProducto.setText(fila.getString(0));
                        PrecioProducto.setText(fila.getString(1));
                        sqLiteDatabase.close();
                    }else{
                        Toast.makeText(getApplicationContext(),"Codigo de Producto No Encontado",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Debe Ingresar el Codigo de Producto a Buscar",Toast.LENGTH_LONG).show();
                    sqLiteDatabase.close();
                }
            }
        });

        Modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQLOpenHelper admin=new AdminSQLOpenHelper(getApplicationContext(),
                        "administracion",
                        null,
                        1);
                SQLiteDatabase sqLiteDatabase=admin.getWritableDatabase();
                String cod=Codigo.getText().toString();
                String nomPro=NombreProducto.getText().toString();
                String prePro=PrecioProducto.getText().toString();
                if(!cod.isEmpty() && !nomPro.isEmpty() && !prePro.isEmpty()){
                    ContentValues registro=new ContentValues();
                    registro.put("codigo", cod);
                    registro.put("nombre_producto", nomPro);
                    registro.put("precio", prePro);
                    int cantidad=sqLiteDatabase.update("productos",
                            registro,"codigo=" + cod, null);
                    sqLiteDatabase.close();
                    if(cantidad == 1) {
                        Toast.makeText(getApplicationContext(),"Producto Modificado", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Producto No Encontrado", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Debe Ingresar el Codigo del Producto a Modificar", Toast.LENGTH_LONG).show();
                    sqLiteDatabase.close();
                }
            }
        });

        Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQLOpenHelper admin=new AdminSQLOpenHelper(getApplicationContext(),
                        "administracion",
                        null,
                        1);
                SQLiteDatabase sqLiteDatabase=admin.getWritableDatabase();
                String cod=Codigo.getText().toString();
                if(!cod.isEmpty()) {
                    int cantidad = sqLiteDatabase.delete("productos",
                            "codigo=" + cod,null);
                    sqLiteDatabase.close();
                    Codigo.setText("");
                    NombreProducto.setText("");
                    PrecioProducto.setText("");
                    if(cantidad == 1) {
                        Toast.makeText(getApplicationContext(),"Producto Eliminado", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Producto No Existente", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Debe Ingresar el Codigo del Producto a Eliminar", Toast.LENGTH_LONG).show();
                    sqLiteDatabase.close();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}