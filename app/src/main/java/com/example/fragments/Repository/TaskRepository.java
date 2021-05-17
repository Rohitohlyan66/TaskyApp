package com.example.fragments.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.fragments.localdatabase.TaskDAO;
import com.example.fragments.localdatabase.TaskDatabase;
import com.example.fragments.localdatabase.TaskEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TaskRepository {

    private TaskDAO dao;
    private LiveData<List<TaskEntity>> allTask;

    public TaskRepository(Application application) {
        TaskDatabase database = TaskDatabase.getInstance(application);
        dao = database.dao();
        allTask = dao.getAllTask();
    }

    // Observing live data
    public LiveData<List<TaskEntity>> getAllTask() {
        return allTask;
    }


    public void insertTask(TaskEntity task) {
        new InsertTaskAsyncTask(dao).execute(task);
    }

    public void deleteTask(TaskEntity task) {
        new DeleteTaskAsyncTask(dao).execute(task);
    }

    public void updateTask(TaskEntity task) {
        new UpdateTaskAsyncTask(dao).execute(task);
    }

    public void deleteAllTask() {
        new DeleteAllTaskAsyncTask(dao).execute();
    }

    public void deleteDoneTask() {
        new DeleteDoneTaskAsyncTask(dao).execute();
    }

    public void deleteUnDoneTask() {
        new DeleteUndoneTaskAsyncTask(dao).execute();
    }



    /*
     * Async tasks for doing work in background
     * Room does not allow us to work on main thread as it may crash our app
     */

    private static class InsertTaskAsyncTask extends AsyncTask<TaskEntity, Void, Void> {
        TaskDAO dao;

        public InsertTaskAsyncTask(TaskDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TaskEntity... taskEntities) {
            dao.insertTask(taskEntities[0]);
            return null;
        }
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<TaskEntity, Void, Void> {
        TaskDAO dao;

        public DeleteTaskAsyncTask(TaskDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TaskEntity... taskEntities) {
            dao.deleteTask(taskEntities[0]);
            return null;
        }
    }

    private static class UpdateTaskAsyncTask extends AsyncTask<TaskEntity, Void, Void> {
        TaskDAO dao;

        public UpdateTaskAsyncTask(TaskDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TaskEntity... taskEntities) {
            dao.updateTask(taskEntities[0]);
            return null;
        }
    }

    private static class DeleteAllTaskAsyncTask extends AsyncTask<Void, Void, Void> {
        TaskDAO dao;

        public DeleteAllTaskAsyncTask(TaskDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllTask();
            return null;
        }
    }

    private static class DeleteDoneTaskAsyncTask extends AsyncTask<Void, Void, Void> {
        TaskDAO dao;

        public DeleteDoneTaskAsyncTask(TaskDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteDoneTask();
            return null;
        }
    }

    private static class DeleteUndoneTaskAsyncTask extends AsyncTask<Void, Void, Void> {
        TaskDAO dao;

        public DeleteUndoneTaskAsyncTask(TaskDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteUnDoneTask();
            return null;
        }
    }

}
