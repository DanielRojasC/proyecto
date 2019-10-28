package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Adapter.AdapterPosts;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {


    RecyclerView recyclerViewPosts;
    AdapterPosts adapterPosts;
    ImageButton btnEliminar, btnEditar;
    Dialog dialogAgregarPost;
    public  static String agregarEditar;

    //****** SE CREA VARIABLE DE COLA DE SOLICITUDES QUE SE INICIALIZA EN EL METODO INICIALIZAR() ******//
    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        inicializar();
        recyclerViewPosts=findViewById(R.id.Recycler_view2);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //****** SE CREA EL DIALOG PARA CREAR POSTS Y SE DECLARAN LOS INPUT ETC******//

                TextView txtTitulo;
                Button btnGuardar;
                final TextInputEditText txtTituloPost;
                final TextInputEditText txtContenidoPost;

                dialogAgregarPost.setContentView(R.layout.activity_agregar);
                dialogAgregarPost.setCancelable(true);
                dialogAgregarPost.create();
                dialogAgregarPost.show();

                txtTitulo=dialogAgregarPost.findViewById(R.id.txtTitulo);
                btnGuardar=dialogAgregarPost.findViewById(R.id.btnGuardar);

                txtTituloPost=dialogAgregarPost.findViewById(R.id.titulo);
                txtContenidoPost=dialogAgregarPost.findViewById(R.id.contenido);


                //****** SE CREA UNA VARIABLE ESTÁTICA "agregarEditar" PARA VALIDAR Y CAMBIARLE EL TÍTULO AL DIALOG SEGÚN SEA INSERCION O ACTUALIZACION******//


                agregarEditar="AGREGAR";

                if(PostActivity.agregarEditar=="AGREGAR")
                {
                    txtTitulo.setText("AGREGAR POST");
                }
                else
                {
                    txtTitulo.setText("EDITAR POST");
                }

                //****** EVENTO CLICK DE GUARDAR EN EL FORM DE INSERCION DE POSTS ******//

                btnGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //****** SE CREA LA URL ******//

                        String url = "https://jsonplaceholder.typicode.com/post";

                        //****** SE CREA UNA SOLICITUD DE COLA DONDE SE LE MANDA EL MÉTODO "POST", LA URL Y SE CREAN LOS EVENTOS "RESPONSE" Y "ON ERROR RESPONSE" ******//

                        StringRequest requestCrearPost = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                //****** SI LA SOLICITUD SE COMPLETA CORRECTAMENTE Y EL RESPONSE ES "OK", SE MUESTRA UN TOAST DE CONFIRMACIÓN Y SE RE-CREA LA ACTIVIDAD******//
                                Toast.makeText(PostActivity.this, "POST CREADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                                recreate();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                //****** SI LA SOLICITU NO SE COMPLETA CORRECTAMENTE, SE MUESTRA UN TOAST CON EL ERROR ******//
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

                            }
                        }) {

                            //****** SE MAPEAN Y SE RETORNAN LOS PARÁMETROS MANDÁNDOLE EL NOMBRE (COMO LO PIDE EL SERVIDOR) Y EL VALOR ******//
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("title", txtTituloPost.getText().toString());
                                params.put("body", txtContenidoPost.getText().toString());
                                params.put("userId", MainActivity.idUsuario);

                                return params;
                            }
                        };
                        //****** SE AGREGA A LA COLA DE SOLICITUDES LA SOLICITUD "requestCrearPost" ******//
                        queue.add(requestCrearPost);
                    }

                });

            }

        });








    }
    //****** SE INICIALIZA TODO ******//
    private void inicializar() {
        recyclerViewPosts=findViewById(R.id.Recycler_view2);
        btnEditar = recyclerViewPosts.findViewById(R.id.btnEditar);
        btnEliminar = recyclerViewPosts.findViewById(R.id.btnEliminar);
        adapterPosts= new AdapterPosts(MainActivity.arrayListPosts, this);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewPosts.setHasFixedSize(true);
        recyclerViewPosts.setAdapter(adapterPosts);
        queue= Volley.newRequestQueue(getApplicationContext());
        dialogAgregarPost=new Dialog(this);

    }
}
