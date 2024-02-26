package com.example.android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Datos> listaDatos = new ArrayList<>();
    List<String> listaNombres = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void GuardaEnLista(View v) {
        EditText txtNombre = findViewById(R.id.txtNombre);
        Spinner lstNombres = findViewById(R.id.lstNombres);

        listaDatos.add(new Datos(txtNombre.getText().toString()));

        Toast.makeText(this,"Agregado a la lista",Toast.LENGTH_SHORT).show();

        // Agrego nombre al spinner
        listaNombres.add(txtNombre.getText().toString());
        ArrayAdapter<String> llenaSpinner = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,listaNombres);
        llenaSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstNombres.setAdapter(llenaSpinner);

        // Limpio los campos
        txtNombre.setText("");
    }

    public void GuardaEnArchivo(View v) {

        File ruta = getApplicationContext().getFilesDir();
        // Éste es el nombre del archivo
        String nombreArch = "archivo.tpo";

        try {
            FileOutputStream escribirArch = new FileOutputStream(new File(ruta,nombreArch));
            ObjectOutputStream streamArch = new ObjectOutputStream(escribirArch);
            streamArch.writeObject(listaDatos);
            streamArch.close();
        } catch (Exception e) {
            e.printStackTrace();        // Si hay error, que muestre datos sobre el fallo
        }
    }

    public void LeeDelArchivo(View v) {
        // Obtengo referencia a los controles de la pantalla que voy a usar
        Spinner lstNombres = findViewById(R.id.lstNombres);

        // El objeto File con la ruta donde almacenarlo
        File ruta = getApplicationContext().getFilesDir();
        // Éste es el nombre del archivo
        String nombreArch = "archivo.tpo";

        // Borro la lista y borro lo que está en el spinner (el adapter será el arreglo vacío)
        listaNombres.clear();
        ArrayAdapter<String> llenaSpinner = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,listaNombres);
        lstNombres.setAdapter(llenaSpinner);

        // Leo los datos del archivo
        try {
            // FileInputStream me permite abri el archivo para leer de él
            FileInputStream leeArch = new FileInputStream (new File(ruta,nombreArch));
            // El ObjectInputStream me pemite traducir el arreglo de bytes al Arraylist
            ObjectInputStream streamArch = new ObjectInputStream (leeArch);
            // Leo todo y lleno la lista
            listaDatos = (ArrayList<Datos>) streamArch.readObject();
            // Cierro el stream
            streamArch.close();

            // Lleno la lista de nombres (strings) con los nombres de la lista de datos
            listaNombres.clear();
            for (int i=0;i<listaDatos.size();i++) {
                listaNombres.add(listaDatos.get(i).getNombre());
            }
            // Lleno el Spinner de la nueva lista
            llenaSpinner = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item,listaNombres);
            llenaSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            lstNombres.setAdapter(llenaSpinner);
        } catch (Exception e) {
            e.printStackTrace();        // Si hay error, que muestre datos sobre el fallo
        }
    }

    // Método que busca en la lista de datos y muestra información del que
    // se seleccionó en el Spinner
    public void MuestraDatos(View v) {
        // Obtengo referencia a los controles de la pantalla que voy a usar
        Spinner lstNombres = findViewById(R.id.lstNombres);

        // Creo un Alert Builder (ventana estándar que puedo usar)
        AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        constructor.setTitle("Almacena");       // Pongo título a la ventana
        constructor.setPositiveButton("Aceptar",null);      // Agrego un botón

        // Si se seleccionó algo en el spinner, le sigo
        long index = lstNombres.getSelectedItemId();
        if (index > -1) {
            // Pongo mensaje a la ventana que voy a mostrar
            constructor.setMessage("Nombre: " + listaDatos.get((int) index).getNombre()
                /*+ " - Lugar: " + listaDatos.get((int) index).getLugar()
                + " - Edad: " + listaDatos.get((int) index).getEdad()*/);     // El texto en la ventana
        } else {
            constructor.setMessage("Debe seleccionar un nombre de la lista");
        }

        // Creo y muestro la ventana
        AlertDialog ventanaMensaje = constructor.create();
        ventanaMensaje.show();
    }
}