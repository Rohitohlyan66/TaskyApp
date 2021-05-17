package com.example.fragments.localdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDAO {

    @Insert
    void insertTask(TaskEntity task);

    @Delete
    void deleteTask(TaskEntity task);

    @Update
    void updateTask(TaskEntity task);

    @Query("DELETE FROM task_table")
    void deleteAllTask();

    @Query("SELECT * FROM task_table")
    LiveData<List<TaskEntity>> getAllTask();

    @Query("DELETE FROM task_table WHERE isDone = 1")
    void deleteDoneTask();

    @Query("DELETE FROM task_table WHERE isDone = 0")
    void deleteUnDoneTask();

}
