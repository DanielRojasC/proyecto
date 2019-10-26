package com.example.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

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
import com.example.myapplication.Adapter.Usuarios;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    RequestQueue queue;
    ArrayList<Usuarios> arrayListUsuarios = new ArrayList<>();
    Gson gson= new Gson();
    RecyclerView recyclerViewUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        inicializar();
        descargarDatos();
        llenaRecycler();

    }

    private void llenaRecycler() {

        Adapter adapter = new Adapter(arrayListUsuarios, this);

        recyclerViewUsuario.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewUsuario.setHasFixedSize(true);
        recyclerViewUsuario.setAdapter(adapter);
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

    private String quitarSaltos(String response) {
        return response.replace("\n" +
                "  ","");
    }

    private void inicializar() {
        queue= Volley.newRequestQueue(this);
        recyclerViewUsuario=findViewById(R.id.Recycler_view);
    }
}
