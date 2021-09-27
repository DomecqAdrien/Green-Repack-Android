package com.esgi.greenrepack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ProjectsActivity extends AppCompatActivity {


    int ProjectList[] = {R.drawable.travauxmaritime, R.drawable.ponttillionville,R.drawable.traintravaux, R.drawable.travauxmaritime, R.drawable.ponttillionville,R.drawable.traintravaux,};
    String Labels[] = {"Port Lagos","Pont Algerie","Feroviere Turquie","Port Lagos","Pont Algerie","Feroviere Turquie"};
    RecyclerView recyclerViewX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

        ListeGenerate(R.id.Project_Liste);
        ListeGenerate(R.id.Project_ListeII);
        ListeGenerate(R.id.Project_ListeIII);
        ListeGenerate(R.id.Project_ListeIV);


    }

    public RecyclerView ListeGenerate(int recyclerviewid){

        RecyclerView recyclerViewX = findViewById(recyclerviewid);
        recyclerViewX.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        recyclerViewX.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter recyclerViewAdapter = new ProjectAdapter(ProjectList, Labels, this);
        recyclerViewX.setAdapter(recyclerViewAdapter);
        return recyclerViewX;



    }

}