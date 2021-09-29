package com.esgi.greenrepack

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.greenrepack.models.Association
import kotlinx.android.synthetic.main.association_role.view.*

class AssociationAdapter(private val context: Context): RecyclerView.Adapter<AssociationAdapter.AssociationViewHolder>() {
    var associations: List<Association> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssociationViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.association_role, parent, false)
        return AssociationViewHolder(view)
    }

    override fun onBindViewHolder(holder: AssociationViewHolder, position: Int) {
        val association = associations[position]
        holder.associationName.text = association.nom
        val projectLayoutManager = LinearLayoutManager(holder.projects.context, RecyclerView.HORIZONTAL, false)

        holder.projects.apply {
            layoutManager = projectLayoutManager
            adapter = ProjectAdapter(association.projects!!, context)
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

