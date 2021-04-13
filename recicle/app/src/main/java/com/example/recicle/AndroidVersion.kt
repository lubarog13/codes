package com.example.recicle

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class AndroidVersion : AppCompatActivity() {
    private var name: TextView = findViewById(R.id.name)
    private var image: ImageView = findViewById(R.id.icon)
    private var background: ImageView = findViewById(R.id.background)
    private var realased: TextView = findViewById(R.id.realased)
    private var textView: TextView = findViewById(R.id.textView)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.android_view)
        val intent = intent
        name.text = intent.getStringExtra("name")
        Picasso.get().load(intent.getStringExtra("image")).resize(300, 300).centerCrop().into(image)
        realased.text  = intent.getStringExtra("version")
        textView.text = intent.getStringExtra("info")
        Picasso.get().load("https://i.pinimg.com/564x/e7/c6/df/e7c6df386e4d410169d5f47c19a1bdc6.jpg").resize(1000, 400).into(background)
    }
}