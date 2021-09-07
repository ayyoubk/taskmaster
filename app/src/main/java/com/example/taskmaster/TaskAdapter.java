package com.example.taskmaster;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{
    List<Todo> tasks = new ArrayList<Todo>();

    public TaskAdapter(List<Todo> tasks) {
        this.tasks = tasks;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        public Todo task;
        View itemView;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            itemView.setOnClickListener(view -> {
                Intent goToDetailPage = new Intent(view.getContext(),TaskDetailPage.class);
                goToDetailPage.putExtra("title",task.getTitle());
                goToDetailPage.putExtra("body",task.getBode());
                goToDetailPage.putExtra("state",task.getState());
                view.getContext().startActivity(goToDetailPage);
            });
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task,parent,false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(view);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.task = tasks.get(position);
        TextView taskTitle = holder.itemView.findViewById(R.id.titleOfTask);
        taskTitle.setText(holder.task.getTitle());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


}
