package com.example.taskmaster;

import androidx.room.*;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract TaskDao taskDao();
}
