package com.esgi.greenrepack.associations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.esgi.greenrepack.ProjectAdapter;
import com.esgi.greenrepack.R;

public class InCurseActivity extends AppCompatActivity {

    int ProjectList[] = {R.drawable.travauxmaritime, R.drawable.ponttillionville,R.drawable.traintravaux};
    String Labels[] = {"Port Lagos","Pont Algerie","Feroviere Turquie"};
    RecyclerView recyclerViewX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_curse);

        ListeGenerate(R.id.Project_ListeVII);
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