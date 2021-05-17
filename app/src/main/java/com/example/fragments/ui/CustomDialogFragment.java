package com.example.fragments.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fragments.MainActivity;
import com.example.fragments.R;
import com.example.fragments.ViewModel.TaskViewModel;
import com.example.fragments.localdatabase.TaskEntity;


public class CustomDialogFragment extends AppCompatDialogFragment {

    public static String TAG = "PurchaseConfirmationDialog";
    Button saveButton, cancelButton;
    EditText taskEditText;
    TaskViewModel viewModel;
    Dialog dialog;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        /*
         * Preparing our Dialog View
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);


        taskEditText = view.findViewById(R.id.task_edit_text);
        cancelButton = view.findViewById(R.id.cancel_button);
        saveButton = view.findViewById(R.id.save_button);
        viewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);

        /*
         * If we get no arguments from activity it means we have to add new task
         * otherwise we have to update the task and in that case argument!=null
         */
        if (getArguments() == null) {
            saveButton.setText("Save");
        } else {
            saveButton.setText("Update");
            taskEditText.setText(getArguments().getString(MainActivity.TASK_TAG));
        }

        // Cancel Button in Dialog
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Save button in dialog
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getArguments() == null) {
                    if (!TextUtils.isEmpty(taskEditText.getText().toString()))
                        saveTask(taskEditText.getText().toString());
                    else
                        Toast.makeText(getActivity(), "Enter some task", Toast.LENGTH_SHORT).show();
                } else {
                    int id = getArguments().getInt(MainActivity.TASK_ID);
                    boolean isDone = getArguments().getBoolean(MainActivity.TASK_DONE_TAG);
                    if (!TextUtils.isEmpty(taskEditText.getText().toString()))
                        updateTask(taskEditText.getText().toString(), id, isDone);
                    else
                        Toast.makeText(getActivity(), "Enter some task", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return dialog;
    }


    private void updateTask(String task, int id, boolean isDone) {
        TaskEntity taskEntity = new TaskEntity(id, task, isDone);
        viewModel.updateTask(taskEntity);
        dialog.dismiss();

    }

    private void saveTask(String text) {
        TaskEntity task = new TaskEntity(text, false);
        viewModel.insertTask(task);
        dialog.dismiss();
    }


}


