package com.esgi.greenrepack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.service.quicksettings.Tile;
import android.widget.TextView;

import com.esgi.greenrepack.obj.Project;

public class ProjectActivity extends AppCompatActivity {

    Project ProjectI = new Project(3,"First Project","Thats Third Project",240.21,"24/10/2021","16/12/2021",3);
    TextView Title;
    TextView description;
    TextView Somme;
    TextView Start;
    TextView End;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        TextView Title = (TextView)this.findViewById(R.id.Project_TitleII);
        Title.setText(ProjectI.getLibelle());
        TextView description = (TextView)this.findViewById(R.id.Description_Content);
        description.setText(ProjectI.getDescription());

        TextView Somme = (TextView)this.findViewById(R.id.Project_price);
        Somme.setText(ProjectI.getSomme().toString());

        TextView Start = (TextView)this.findViewById(R.id.Project_Start);
        Start.setText(ProjectI.getDateDebut().toString());

        TextView End = (TextView)this.findViewById(R.id.Project_End);
        End.setText(ProjectI.getDateFin().toString());


    }
}