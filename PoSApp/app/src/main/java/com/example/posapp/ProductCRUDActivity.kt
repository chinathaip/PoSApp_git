package com.example.posapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.posapp.databinding.ActivityProductCrudactivityBinding

class ProductCRUDActivity : AppCompatActivity() {
    private lateinit var binding:ActivityProductCrudactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
//        binding = ActivityProductCrudactivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_crudactivity)

    }
}