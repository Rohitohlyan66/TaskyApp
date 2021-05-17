package com.example.fragments.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragments.MainActivity;
import com.example.fragments.R;
import com.example.fragments.localdatabase.TaskEntity;

import java.util.Random;

public class TaskAdapter extends ListAdapter<TaskEntity, TaskAdapter.MyViewHolder> {

    private onCheckboxCLickListener checkBoxListener;
    private onTextViewClickListener textViewListener;

    public TaskAdapter() { super(diffCallback); }

    public static DiffUtil.ItemCallback<TaskEntity> diffCallback = new DiffUtil.ItemCallback<TaskEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull TaskEntity oldItem, @NonNull TaskEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TaskEntity oldItem, @NonNull TaskEntity newItem) {

            return oldItem.getId() == newItem.getId() &&
                    oldItem.getTask().equals(newItem.getTask()) &&
                    oldItem.isDone() == newItem.isDone();
        }
    };


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_view_single_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

       /*
        * Generating a random number
        * And getting color according to the number from the colors array
        */
        int[] colorList = {R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5, R.color.color6, R.color.color7, R.color.color8};
        int random = new Random().nextInt((7 - 0) + 1) + 0;

        /*
         * Setting data to views
         */
        holder.taskTextView.setText(getItem(position).getTask());
        holder.checkBox.setChecked(getItem(position).isDone());
        holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), colorList[random]));
    }

    public TaskEntity getNoteAt(int position) {
        return getItem(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView taskTextView;
        CheckBox checkBox;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            taskTextView = itemView.findViewById(R.id.task_text_view);
            checkBox = itemView.findViewById(R.id.checkbox);
            cardView = itemView.findViewById(R.id.card_view);


            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBoxListener.onCheckboxClick(getItem(getLayoutPosition()));
                }
            });

            taskTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textViewListener.onTextViewClick(getItem(getLayoutPosition()));
                }
            });
        }
    }

    public interface onCheckboxCLickListener {
        void onCheckboxClick(TaskEntity entity);
    }

    public void setOnCheckboxCLickListener(onCheckboxCLickListener checkBoxListener) {
        this.checkBoxListener = checkBoxListener;
    }

    public interface onTextViewClickListener {
        void onTextViewClick(TaskEntity entity);
    }

    public void setOnTextViewClickListener(onTextViewClickListener textViewListener) {
        this.textViewListener = textViewListener;
    }
}
