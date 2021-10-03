package com.esgi.greenrepack.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esgi.greenrepack.ProjectDetailActivity
import com.esgi.greenrepack.R
import com.esgi.greenrepack.adapter.ProjectAdapter.MyViewHolder
import com.esgi.greenrepack.models.Projet
import kotlinx.android.synthetic.main.project_label_row.view.*

class ProjectAdapter(
    private val projets: List<Projet>,
    private val association: String,
    private val context: Context
    ) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.project_label_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val projet = projets[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProjectDetailActivity::class.java)
            intent.putExtra("projet", projet)
            intent.putExtra("association", association)
            context.startActivity(intent)
        }
        holder.projectLabel.text = projets[position].libelle
        Glide.with(context)
            .load(projets[position].logo)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.projectpicture)
    }

    override fun getItemCount(): Int {
        return projets.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var projectLabel: TextView = itemView.project_label
        var projectpicture: ImageView = itemView.project_image
    }
}