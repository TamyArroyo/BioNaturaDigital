package com.example.prueba2;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    EditText usuario,contraseña;
    Button ingresar,registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        usuario=(EditText) findViewById(R.id.etUsuario);
        contraseña=(EditText) findViewById(R.id.etContraseña);
        ingresar=(Button) findViewById(R.id.btnIngresar);
        registro=(Button) findViewById(R.id.btnRegistrar);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQLOpenHelper admin=new AdminSQLOpenHelper(getApplicationContext(),
                        "administracion",
                        null,
                        1);
                SQLiteDatabase sqLiteDatabase=admin.getWritableDatabase();
                String user=usuario.getText().toString();
                String cont=contraseña.getText().toString();
                if(user.equals("ADMIN") && cont.equals("ADMIN123")){
                    Intent I = new Intent(MainActivity.this, Administrador.class);
                    startActivity(I);
                } else if (!user.isEmpty() && !cont.isEmpty()) {
                    Cursor fila=sqLiteDatabase.rawQuery("SELECT * FROM usuarios WHERE usuario = ? AND contraseña = ?", new String[]{user, cont});
                    if(fila.moveToFirst()){
                        Intent I = new Intent(MainActivity.this, Principal.class);
                        I.putExtra("clvUsuario",user);
                        startActivity(I);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Usuario o contraseña Incorrectos", Toast.LENGTH_LONG).show();
                    }
                    fila.close();
                }else{
                    Toast.makeText(getApplicationContext(), "Por favor, Ingrese todos los campos", Toast.LENGTH_LONG).show();
                }
                sqLiteDatabase.close();
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(MainActivity.this, Registro.class);

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