package com.example.coffee.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffee.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recycle_view)
        recyclerView.setLayoutManager(LinearLayoutManager(view?.getContext()));
        val coffeeClickListener: CoffeeAdapter.onCoffeeClickListener = object : CoffeeAdapter.onCoffeeClickListener {
            override fun onCoffeeClick(coffee: Coffee?, position: Int) {
                val text = "Цена: "+ coffee?.cost.toString()
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(getActivity(), text, duration)
                toast.show()
            }
        }
        val adapter: RecyclerView.Adapter<*> = CoffeeAdapter(generateCoffee(), coffeeClickListener)
        recyclerView.adapter = adapter
        return root
    }
    private fun generateCoffee(): List<Coffee> {
        val coffees: MutableList<Coffee> = ArrayList()
        coffees.add(Coffee("Milk Blend", "https://i.pinimg.com/564x/19/a5/95/19a59539efc4fe5dabbf6c83ad046007.jpg", getString(R.string.dasc_1), 356))
        coffees.add(Coffee("Бразилия Рамос", "https://i.pinimg.com/564x/66/8a/ac/668aacad2c8e11997f298c08baec2310.jpg", getString(R.string.dasc_2), 259))
        coffees.add(Coffee("Никарагуа Марагоджип", "https://i.pinimg.com/564x/88/9c/53/889c5304fb9094e9cf348d6fe05bed96.jpg", getString(R.string.dasc_3), 734))
        coffees.add(Coffee("San Ramon", "https://i.pinimg.com/564x/72/63/c1/7263c1938526fcb31235a23bdad3d7d5.jpg", getString(R.string.dasc_4), 479))
        return coffees
    }
}