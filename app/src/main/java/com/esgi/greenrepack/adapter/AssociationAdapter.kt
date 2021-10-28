package com.esgi.greenrepack.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.greenrepack.R
import com.esgi.greenrepack.models.Association
import com.esgi.greenrepack.models.Projet
import kotlinx.android.synthetic.main.association_row.view.*
import java.time.LocalDate

class AssociationAdapter(private val context: Context): RecyclerView.Adapter<AssociationAdapter.AssociationViewHolder>() {
    var associations: List<Association> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssociationViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.association_row, parent, false)
        return AssociationViewHolder(view)
    }

    override fun onBindViewHolder(holder: AssociationViewHolder, position: Int) {
        val association = associations[position]

        Log.i("projets", association.projets.toString())
        holder.associationName.text = association.nom
        val projectLayoutManager = LinearLayoutManager(holder.projects.context, RecyclerView.HORIZONTAL, false)
        val dateNow = LocalDate.now()
        val projetsEnCours = mutableListOf<Projet>()
        for(projet in association.projets) {
            val dateFin = LocalDate.parse(projet.dateFin!!.substring(0,10))
            if(dateNow.isBefore(dateFin)){
                projetsEnCours.add(projet)
            }
        }
        association.projets = projetsEnCours
        holder.projects.apply {
            layoutManager = projectLayoutManager
            adapter = ProjectAdapter(association.projets, association.nom, context)
            setRecycledViewPool(recycledViewPool)
        }

    }

    override fun getItemCount(): Int {
        return associations.size
    }

    class AssociationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var associationName: TextView = itemView.association_name
        var projects: RecyclerView = itemView.rv_projects
    }
}

