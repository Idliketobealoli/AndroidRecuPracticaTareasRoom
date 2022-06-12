package com.example.androidrecupracticatareasroom.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Nota.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB db;
    private static final String DATABASE_NAME = "database";
    public abstract NotaDao mainDao();

    public synchronized static RoomDB getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }
}
