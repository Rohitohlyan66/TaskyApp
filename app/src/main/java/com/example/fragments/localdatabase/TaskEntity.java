package com.example.fragments.localdatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "task_table")
public class TaskEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "key")
    int id;

    @ColumnInfo(name = "task")
    String task;

    @ColumnInfo(name = "isDone")
    boolean isDone;

    @Ignore
    public TaskEntity(String task, boolean isDone) {
        this.task = task;
        this.isDone = isDone;
    }

    public TaskEntity(int id, String task, boolean isDone) {
        this.id = id;
        this.task = task;
        this.isDone = isDone;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
