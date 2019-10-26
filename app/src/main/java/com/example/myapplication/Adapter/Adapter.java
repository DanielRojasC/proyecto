package com.example.myapplication.Adapter;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter  extends RecyclerView.Adapter<Adapter.Holder> {

    ArrayList<Usuarios> Usuarios;

    private OnClickListener mlistener;
    public interface OnClickListener
    {
        void itemClick(int position, View itemView);
    }


    public Adapter(ArrayList<Usuarios> Usuarios, MainActivity mainActivity)
    {
        this.Usuarios = Usuarios;
    }

    public void setMlistener(OnClickListener mlistener)
    {
        this.mlistener= mlistener;
    }


    @NonNull
    @Override
    public Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new Adapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.Holder holder, int position) {


        holder.fijarDatos(Usuarios.get(position));

    }


    @Override
    public int getItemCount()
    {
        return Usuarios.size();
    }



    public class Holder extends RecyclerView.ViewHolder
    {


        TextView txtUsername;
        TextView txtEmail;

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

            txtUsername  =itemView.findViewById(R.id.username);
            txtEmail=itemView.findViewById(R.id.descripcion);



        }

        public void fijarDatos(Usuarios Usuarios)
        {

            txtUsername.setText(Usuarios.getUsername());
            txtEmail.setText(Usuarios.getEmail());


        }
    }
}