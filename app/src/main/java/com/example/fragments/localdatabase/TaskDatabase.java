package com.example.fragments.localdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TaskEntity.class}, exportSchema = false, version = 1)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDAO dao();

    private static final String DATABASE_NAME = "MyTask";
    private static TaskDatabase INSTANCE;

    public static synchronized TaskDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, TaskDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }


}
