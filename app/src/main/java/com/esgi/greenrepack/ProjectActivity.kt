package com.esgi.greenrepack

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.esgi.greenrepack.models.Project

class ProjectActivity : AppCompatActivity() {
    var project: Project? =
        Project(3, "First Project", "Thats Third Project", 240.21, "24/10/2021", "16/12/2021", 3)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)


        project = intent.getParcelableExtra("project")
        Log.i("project", project.toString())

        val Title = findViewById<View>(R.id.Project_TitleII) as TextView
        Title.text = project!!.libelle
        val description = findViewById<View>(R.id.Description_Content) as TextView
        description.text = project!!.description
        val Somme = findViewById<View>(R.id.Project_price) as TextView
        Somme.text = project!!.somme.toString()
        val Start = findViewById<View>(R.id.Project_Start) as TextView
        Start.text = project!!.dateDebut.toString()
        val End = findViewById<View>(R.id.Project_End) as TextView
        End.text = project!!.dateFin.toString()
    }
}