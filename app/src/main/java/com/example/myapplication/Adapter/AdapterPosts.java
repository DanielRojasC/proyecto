package com.example.myapplication.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.AgregarActivity;
import com.example.myapplication.Clases.Posts;
import com.example.myapplication.MainActivity;
import com.example.myapplication.PostActivity;
import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterPosts extends RecyclerView.Adapter<AdapterPosts.Holder> {

    ArrayList<com.example.myapplication.Clases.Posts> Posts;

    //****** SE CREA UN DIALOG Y UNA COLA DE SOLICITUDES CON EL CONTEXT ESTÁTICO QUE SE CREO EN EL MAIN ACTIVITY ******//
    Dialog dialogPosts;
    RequestQueue queue= Volley.newRequestQueue(MainActivity.context);

    private AdapterPosts.OnClickListener mlistener;
    public interface OnClickListener
    {
        void itemClick(int position, View itemView);
    }


    public AdapterPosts(ArrayList<Posts> Posts, PostActivity mainActivity)
    {
        this.Posts = Posts;
    }

    public void setMlistener(AdapterPosts.OnClickListener mlistener)
    {
        this.mlistener= mlistener;
    }


    @NonNull
    @Override
    public AdapterPosts.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_detalle, parent, false);
        return new AdapterPosts.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPosts.Holder holder, int position) {

        holder.relativeLayout.setAnimation(AnimationUtils.loadAnimation(MainActivity.context,R.anim.fade_scale_animation));

        holder.fijarDatos(Posts.get(position));

    }


    @Override
    public int getItemCount()
    {
        return Posts.size();
    }



    public class Holder extends RecyclerView.ViewHolder
    {

        RelativeLayout relativeLayout;
        TextView txtTitulo;
        TextView txtContenido;
        TextView txtDescripcion;
        ImageButton btnEliminar, btnEditar;

        public Holder(final View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (mlistener!=null)
                    {
                        int position = getLayoutPosition();
                        if (position!=RecyclerView.NO_POSITION)
                        {
                            mlistener.itemClick(position,itemView);
                        }
                    }
                }
            });

            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            txtTitulo=itemView.findViewById(R.id.username);
            txtDescripcion=itemView.findViewById(R.id.descripcion);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);



        }

        public void fijarDatos(Posts Posts)
        {

            //****** SE INICIALIZA EL DIALOG  ******//
            dialogPosts= new Dialog(itemView.getRootView().getContext());
            txtTitulo.setText("Título: "+Posts.getTitle());
            txtDescripcion.setText("Contenido: \n"+Posts.getBody());

            //****** SE CREA EL EVENTO CLICK DEL BOTÓN DE EDITAR POST ******//
            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //****** SE DECLARAN LOS INPUT, TEXTVIEW Y BOTONES ******//
                    TextView txtTitulo;
                    Button btnGuardar;
                    final TextInputEditText txtTituloPost;
                    final TextInputEditText txtContenidoPost;

                    //****** SE INICIALIZA EL DIALOG SETEANDO EL XML QUE SE MUESTRA (contentView) Y OTROS PARÁMETROS ******//
                    dialogPosts.setContentView(R.layout.activity_agregar);
                    dialogPosts.setCancelable(true);
                    dialogPosts.create();
                    dialogPosts.show();

                    //****** SE INICIALIZAN LOS CONTROLES ******//
                    txtTitulo=dialogPosts.findViewById(R.id.txtTitulo);
                    btnGuardar=dialogPosts.findViewById(R.id.btnGuardar);

                    txtTituloPost=dialogPosts.findViewById(R.id.titulo);
                    txtContenidoPost=dialogPosts.findViewById(R.id.contenido);

                    //****** SE SETEAN LOS VALORES DE TITULO Y CONTENIDO PARA QUE SE CARGUEN EN EL DIALOG DE EDICION. ******//
                    //****** LOS VALORES SE TRAEN DEL ARRAY DE POSTS ESTÁTICO DEL MAIN ACTIVITY Y SE OBTIENE LA POSICION DONDE SE DA CLICK EN EL ADAPTADOR (getAdapterPosition) ******//
                    //****** Y SE OBTIENE EL DATO CON EL GET******//
                    txtTituloPost.setText(MainActivity.arrayListPosts.get(getAdapterPosition()).getTitle());
                    txtContenidoPost.setText(MainActivity.arrayListPosts.get(getAdapterPosition()).getBody());

                    //****** SE VALIDA PARA EL TÍTULO DEL DIALOG ******//
                    PostActivity.agregarEditar="EDITAR";

                    if(PostActivity.agregarEditar=="AGREGAR")
                    {
                        txtTitulo.setText("AGREGAR POST");
                    }
                    else
                    {
                        txtTitulo.setText("EDITAR POST");

                    }

                    //****** EVENTO CLICK DEL BOTÓN DE GUARDAR EDICIÓN ******//
                    btnGuardar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {

                            //****** SE CREA LA URL, SE CONCATENA EL ID DEL USUARIO Y SE REALIZA EL MISMO PROCESO DE SOLICITUD QUE SE HIZO PARA CREAR EL POST******//
                            //****** SOLICITUD, MAPEO DE PARÁMETROS, RESPONSE Y ON ERROR RESPONSE - SOLO SE CAMBIA EL MÉTODO A PUT PARA ACTUALIZACION ******//
                            String url = "https://jsonplaceholder.typicode.com/posts/"+MainActivity.arrayListPosts.get(getAdapterPosition()).getId();
                            StringRequest requestCrearPost = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(v.getRootView().getContext(), "POST ACTUALIZADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                                    dialogPosts.cancel();

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(v.getRootView().getContext(), error.toString(), Toast.LENGTH_SHORT).show();

                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("id",MainActivity.arrayListPosts.get(getAdapterPosition()).getId());
                                    params.put("title", txtTituloPost.getText().toString());
                                    params.put("body", txtContenidoPost.getText().toString());
                                    params.put("userId", MainActivity.idUsuario);

                                    return params;
                                }
                            };
                            //****** SE AÑADE LA SOLICITUD A LA COLA ******//
                            queue.add(requestCrearPost);
                        }

                    });


                }
            });

            //****** EVENTO CLICK DEL BOTÓN DE ELIMINAR ******//
            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //****** SE CREA UN ALERT DIALOG PARA CONFIRMAR LA ELIMINACION******//
                    //****** EL CONTEXTO SE OBTIENE CON LA VISTA RAIZ (getRootView()) (LO VI EN INTERNET XD) ******//
                    AlertDialog.Builder alerta= new AlertDialog.Builder(v.getRootView().getContext());

                    //****** SE CREAN LOS PARAMETROS DE TITULO Y MENSAJE DEL ALERT DIALOG******//
                    alerta.setTitle("Eliminar Post");
                    alerta.setMessage("¿Desea eliminar el post?");

                    //****** BOTÓN CONFIRMAR CON SU TEXT Y EVENTO CLICK******//
                    alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //****** SE CREA LA URL, SE CONCATENA EL ID DEL USUARIO Y SE REALIZA EL MISMO PROCESO DE SOLICITUD QUE SE HIZO PARA CREAR EL POST******//
                            //****** SOLICITUD, MAPEO DE PARÁMETROS, RESPONSE Y ON ERROR RESPONSE - SOLO SE CAMBIA EL MÉTODO A DELETE PARA ELIMINACION ******//
                            String url="https://jsonplaceholder.typicode.com/posts/"+MainActivity.arrayListPosts.get(getAdapterPosition()).getId().toString();
                            StringRequest requestEliminarPost=new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(MainActivity.context, "EL POST SE HA ELIMINADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(MainActivity.context, error.toString(), Toast.LENGTH_SHORT).show();

                                }
                            });

                            //****** SE AÑADE LA SOLICITUD A LA COLA ******//
                            queue.add(requestEliminarPost);
                        }
                    });

                    //****** BOTON CANCELAR DEL ALERT DIALOG CON TEXT Y EVENTO CLICK ******//
                    alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //****** SE CIERRA EL DIALOG ******//
                            dialog.cancel();
                        }
                    });


                    //****** SE CREA Y SE MUESTRA EL ALERT DIALOG ******//
                    alerta.create();
                    alerta.show();


                }
            });

        }
    }
}