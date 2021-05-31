package com.example.coffee.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.coffee.R
import com.squareup.picasso.Picasso

class CoffeeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val root = inflater.inflate(R.layout.fragment_coffee, container, false)
        val name = root.findViewById<TextView>(R.id.name_coffee)
        name.text = requireArguments().getString("name", "")
        val image = root.findViewById<ImageView>(R.id.image_coffee)
        Picasso.get().load(requireArguments().getString("image", "")).into(image)
        val cost = root.findViewById<TextView>(R.id.cost)
        cost.setText(requireArguments().getInt("cost", 0))
        val description = root.findViewById<TextView>(R.id.description)
        description.text = requireArguments().getString("description", "")
        return root
    }

    companion object {
        fun newInstance(
            name: String?,
            image: String?,
            cost: Int,
            description: String?
        ): CoffeeFragment {
            val coffeeFragment = CoffeeFragment()
            val args = Bundle()
            args.putString("name", name)
            args.putString("image", image)
            args.putInt("cost", cost)
            args.putString("description", description)
            coffeeFragment.arguments = args
            return coffeeFragment
        }
    }
}