package com.example.androidrecupracticatareasroom.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface NotaDao {
    @Insert(onConflict = REPLACE)
    void insert(Nota nota);

    @Delete
    void delete(Nota nota);

    @Delete
    void reset(List<Nota> notas);

    @Query("SELECT * FROM nota")
    List<Nota> findAll();
}
