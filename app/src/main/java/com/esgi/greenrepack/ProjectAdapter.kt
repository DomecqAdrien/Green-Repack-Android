package com.esgi.greenrepack

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esgi.greenrepack.ProjectAdapter.MyViewHolder
import com.esgi.greenrepack.obj.Project
import kotlinx.android.synthetic.main.project_label_row.view.*

class ProjectAdapter(private val projects: List<Project>, private val context: Context) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.project_label_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProjectActivity::class.java)
            //intent.putExtra("film_id",film.id)
            context.startActivity(intent)
        }
        holder.projectLabel.text = projects[position].libelle
        Glide.with(context)
            .load(projects[position].logo)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.projectpicture)
    }

    override fun getItemCount(): Int {
        return projects.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var projectLabel: TextView = itemView.project_label
        var projectpicture: ImageView = itemView.project_image
    }
}