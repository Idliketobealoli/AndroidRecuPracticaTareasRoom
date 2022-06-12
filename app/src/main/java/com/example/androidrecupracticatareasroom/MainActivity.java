package com.example.androidrecupracticatareasroom;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidrecupracticatareasroom.data.Nota;
import com.example.androidrecupracticatareasroom.data.RoomDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NotaItemClickListener {
    RecyclerView recycler;
    FloatingActionButton fab;
    List<Nota> notas;
    LinearLayoutManager manager;
    RoomDB db;
    NotaRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.recycler);
        fab = findViewById(R.id.fab);

        db = RoomDB.getInstance(this);
        notas = db.mainDao().findAll();

        manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);

        adapter = new NotaRecyclerAdapter(this, notas, this);
        recycler.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogNota dialog = new DialogNota();
                dialog.show(getSupportFragmentManager(), "add_nota");
                notas.clear();
                notas.addAll(db.mainDao().findAll());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onListItemClick(int position) {
        Nota nota = notas.get(position);

        db.mainDao().delete(nota);

        notas.clear();
        notas.addAll(db.mainDao().findAll());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLongClick() {
        db.mainDao().reset(notas);
        notas.clear();
        notas.addAll(db.mainDao().findAll());
        adapter.notifyDataSetChanged();
    }
}