package com.example.coffee.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffee.R
import com.example.coffee.ui.home.Coffee
import com.example.coffee.ui.home.CoffeeAdapter
import java.util.*
import kotlin.collections.ArrayList

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recycle_view_sale)
        recyclerView.setLayoutManager(LinearLayoutManager(view?.getContext()))
        val adapter: RecyclerView.Adapter<*> = SaleAdapter(generateSales())
        recyclerView.adapter = adapter
        return root
    }
    private fun generateSales(): List<Sale> {
        val sales: MutableList<Sale> = ArrayList()
        sales.add(Sale("Скидка 20% на весь чай", "https://i.pinimg.com/564x/7f/2c/5f/7f2c5f9cc52a59e25c452efbf2d6c46a.jpg", Date()))
        sales.add(Sale("Скидка в день рождения -15% на все", "https://i.pinimg.com/564x/1f/78/45/1f7845555480d99cbcb59387a2c08b05.jpg", Date()))
        sales.add(Sale("Новый сорт кофе из бразилии", "https://i.pinimg.com/564x/60/37/43/6037438a8f699e55db62750b688f3217.jpg", Date()))
        return sales
    }
}