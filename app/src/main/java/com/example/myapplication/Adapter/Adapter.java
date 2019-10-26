package com.example.myapplication.Adapter;

//import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        private Context mcontext;
        private List<Usuario> mUsers;


        public Adapter(Context mContext, List<Usuario> mUsers){
        this.mUsers = mUsers;
        this.mcontext = mContext;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_list,parent,false);
        return new Adapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        /*final User user = mUsers.get(position);
                holder.username.setText(user.getUsername());
                if (user.getImgUrl() == null){
                holder.profile_img.setImageResource(R.mipmap.ic_launcher);
                }else {
                Glide.with(mcontext).load(user.getImgUrl()).into(holder.profile_img);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent intent = new Intent(mcontext, MessageActivity.class);
        intent.putExtra("userid",user.getId());
        mcontext.startActivity(intent);
        }
        });*/

        }

    @Override
    public int getItemCount() {
            return mUsers.size();
            }

    public class ViewHolder extends RecyclerView.ViewHolder{

    public TextView username;
    public ImageView profile_img;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        username = itemView.findViewById(R.id.username);
        profile_img = itemView.findViewById(R.id.profile_img);



    }
}
}
