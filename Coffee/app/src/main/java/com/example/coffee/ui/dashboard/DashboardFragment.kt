package com.example.coffee.ui.dashboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.coffee.R
import com.squareup.picasso.Picasso


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val name: TextView = root.findViewById(R.id.name)
        dashboardViewModel.name.observe(viewLifecycleOwner, Observer {
            name.text = it
        })
        val email: TextView = root.findViewById(R.id.textView6)
        dashboardViewModel.email.observe(viewLifecycleOwner, Observer {
            email.text = it
        })
        val discount: TextView = root.findViewById(R.id.textView2)
        dashboardViewModel.discout.observe(viewLifecycleOwner, Observer {
            discount.text = it
        })
        val inform: TextView = root.findViewById(R.id.inform)
        dashboardViewModel.inform.observe(viewLifecycleOwner, Observer {
            inform.text = it
        })
        val image: ImageView = root.findViewById(R.id.imageView)
        Picasso.get().load("https://i.pinimg.com/564x/c7/94/7c/c7947c8393bae90406281ef96d1cb83e.jpg").into(image)
        val mDialButton: ImageButton = root.findViewById(R.id.call)
        mDialButton.setOnClickListener(View.OnClickListener {
            val phoneNo: String = "89817668148"
            val dial = "tel:$phoneNo"
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(dial)))
        })
        val emailButton: ImageButton = root.findViewById(R.id.email)
        emailButton.setOnClickListener(View.OnClickListener {
            val emailNo: String = "lubarog13@gmail.com"
           val emailIntent: Intent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_EMAIL,
                emailNo)
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "message");
            emailIntent.setType("message/rfc822");
            startActivity(Intent.createChooser(emailIntent, "Выберите email клиент :"))
        })
        val telegrammButton: ImageButton = root.findViewById(R.id.telegramm)
        telegrammButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://t.me/@lubarog13"))
            startActivity(intent)
        })
        return root
    }
}