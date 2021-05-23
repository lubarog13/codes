package com.example.coffee.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coffee.R

class DashboardViewModel : ViewModel() {

    private val _name = MutableLiveData<String>().apply {
        value = "Иванов Иван Иванович"
    }
    private val _email = MutableLiveData<String>().apply {
        value = "ivan@mail.com"
    }
    private val _discount = MutableLiveData<String>().apply {
        value = "Ваша скидка:10%"
    }
    private val _inform = MutableLiveData<String>().apply {
        value = """
        Телефон: 8-(981)-766-81-48
        Email: lubarog13@gmail.com
        Telegram: t.me/@lubarog13
        """.trimIndent()
    }
    val name: LiveData<String> = _name
    val email:LiveData<String> = _email
    val discout:LiveData<String> = _discount
    val inform:LiveData<String> = _inform
}