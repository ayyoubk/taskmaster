package com.example.taskmaster;

import androidx.room.*;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    List<Task> getAllTasks();

    @Insert
    void insertAll(Task... tasks);
}
