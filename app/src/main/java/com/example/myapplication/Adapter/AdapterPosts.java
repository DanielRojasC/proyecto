package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Clases.Posts;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AdapterPosts extends RecyclerView.Adapter<AdapterPosts.Holder> {

    ArrayList<com.example.myapplication.Clases.Posts> Posts;

    private AdapterPosts.OnClickListener mlistener;
    public interface OnClickListener
    {
        void itemClick(int position, View itemView);
    }


    public AdapterPosts(ArrayList<Posts> Posts, MainActivity mainActivity)
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
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

        public void fijarDatos(Posts Posts)
        {

            //AQUI SETEAS LOS TEXTVIEW

        }
    }
}