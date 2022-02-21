package com.example.posapp

import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast



class CategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
    }

    fun clickMacaron(view: View){
        Toast.makeText(this,"Going to macaron page", Toast.LENGTH_SHORT).show()
        val intent = Intent(this,OrderActivity::class.java)
        //'CatID' is the keyword, MACARON_CAT_ID is the value.
        //To retrieve value MACARON_CAT_ID, you need to put the right keyword, 'CatID' in this case
        intent.putExtra("CatID", MACARON_CAT_ID)
        startActivity(intent)
    }

    fun clickDrink(view:View){
        Toast.makeText(this,"Going to drink page",Toast.LENGTH_SHORT).show()
        val intent = Intent(this,OrderActivity::class.java)
        intent.putExtra("CatID", DRINK_CAT_ID)
        startActivity(intent)
    }

    fun clickDessert(view:View){
        Toast.makeText(this,"Going to dessert page",Toast.LENGTH_SHORT).show()
        val intent = Intent(this,OrderActivity::class.java)
        intent.putExtra("CatID", DESSERT_CAT_ID)
        startActivity(intent)
    }

    fun clickIceCream(view: View){
        Toast.makeText(this,"Going to ice-cream page",Toast.LENGTH_SHORT).show()
        val intent = Intent(this,OrderActivity::class.java)
        intent.putExtra("CatID", ICECREAM_CAT_ID)
        startActivity(intent)
    }

    fun clickThaiDish(view:View){
        Toast.makeText(this,"Going to Thai dish page",Toast.LENGTH_SHORT).show()
        val intent = Intent(this,OrderActivity::class.java)
        intent.putExtra("CatID", THAIDISHES_CAT_ID)
        startActivity(intent)
    }

    fun clickFruit(view:View){
        Toast.makeText(this,"Going to fruit page",Toast.LENGTH_SHORT).show()
        val intent = Intent(this,OrderActivity::class.java)
        intent.putExtra("CatID", FRUITS_CAT_ID)
        startActivity(intent)
    }
}