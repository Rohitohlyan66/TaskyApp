package com.example.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.fragments.ViewModel.TaskViewModel;
import com.example.fragments.adapter.TaskAdapter;
import com.example.fragments.localdatabase.TaskEntity;
import com.example.fragments.ui.CustomDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TaskAdapter adapter;
    RecyclerView recyclerView;
    TaskViewModel viewModel;
    public static final String TASK_TAG = "task";
    public static final String TASK_DONE_TAG = "done";
    public static final String TASK_ID = "id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initializing views
        initViews();

        /*
         * View Model and observing live data
         */
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(TaskViewModel.class);
        observeLiveData();

        /*
         * Handling adapter checkbox clicks
         */
        adapter.setOnCheckboxCLickListener(new TaskAdapter.onCheckboxCLickListener() {
            @Override
            public void onCheckboxClick(TaskEntity entity) {
                entity.setDone(!entity.isDone());
                viewModel.updateTask(entity);
            }
        });

        /*
         * Handling adapter Text clicks for updating particular task
         */
        adapter.setOnTextViewClickListener(new TaskAdapter.onTextViewClickListener() {
            @Override
            public void onTextViewClick(TaskEntity entity) {
                CustomDialogFragment customDialogFragment = new CustomDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(TASK_ID, entity.getId());
                bundle.putBoolean(TASK_DONE_TAG, entity.isDone());
                bundle.putString(TASK_TAG, entity.getTask());
                customDialogFragment.setArguments(bundle);
                customDialogFragment.show(getSupportFragmentManager(), "example dialog");
            }
        });

        /*
         * Swipe to delete functionality
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.deleteTask(adapter.getNoteAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);


    }


    private void observeLiveData() {
        viewModel.getAllTask().observe(MainActivity.this, new Observer<List<TaskEntity>>() {
            @Override
            public void onChanged(List<TaskEntity> taskEntities) {
                adapter.submitList(taskEntities);
            }
        });
    }


    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_new_task:
                openCustomDialogFragment();
                break;
            case R.id.delete_all_task:
                deleteAllTask();
                break;
            case R.id.delete_done_task:
                deleteDoneTask();
                break;
            case R.id.delete_undone_task:
                deleteUndoneTask();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteUndoneTask() {
        viewModel.deleteUndoneTask();
    }

    private void deleteDoneTask() {
        viewModel.deleteDoneTask();
    }

    private void deleteAllTask() {
        viewModel.deleteAllTask();
    }

    private void openCustomDialogFragment() {
        CustomDialogFragment customDialogFragment = new CustomDialogFragment();
        Bundle bundle = null;
        customDialogFragment.setArguments(bundle);
        customDialogFragment.show(getSupportFragmentManager(), "example dialog");
    }


}