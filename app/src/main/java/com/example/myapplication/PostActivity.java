package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myapplication.Adapter.AdapterPosts;

public class PostActivity extends AppCompatActivity {

    RecyclerView recyclerViewPosts;
    AdapterPosts adapterPosts;
    ImageButton btnEliminar, btnEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        inicializar();
        recyclerViewPosts=findViewById(R.id.Recycler_view2);


        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(PostActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_eliminar,null);
                Button btnSi = mView.findViewById(R.id.btnSi);
                Button btnNo = mView.findViewById(R.id.btnNo);

                alert.setView(mView);

                final AlertDialog alertDialog = alert.create();

                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();

                    }
                });

                btnSi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }

        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this,EditarActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

    }

    private void inicializar() {
        recyclerViewPosts=findViewById(R.id.Recycler_view2);
        btnEditar = recyclerViewPosts.findViewById(R.id.btnEditar);
        btnEliminar = recyclerViewPosts.findViewById(R.id.btnEliminar);
        adapterPosts= new AdapterPosts(MainActivity.arrayListPosts, this);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewPosts.setHasFixedSize(true);
        recyclerViewPosts.setAdapter(adapterPosts);





    }
}
