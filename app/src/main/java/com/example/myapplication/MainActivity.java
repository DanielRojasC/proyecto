package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Adapter.Adapter;
import com.example.myapplication.Clases.Posts;
import com.example.myapplication.Clases.Usuarios;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    RequestQueue queue;

    //****** SE COLOCA EL ARRAY DE LA INFORMACIÓN DEL USUARIO COMO ESTÁTICO PARA ACCEDER A ÉL ******//
    static public ArrayList<Usuarios> arrayListUsuarios = new ArrayList<>();
    Gson gson= new Gson();
    RecyclerView recyclerViewUsuario;
    static public ArrayList<Posts> arrayListPosts= new ArrayList<>();

    //****** SE CREA UNA VARIABLE ESTÁTICA "idUsuario" PARA ACCEDER A ELLA SEGÚN SE NECESITE EN LAS FUNCIONES DE CRUD ******//
    static public String idUsuario;

    //****** SE CREA UN CONTEXT ESTÁTICO PARA ACCEDER A ÉL DESDE EL ADAPTADOR ******//
    public static Context context;

    static public String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        inicializar();
        descargarDatos();


    }

    private void descargarDatos() {
        String url="https://jsonplaceholder.typicode.com/users";
        StringRequest requestInicio= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //response="[{\"id\":1,\"name\":\"Leanne Graham\",\"username\":\"Bret\",\"email\":\"Sincere@april.biz\",\"address\":{\"street\":\"Kulas Light\",\"suite\":\"Apt. 556\",\"city\":\"Gwenborough\",\"zipcode\":\"92998-3874\"}}]";
                response=quitarSaltos(response);

                Type type = new TypeToken<List<Usuarios>>() {
                }.getType();
                arrayListUsuarios = gson.fromJson(response, type);
                llenaRecycler();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("NO LLEGA", error.toString());

            }
        });

        queue.add(requestInicio);


    }

    private void llenaRecycler() {

        Adapter adapter = new Adapter(arrayListUsuarios, this);

        recyclerViewUsuario.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewUsuario.setHasFixedSize(true);
        recyclerViewUsuario.setAdapter(adapter);

        adapter.setMlistener(new Adapter.OnClickListener() {
            @Override
            public void itemClick(final int position, View itemView) {
                String id=arrayListUsuarios.get(position).getId();
                String url="https://jsonplaceholder.typicode.com/posts?userId="+id;
                StringRequest requestPosts= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Type type = new TypeToken<List<Posts>>() {
                        }.getType();
                        arrayListPosts = gson.fromJson(response, type);
                        userName=arrayListUsuarios.get(position).getUsername();

                        //****** SE SETEA LA VARIABLE ESTÁTICA "idUsuario" CON EL CLICK SEGÚN LA POSICION DEL ADAPTADOR ******//
                        idUsuario=arrayListUsuarios.get(position).getId().toString();


                        Intent intent= new Intent(getApplicationContext(), PostActivity.class);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(requestPosts);

            }
        });
    }

    private String quitarSaltos(String response) {
        return response.replace("\n" +
                "  ","");
    }

    private void inicializar() {
        queue= Volley.newRequestQueue(this);
        recyclerViewUsuario=findViewById(R.id.Recycler_view);
        context=getApplicationContext();
    }
}
