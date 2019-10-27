package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.Adapter.AdapterPosts;

public class PostActivity extends AppCompatActivity {

    RecyclerView recyclerViewPosts;
    AdapterPosts adapterPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        inicializar();
    }

    private void inicializar() {
        recyclerViewPosts=findViewById(R.id.Recycler_view2);
        adapterPosts= new AdapterPosts(MainActivity.arrayListPosts, this);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewPosts.setHasFixedSize(true);
        recyclerViewPosts.setAdapter(adapterPosts);

    }
}
