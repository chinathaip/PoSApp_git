package com.example.posapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class CommandCenterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_command_center)
    }

    fun onNewORderClick(view: View){
        val intent = Intent(this,CategoryActivity::class.java)
        startActivity(intent)
    }

    fun onAddProductClick(view:View){
        val intent = Intent(this,ProductCRUDActivity::class.java)
        startActivity(intent)
    }
}