package com.esgi.greenrepack

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.greenrepack.obj.Association
import com.esgi.greenrepack.obj.Project
import kotlinx.android.synthetic.main.association_role.*
import java.util.*

class ProjectsActivity : AppCompatActivity() {


    private var Title: TextView? = null
    var ProjectList = intArrayOf(
        R.drawable.travauxmaritime,
        R.drawable.ponttillionville,
        R.drawable.traintravaux,
        R.drawable.travauxmaritime,
        R.drawable.ponttillionville,
        R.drawable.traintravaux
    )
    var Labels: MutableList<String> =
        ArrayList() // {"Port Lagos","Pont Algerie","Feroviere Turquie","Port Lagos","Pont Algerie","Feroviere Turquie"};
    var LabelsX = arrayOf(
        "Port Lagos",
        "Pont Algerie",
        "Feroviere Turquie",
        "Port Lagos",
        "Pont Algerie",
        "Feroviere Turquie"
    )




    var projects: MutableList<Project> = mutableListOf()
    var associations: MutableList<Association> = mutableListOf()
    private lateinit var associationRecyclerView: RecyclerView
    private lateinit var associationAdapter : AssociationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_list)
        generateProjects()
        generateAssociations()
        for (i in associations.indices) {
            val Label = associations[i].nom
            Labels.add(Label!!)
        }

        initRecycler()

    }

    private fun initRecycler() {
        associationRecyclerView = findViewById(R.id.associationRecyclerView)
        associationAdapter = AssociationAdapter(this)
        associationRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ProjectsActivity, RecyclerView.VERTICAL, false)
            adapter = associationAdapter
        }
        Log.i("asso", associations.toString())
        associationAdapter.associations = associations
    }



    private fun generateAssociations() {
        this.associations.add(
            Association(
                1,
                "1000 Espoir",
                "1000@milespoir.com",
                "XDCDCFEVEAVA",
                "",
                "",
                projects
            )
        )
        this.associations.add(
            Association(
                2,
                "Adrien D",
                "1000@milespoir.com",
                "14545121VEFCQCQ",
                "",
                "",
                projects
            )
        )
        this.associations.add(
            Association(3, "Fabien R", "1000@milespoir.com", "ZDFaf64546216", "", "", projects)
        )
    }

    private fun generateProjects() {
        projects.add(Project(3, "FirsP", "Thats Third Project", 240.21, "24/10/2021", "24/08/2021", 3))
        projects.add(Project(2, "FirsPV", "Thats Seconde Project", 240.21, "24/10/2021", "24/08/2021", 2))
        projects.add(Project(3, "FirsXP", "Thats Seconde Project", 240.21, "24/10/2021", "24/08/2021", 3))
        projects.add(Project(4, "Firs11", "Thats My First P11", 240.21, "24/10/2021", "24/08/2021", 1))
        projects.add(Project(5, "Firs22", "Thats My First P22", 240.21, "24/10/2021", "24/08/2021", 2))
        projects.add(Project(6, "First33", "Thats My First P33", 240.21, "24/10/2021", "24/08/2021", 3))
    }
}