package com.esgi.greenrepack.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esgi.greenrepack.R
import com.esgi.greenrepack.models.GreenCoin
import kotlinx.android.synthetic.main.green_coins_available_row.view.*
import kotlinx.android.synthetic.main.project_label_row.view.*
import org.w3c.dom.Text

class GreenCoinAdapter(
    var coins: List<GreenCoin>,
    val context: Context
    ) : RecyclerView.Adapter<GreenCoinAdapter.GreenCoinHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GreenCoinHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.green_coins_available_row, parent, false)
        return GreenCoinHolder(view)
    }

    override fun onBindViewHolder(holder: GreenCoinHolder, position: Int) {
        val coin = coins[position]
        //holder.itemView.setOnClickListener {
        //    val intent = Intent(context, ProjectDetailActivity::class.java)
        //    intent.putExtra("coin", coin)
        //    context.startActivity(intent)
        //}
        holder.montant.text = coin.montant.toString()
        holder.montantRestant.text = coin.montantRestant.toString()
        holder.dateExpiration.text = coin.dateExpiration.substring(0,10)

    }


    override fun getItemCount(): Int {
        return coins.size
    }

    inner class GreenCoinHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var montant: TextView = itemView.montant
        var montantRestant: TextView = itemView.montant_restant
        var dateExpiration: TextView = itemView.date_expiration
    }
}