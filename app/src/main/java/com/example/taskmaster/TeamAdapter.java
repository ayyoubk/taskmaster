package com.example.taskmaster;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {
    List<Team> teams = new ArrayList<>();

    public TeamAdapter(List<Team> teams) {
        this.teams = teams;
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {

        public Team team;
        View itemView;
        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_team,parent,false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        holder.team = teams.get(position);
        RadioButton radioButton = holder.itemView.findViewById(R.id.radioButton);
        radioButton.setText(holder.team.getName());
        radioButton.setId(position);
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

}
