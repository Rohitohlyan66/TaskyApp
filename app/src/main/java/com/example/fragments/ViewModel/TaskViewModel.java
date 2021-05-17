package com.example.fragments.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fragments.Repository.TaskRepository;
import com.example.fragments.localdatabase.TaskEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TaskViewModel extends AndroidViewModel {

    TaskRepository repository;
    LiveData<List<TaskEntity>> allTask;


    public TaskViewModel(@NonNull Application application) throws ExecutionException, InterruptedException {
        super(application);
        repository = new TaskRepository(application);
        allTask = repository.getAllTask();
    }

    public void insertTask(TaskEntity task) {
        repository.insertTask(task);
    }

    public void deleteTask(TaskEntity task) {
        repository.deleteTask(task);
    }

    public void updateTask(TaskEntity task) {
        repository.updateTask(task);
    }

    public void deleteAllTask() {
        repository.deleteAllTask();
    }

    public void deleteDoneTask() {
        repository.deleteDoneTask();
    }

    public void deleteUndoneTask() {
        repository.deleteUnDoneTask();
    }

    public LiveData<List<TaskEntity>> getAllTask() {
        return allTask;
    }


}
