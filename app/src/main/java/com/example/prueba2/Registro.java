package com.example.prueba2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

public class Registro extends AppCompatActivity {
    EditText nombre,apellido,nombreUsuario,contraseña;
    Button registro,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.registro);
        nombre=(EditText) findViewById(R.id.etNombre);
        apellido=(EditText) findViewById(R.id.etApellido);
        nombreUsuario=(EditText) findViewById(R.id.etRegistroUsuario);
        contraseña=(EditText) findViewById(R.id.etRegistroContraseña);
        registro=(Button) findViewById(R.id.btnRegistro);
        login=(Button) findViewById(R.id.btnLogin);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQLOpenHelper admin=new AdminSQLOpenHelper(getApplicationContext(),
                        "administracion",
                        null,
                        1);
                SQLiteDatabase sqLiteDatabase=admin.getWritableDatabase();
                String nom=nombre.getText().toString();
                String ape=apellido.getText().toString();
                String user=nombreUsuario.getText().toString();
                String cont=contraseña.getText().toString();
                if(!nom.isEmpty() && !ape.isEmpty() && !user.isEmpty() && !cont.isEmpty() ) {
                    ContentValues registro=new ContentValues();
                    registro.put("nombre", nom);
                    registro.put("apellido", ape);
                    registro.put("usuario", user);
                    registro.put("contraseña", cont);
                    sqLiteDatabase.insert("usuarios", null, registro);
                    sqLiteDatabase.close();
                    nombre.setText("");
                    apellido.setText("");
                    nombreUsuario.setText("");
                    contraseña.setText("");
                    Toast.makeText(getApplicationContext(),"Usuario Creado Correctamente", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Debe Ingresar la Informacion Solicitada", Toast.LENGTH_LONG).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(Registro.this, MainActivity.class);

                startActivity(I);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}