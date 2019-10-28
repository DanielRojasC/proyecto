package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AgregarActivity extends AppCompatActivity {

    TextView txtTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        inicializar();


    }

    private void inicializar() {

        txtTitulo=findViewById(R.id.txtTitulo);

    }
}
