package com.esgi.greenrepack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.esgi.greenrepack.obj.Association;
import com.esgi.greenrepack.obj.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectsActivity extends AppCompatActivity {

    List<Project> projectListII = new ArrayList<>();
    List<Association> AssociationListe = new ArrayList<>();
    private TextView Title;

    int ProjectList[] = {R.drawable.travauxmaritime, R.drawable.ponttillionville,R.drawable.traintravaux, R.drawable.travauxmaritime, R.drawable.ponttillionville,R.drawable.traintravaux};
    List<String> Labels = new ArrayList<>();// {"Port Lagos","Pont Algerie","Feroviere Turquie","Port Lagos","Pont Algerie","Feroviere Turquie"};
    String LabelsX[] = {"Port Lagos","Pont Algerie","Feroviere Turquie","Port Lagos","Pont Algerie","Feroviere Turquie"};
    RecyclerView recyclerViewX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);


        Title = this.findViewById(R.id.First_Association);

        AssociationGenerate(AssociationListe);

        for (int i = 0; i < AssociationListe.size() ; i++) {

            String Label = AssociationListe.get(i).getNom();
            Labels.add(Label);

        };
        //Title.setText(AssociationListe.get(1).getNom());

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

        RecyclerView.Adapter recyclerViewAdapter = new ProjectAdapter(ProjectList, LabelsX, this);
        recyclerViewX.setAdapter(recyclerViewAdapter);

        return recyclerViewX;

    }


    public List<Association> AssociationGenerate(List<Association> Associations){

        Associations = AssociationListe;

        Associations.add(0,new Association(1,"1000 Espoir","1000@milespoir.com","XDCDCFEVEAVA","","",projectListII));
        Associations.add(1,new Association(2,"Adrien D","1000@milespoir.com","14545121VEFCQCQ","","",projectListII));
        Associations.add(2,new Association(3,"Fabien R","1000@milespoir.com","ZDFaf64546216","","",projectListII));

        Title.setText(AssociationListe.get(1).getNom());
        return AssociationListe;
    }

    public List<Project> ProjectListeGenerate(List<Project> ProjectListII){


        projectListII.add(1, new Project(3,"FirsP","Thats Third Project",240.21,"24/10/2021","24/08/2021",3));
        projectListII.add(2, new Project(2,"FirsPV","Thats Seconde Project",240.21,"24/10/2021","24/08/2021",2));
        projectListII.add(3, new Project(3,"FirsXP","Thats Seconde Project",240.21,"24/10/2021","24/08/2021",3));
        projectListII.add(1, new Project(4,"Firs11","Thats My First P11",240.21,"24/10/2021","24/08/2021",1));
        projectListII.add(2, new Project(5,"Firs22","Thats My First P22",240.21,"24/10/2021","24/08/2021",2));
        projectListII.add(3, new Project(6,"First33","Thats My First P33",240.21,"24/10/2021","24/08/2021",3));

        return projectListII;
    }


}