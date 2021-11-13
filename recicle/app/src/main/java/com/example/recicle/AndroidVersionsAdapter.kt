package com.example.recicle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.recicle.AndroidVersionsAdapter.AndroidHolder
import com.squareup.picasso.Picasso

class AndroidVersionsAdapter(private val versions: List<AndroidVersions>, private val onClickListener: onAndroidClickListener) : RecyclerView.Adapter<AndroidHolder>() {
    interface onAndroidClickListener {
        fun onAndroidClick(androidVersions: AndroidVersions?, position: Int)
    }

    class AndroidHolder(itemView: View) : ViewHolder(itemView) {
        val image: ImageView
        val name: TextView

        init {
            image = itemView.findViewById(R.id.item_image)
            name = itemView.findViewById(R.id.item_name)
            //name.setOnClickListener(this);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AndroidHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_view, parent, false)
        return AndroidHolder(itemView)
    }

    override fun onBindViewHolder(holder: AndroidHolder, position: Int) {
        val androidVersion = versions[position]
        Picasso.get().load(androidVersion.image).into(holder.image)
        holder.name.text = androidVersion.name
        holder.itemView.setOnClickListener { onClickListener.onAndroidClick(androidVersion, position) }
    }

    override fun getItemCount(): Int {
        return versions.size
    }

}