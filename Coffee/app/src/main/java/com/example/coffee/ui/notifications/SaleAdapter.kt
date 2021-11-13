package com.example.coffee.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.coffee.R
import com.squareup.picasso.Picasso

class SaleAdapter(private val versions: List<Sale>) : RecyclerView.Adapter<SaleAdapter.SaleHolder>() {
    class SaleHolder(itemView: View) : ViewHolder(itemView) {
        val image: ImageView
        val name: TextView
        val date: TextView

        init {
            image = itemView.findViewById(R.id.image)
            name = itemView.findViewById(R.id.name)
            date = itemView.findViewById(R.id.date)
            //name.setOnClickListener(this);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.sale_item_view, parent, false)
        return SaleHolder(itemView)
    }

    override fun onBindViewHolder(holder: SaleHolder, position: Int) {
        val sale = versions[position]
        Picasso.get().load(sale.image).into(holder.image)
        holder.name.text = sale.name
        holder.date.text = sale.date.toString()
    }

    override fun getItemCount(): Int {
        return versions.size
    }

}