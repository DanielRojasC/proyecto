package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Clases.Posts;
import com.example.myapplication.MainActivity;
import com.example.myapplication.PostActivity;
import com.example.myapplication.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterPosts extends RecyclerView.Adapter<AdapterPosts.Holder> {

    ArrayList<com.example.myapplication.Clases.Posts> Posts;

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


        holder.fijarDatos(Posts.get(position));

    }


    @Override
    public int getItemCount()
    {
        return Posts.size();
    }



    public class Holder extends RecyclerView.ViewHolder
    {


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


            txtTitulo=itemView.findViewById(R.id.username);
            txtDescripcion=itemView.findViewById(R.id.descripcion);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);



        }

        public void fijarDatos(Posts Posts)
        {

            txtTitulo.setText("Título: "+Posts.getTitle());
            txtDescripcion.setText("Contenido: \n"+Posts.getBody());

        }
    }
}