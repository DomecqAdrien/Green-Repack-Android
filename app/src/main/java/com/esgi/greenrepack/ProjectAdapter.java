package com.esgi.greenrepack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyViewHolder> {
    @NonNull
    @NotNull

    int ProjectList[] = {R.drawable.travauxmaritime, R.drawable.ponttillionville,R.drawable.traintravaux};
    String Labels[];
    Context context;

    public ProjectAdapter(int[] projectList, String[] labels, Context context) {
        ProjectList = projectList;
        Labels = labels;
        this.context = context;
    }
    public ProjectAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.project_label_row,parent,false);
        return new ProjectAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProjectAdapter.MyViewHolder holder, int position) {

        holder.ProjectLabel.setText(Labels[position]);
        holder.Projectpicture.setImageResource(ProjectList[position]);

    }

    @Override
    public int getItemCount() {
        return ProjectList.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ProjectLabel;
        ImageView Projectpicture;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ProjectLabel = itemView.findViewById(R.id.ProjectLabel);
            Projectpicture = itemView.findViewById(R.id.ProjectThumbs);
        }
    }

}
