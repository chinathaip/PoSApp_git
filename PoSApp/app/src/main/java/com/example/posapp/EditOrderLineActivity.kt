package com.example.posapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class EditOrderLineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_order_line)

        val productPriceInput = findViewById<TextView>(R.id.editorderline_price_input)
        val productQuantityInput=findViewById<TextView>(R.id.editorderline_quantity_input)
        val applychangesbtn =findViewById<Button>(R.id.editorderline_submitbtn)
        val id = intent.getLongExtra("order_id",0)
        val productid = intent.getLongExtra("product_id",0)
        val price = intent.getIntExtra("product_price",0)
        Log.i("EditOrderActivity","Before: ${productid.toString()}")
        val quantity=intent.getIntExtra("product_quantity",0)
        productPriceInput.text = price.toString()
        productQuantityInput.text = quantity.toString()
        applychangesbtn.setOnClickListener {

            val alertDialog=AlertDialog.Builder(this)
            alertDialog.setTitle("OrderLine Update Operation")
            alertDialog.setMessage("Successfully updated the table")
            alertDialog.setNeutralButton("Ok"){
                dialong,which->GlobalScope.launch {
                val db=POSAppDatabase.getInstance(this@EditOrderLineActivity)
                //NOTE:   .toString.toInt() is just for a conversion purpose to put into the update function
                //solution to the numberformattingerror something.
//                db.orderLineDAO().updatelol()
                db.orderLineDAO().update(productid,productPriceInput.text.toString().toInt(),productQuantityInput.text.toString().toInt())
//                Log.i("EditOrderActivity",db.orderLineDAO().getAll().toString())
//                Log.i("EditOrderActivity",id.toString())
//                Log.i("EditOrderActivity",productid.toString())
//                Log.i("EditOrderActivity",productPriceInput.text.toString())
//                Log.i("EditOrderActivity",productQuantityInput.text.toString())
            }
            }
            alertDialog.show()
        }


    }
}