package com.example.coffee.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.coffee.R
import com.squareup.picasso.Picasso

class CoffeeAdapter(private val versions: List<Coffee>, private val onClickListener: onCoffeeClickListener) : RecyclerView.Adapter<CoffeeAdapter.CoffeeHolder>() {
    interface onCoffeeClickListener {
        fun onCoffeeClick(coffee: Coffee?, position: Int)
    }

    class CoffeeHolder(itemView: View) : ViewHolder(itemView) {
        val image: ImageView
        val name: TextView
        val description: TextView

        init {
            image = itemView.findViewById(R.id.item_image)
            name = itemView.findViewById(R.id.item_name)
            description = itemView.findViewById(R.id.item_text)
            //name.setOnClickListener(this);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_view, parent, false)
        return CoffeeHolder(itemView)
    }

    override fun onBindViewHolder(holder: CoffeeHolder, position: Int) {
        val coffee = versions[position]
        Picasso.get().load(coffee.image).into(holder.image)
        holder.name.text = coffee.name
        holder.description.text = coffee.description
        holder.itemView.setOnClickListener { onClickListener.onCoffeeClick(coffee, position) }
    }

    override fun getItemCount(): Int {
        return versions.size
    }

}