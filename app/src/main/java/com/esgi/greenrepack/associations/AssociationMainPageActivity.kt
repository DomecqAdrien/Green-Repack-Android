package com.esgi.greenrepack.associations

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esgi.greenrepack.R
import com.esgi.greenrepack.services.TokenManager
import com.esgi.greenrepack.adapter.ProjectAdapter
import com.esgi.greenrepack.databinding.ActivityAssociationMainPageBinding
import com.esgi.greenrepack.databinding.ActivityProjectBinding
import com.esgi.greenrepack.models.Association
import com.esgi.greenrepack.models.Projet
import com.esgi.greenrepack.services.ApiClient
import kotlinx.android.synthetic.main.activity_association_main_page.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate

class AssociationMainPageActivity : AppCompatActivity() {

    private lateinit var tk: TokenManager
    private lateinit var binding: ActivityAssociationMainPageBinding

    private lateinit var association: Association
    private var projets: List<Projet> = listOf()
    private var projetsEnCours: MutableList<Projet> = mutableListOf()
    private var projetsTermines: MutableList<Projet> = mutableListOf()
    private lateinit var projetsEnCoursRecyclerView: RecyclerView
    private lateinit var projetsEncoursAdapter: ProjectAdapter
    private lateinit var projetsTerminesRecyclerView: RecyclerView
    private lateinit var projetsTerminesAdapter: ProjectAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tk = TokenManager(this)
        getAssoc()

    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Quitter Green Repack")
        builder.setMessage("Voulez vous vraiment quitter l'application?")
        builder.setPositiveButton("Quitter") { dialog, which ->
            finishAffinity()
        }

        builder.setNegativeButton("Annuler") { dialog, which ->
        }

        builder.show()
        //super.onBackPressed()
    }

    private fun initView() {
        binding = ActivityAssociationMainPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        with(binding) {
            projetNom.text = association.nom
            Glide.with(this@AssociationMainPageActivity)
                .load(association.logo)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(associationLogo)

            btnNewProject.setOnClickListener {
                val intent = Intent(this@AssociationMainPageActivity, CreateProjectActivity::class.java)
                intent.putExtra("associationId", association.id)
                intent.putExtra("logo", association.logo)
                startActivity(intent)
            }

            btnAssociationLogout.setOnClickListener {
                tk.clear()
                setResult(80)
                finish()
            }

            projetsEnCoursRecyclerView = rvProjetEnCours
            projetsTerminesRecyclerView = rvProjetsTermines
        }

        projetsEncoursAdapter = ProjectAdapter(projetsEnCours, association.nom,  this)
        projetsEnCoursRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@AssociationMainPageActivity, RecyclerView.HORIZONTAL, false)
            adapter = projetsEncoursAdapter
        }


        projetsTerminesAdapter = ProjectAdapter(projetsTermines, association.nom,this)
        projetsTerminesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@AssociationMainPageActivity, RecyclerView.HORIZONTAL, false)
            adapter = projetsTerminesAdapter
        }

    }

    private fun generateLists() {
        val dateNow = LocalDate.now()
        Log.i("date", dateNow.toString())
        for (projet in projets) {
            val dateFin = LocalDate.parse(projet.dateFin!!.substring(0,10))
            Log.i("dateFin", dateFin.toString())
            if(dateNow.isAfter(dateFin)){
                projetsEnCours.add(projet)
            } else {
                projetsTermines.add(projet)
            }
        }
    }

    private fun getAssoc() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = ApiClient.apiService.getAssociation("Bearer ${tk.token}")
                if (response.isSuccessful && response.body() != null) {
                    val content = response.body()
                    if (content != null) {
                        association = content
                        projets = association.projets
                        Log.i("content", content.toString())
                        generateLists()
                        initView()
                    }
                } else {
                    Log.e("Error Occured", response.message())
                }
            } catch (e: Exception) {
                Log.e("Exception Occured", e.message!!)
            }
        }
    }
}