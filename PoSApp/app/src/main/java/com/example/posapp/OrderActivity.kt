package com.example.posapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class OrderActivity : AppCompatActivity() {
    private lateinit var orderlist:ArrayList<Product>
    private var totalprice=0
    //i created a companion object to make a static variable, one that can be accessed by any class
    //so that RecylclerViewAdapter.kt can just type "OrderActivity.catID" to get the value
    companion object{
        var catID:Int?=null
        var addorminusoperation:Int?=null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        val recyclerview = findViewById<RecyclerView>(R.id.productLIst)
        val priceview = findViewById<TextView>(R.id.priceTextview)


        //Normal ViewModel Architecture
        val orderViewModel:OrderViewModel by viewModels()
        priceview.text=getString(R.string.total_price_textView,orderViewModel.totalprice)

        //Live Data Implementation
//        val totalpriceObserver = Observer<Int>{
//            newamount ->priceview.text=getString(R.string.total_price_textView,newamount)
//
//        }
//        orderViewModel.getTotalPriceLiveData().observe(this,totalpriceObserver)

        orderlist= arrayListOf()
        catID = intent.getIntExtra("CatID",0)
        //retrieve the data of Category ID by entering the keyword that 'stores' the data we 'putExtra' to.  (CatID in this case) (refer to CategoryActivity)
        Log.i("lol", catID.toString())
        recyclerview.layoutManager=LinearLayoutManager(this)
        val orderrecyclerview = findViewById<RecyclerView>(R.id.orderList)
        val adapter = OrderRecyclerViewAdapter(orderlist)
        orderrecyclerview.adapter=adapter
        orderrecyclerview.layoutManager=LinearLayoutManager(this)
        when(catID){
            MACARON_CAT_ID->{
                val macaron = ProductItem.createMacaronList()
                val macaronAdapter= ProductRecyclerViewAdapter(macaron){
                    macaron->Log.d("Item clicked",macaron.name) // "macaron ->"  is the value of the parameter private val onItemClicked
                    if(addorminusoperation==1){ //1 means add item to the list (addmorebtn will pass '1' if it is clicked)
                        if(orderlist.contains(macaron)){ //if the item already exists in the list, then we increment the quantity instead
                            val index = orderlist.indexOf(macaron)//get index of that existed macaron
                            macaron.quantity+= 1 //increment the quantity
                            orderlist.set(index,macaron) //replace the old one with the new one
                            adapter.notifyItemChanged(index) //update the recyclerview

                        }else{ //if we've never had this item in the list, then add it and display on the recyclerview
                            orderlist.add(macaron)
                            adapter.notifyItemInserted(orderlist.size)
                            Log.d("Array size after added", orderlist.size.toString())
                        }


                    }else if(addorminusoperation==2){//2 means remove item from the list (removeonebtn will pass '2' if it is clicked)
                        if(orderlist.contains(macaron)){//make sure if the item has been added before (if it's in the list?)
                            val index = orderlist.indexOf(macaron)
                            if(macaron.quantity==1){ //if there's only one piece of this product in the list, just remove it
                                orderlist.removeAt(index)
                                adapter.notifyItemRemoved(index)
                            }
                            else { // if the quantity is more than 1, then we decrement it by 1 everytime a button is clicked
                                macaron.quantity-=1
                                orderlist.set(index,macaron)
                                adapter.notifyItemChanged(index)
                                Log.d("Array size after delte",orderlist.size.toString())
                            }
                        }
                    }
                    totalprice = 0
//                    orderViewModel.totalpriceLivedata.value=0
                    for(item in orderlist){
                        if(item.quantity!=1){
                            totalprice+=item.price*item.quantity
//                            orderViewModel.totalpriceLivedata.value=totalprice
                        }else{
                            totalprice+=item.price
                            //update the livedata
//                            orderViewModel.totalpriceLivedata.value=orderViewModel.totalpriceLivedata.value?.plus(totalprice)
                        }
                    }

                    //update the orderViewmodel's totalprice )
                    orderViewModel.totalprice=totalprice //store in viewmodel, so it doesnt get destroyed when the activity is recreated
                    Log.d("totalprice","Total Price : $totalprice")
                    priceview.text = getString(R.string.total_price_textView,orderViewModel.totalprice)
                }


                recyclerview.adapter=macaronAdapter

            }
            DRINK_CAT_ID->{
                val drink = ProductItem.createDrinkList()
                val drinkAdapter = ProductRecyclerViewAdapter(drink){
                    drink->Log.d("DRINK",drink.name)
                    if(addorminusoperation==1){ //1 means add item to the list (addmorebtn will pass '1' if it is clicked)
                        if(orderlist.contains(drink)){ //if the item already exists in the list, then we increment the quantity instead
                            val index = orderlist.indexOf(drink)//get index of that existed macaron
                            drink.quantity+= 1 //increment the quantity
                            orderlist.set(index,drink) //replace the old one with the new one
                            adapter.notifyItemChanged(index) //update the recyclerview

                        }else{ //if we've never had this item in the list, then add it and display on the recyclerview
                            orderlist.add(drink)
                            adapter.notifyItemInserted(orderlist.size)
                            Log.d("Array size after added", orderlist.size.toString())
                        }


                    }else if(addorminusoperation==2){//2 means remove item from the list (removeonebtn will pass '2' if it is clicked)
                        if(orderlist.contains(drink)){//make sure if the item has been added before (if it's in the list?)
                            val index = orderlist.indexOf(drink)
                            if(drink.quantity==1){ //if there's only one piece of this product in the list, just remove it
                                orderlist.removeAt(index)
                                adapter.notifyItemRemoved(index)
                            }
                            else { // if the quantity is more than 1, then we decrement it by 1 everytime a button is clicked
                                drink.quantity-=1
                                orderlist.set(index,drink)
                                adapter.notifyItemChanged(index)
                                Log.d("Array size after delte",orderlist.size.toString())
                            }
                        }
                    }

                    totalprice=0
                    for(item in orderlist){
                        if(item.quantity!=1){
                            totalprice+=item.price*item.quantity
                        }else{
                            totalprice+=item.price
                        }
                    }
                    val priceview = findViewById<TextView>(R.id.priceTextview)
                    Log.d("totalprice","Total Price : $totalprice")
                    priceview.text = getString(R.string.total_price_textView,totalprice)
                }

                recyclerview.adapter=drinkAdapter

            }
            DESSERT_CAT_ID->{
                val dessert = ProductItem.createDessertList()
                val dessertAdapter = ProductRecyclerViewAdapter(dessert){
                        dessert->Log.d("DRINK",dessert.name)
                    if(addorminusoperation==1){ //1 means add item to the list (addmorebtn will pass '1' if it is clicked)
                        if(orderlist.contains(dessert)){ //if the item already exists in the list, then we increment the quantity instead
                            val index = orderlist.indexOf(dessert)//get index of that existed macaron
                            dessert.quantity+= 1 //increment the quantity
                            orderlist.set(index,dessert) //replace the old one with the new one
                            adapter.notifyItemChanged(index) //update the recyclerview

                        }else{ //if we've never had this item in the list, then add it and display on the recyclerview
                            orderlist.add(dessert)
                            adapter.notifyItemInserted(orderlist.size)
                            Log.d("Array size after added", orderlist.size.toString())
                        }


                    }else if(addorminusoperation==2){//2 means remove item from the list (removeonebtn will pass '2' if it is clicked)
                        if(orderlist.contains(dessert)){//make sure if the item has been added before (if it's in the list?)
                            val index = orderlist.indexOf(dessert)
                            if(dessert.quantity==1){ //if there's only one piece of this product in the list, just remove it
                                orderlist.removeAt(index)
                                adapter.notifyItemRemoved(index)
                            }
                            else { // if the quantity is more than 1, then we decrement it by 1 everytime a button is clicked
                                dessert.quantity-=1
                                orderlist.set(index,dessert)
                                adapter.notifyItemChanged(index)
                                Log.d("Array size after delte",orderlist.size.toString())
                            }
                        }
                    }

                    totalprice=0
                    for(item in orderlist){
                        if(item.quantity!=1){
                            totalprice+=item.price*item.quantity
                        }else{
                            totalprice+=item.price
                        }
                    }
                    val priceview = findViewById<TextView>(R.id.priceTextview)
                    Log.d("totalprice","Total Price : $totalprice")
                    priceview.text = getString(R.string.total_price_textView,totalprice)
                }

                recyclerview.adapter=dessertAdapter
            }
            ICECREAM_CAT_ID->{
                val icecream = ProductItem.createIceCreamList()
                val iceCreamAdapter = ProductRecyclerViewAdapter(icecream){
                        icecream->Log.d("DRINK",icecream.name)
                    if(addorminusoperation==1){ //1 means add item to the list (addmorebtn will pass '1' if it is clicked)
                        if(orderlist.contains(icecream)){ //if the item already exists in the list, then we increment the quantity instead
                            val index = orderlist.indexOf(icecream)//get index of that existed macaron
                            icecream.quantity+= 1 //increment the quantity
                            orderlist.set(index,icecream) //replace the old one with the new one
                            adapter.notifyItemChanged(index) //update the recyclerview

                        }else{ //if we've never had this item in the list, then add it and display on the recyclerview
                            orderlist.add(icecream)
                            adapter.notifyItemInserted(orderlist.size)
                            Log.d("Array size after added", orderlist.size.toString())
                        }


                    }else if(addorminusoperation==2){//2 means remove item from the list (removeonebtn will pass '2' if it is clicked)
                        if(orderlist.contains(icecream)){//make sure if the item has been added before (if it's in the list?)
                            val index = orderlist.indexOf(icecream)
                            if(icecream.quantity==1){ //if there's only one piece of this product in the list, just remove it
                                orderlist.removeAt(index)
                                adapter.notifyItemRemoved(index)
                            }
                            else { // if the quantity is more than 1, then we decrement it by 1 everytime a button is clicked
                                icecream.quantity-=1
                                orderlist.set(index,icecream)
                                adapter.notifyItemChanged(index)
                                Log.d("Array size after delete",orderlist.size.toString())
                            }
                        }
                    }

                    totalprice=0
                    for(item in orderlist){
                        if(item.quantity!=1){
                            totalprice+=item.price*item.quantity
                        }else{
                            totalprice+=item.price
                        }
                    }
                    val priceview = findViewById<TextView>(R.id.priceTextview)
                    Log.d("totalprice","Total Price : $totalprice")
                    priceview.text = getString(R.string.total_price_textView,totalprice)
                }

                recyclerview.adapter=iceCreamAdapter
            }
            THAIDISHES_CAT_ID->{
                val thaidish = ProductItem.createThaiDishList()
                val thaidishAdapter = ProductRecyclerViewAdapter(thaidish){
                        thaidish->Log.d("DRINK",thaidish.name)
                    if(addorminusoperation==1){ //1 means add item to the list (addmorebtn will pass '1' if it is clicked)
                        if(orderlist.contains(thaidish)){ //if the item already exists in the list, then we increment the quantity instead
                            val index = orderlist.indexOf(thaidish)//get index of that existed macaron
                            thaidish.quantity+= 1 //increment the quantity
                            orderlist.set(index,thaidish) //replace the old one with the new one
                            adapter.notifyItemChanged(index) //update the recyclerview

                        }else{ //if we've never had this item in the list, then add it and display on the recyclerview
                            orderlist.add(thaidish)
                            adapter.notifyItemInserted(orderlist.size)
                            Log.d("Array size after added", orderlist.size.toString())
                        }


                    }else if(addorminusoperation==2){//2 means remove item from the list (removeonebtn will pass '2' if it is clicked)
                        if(orderlist.contains(thaidish)){//make sure if the item has been added before (if it's in the list?)
                            val index = orderlist.indexOf(thaidish)
                            if(thaidish.quantity==1){ //if there's only one piece of this product in the list, just remove it
                                orderlist.removeAt(index)
                                adapter.notifyItemRemoved(index)
                            }
                            else { // if the quantity is more than 1, then we decrement it by 1 everytime a button is clicked
                                thaidish.quantity-=1
                                orderlist.set(index,thaidish)
                                adapter.notifyItemChanged(index)
                                Log.d("Array size after delte",orderlist.size.toString())
                            }
                        }
                    }

                    totalprice=0
                    for(item in orderlist){
                        if(item.quantity!=1){
                            totalprice+=item.price*item.quantity
                        }else{
                            totalprice+=item.price
                        }
                    }
                    val priceview = findViewById<TextView>(R.id.priceTextview)
                    Log.d("totalprice","Total Price : $totalprice")
                    priceview.text = getString(R.string.total_price_textView,totalprice)
                }

                recyclerview.adapter=thaidishAdapter

            }
            FRUITS_CAT_ID->{
                val fruit = ProductItem.createFruitList()
                val fruitAdapter = ProductRecyclerViewAdapter(fruit){
                        fruit->Log.d("DRINK",fruit.name)
                    if(addorminusoperation==1){ //1 means add item to the list (addmorebtn will pass '1' if it is clicked)
                        if(orderlist.contains(fruit)){ //if the item already exists in the list, then we increment the quantity instead
                            val index = orderlist.indexOf(fruit)//get index of that existed macaron
                            fruit.quantity+= 1 //increment the quantity
                            orderlist.set(index,fruit) //replace the old one with the new one
                            adapter.notifyItemChanged(index) //update the recyclerview

                        }else{ //if we've never had this item in the list, then add it and display on the recyclerview
                            orderlist.add(fruit)
                            adapter.notifyItemInserted(orderlist.size)
                            Log.d("Array size after added", orderlist.size.toString())
                        }


                    }else if(addorminusoperation==2){//2 means remove item from the list (removeonebtn will pass '2' if it is clicked)
                        if(orderlist.contains(fruit)){//make sure if the item has been added before (if it's in the list?)
                            val index = orderlist.indexOf(fruit)
                            if(fruit.quantity==1){ //if there's only one piece of this product in the list, just remove it
                                orderlist.removeAt(index)
                                adapter.notifyItemRemoved(index)
                            }
                            else { // if the quantity is more than 1, then we decrement it by 1 everytime a button is clicked
                                fruit.quantity-=1
                                orderlist.set(index,fruit)
                                adapter.notifyItemChanged(index)
                                Log.d("Array size after delte",orderlist.size.toString())
                            }
                        }
                    }

                    totalprice=0
                    for(item in orderlist){
                        if(item.quantity!=1){
                            totalprice+=item.price*item.quantity
                        }else{
                            totalprice+=item.price
                        }
                    }
                    val priceview = findViewById<TextView>(R.id.priceTextview)
                    Log.d("totalprice","Total Price : $totalprice")
                    priceview.text = getString(R.string.total_price_textView,totalprice)
                }

                recyclerview.adapter=fruitAdapter

            }
        }





        //TODO(store orderList into the database, use coroutines)
        val submitbtn = findViewById<Button>(R.id.SubmitOrderBtn)
        submitbtn.setOnClickListener {
            Log.i("SubmitOrder","The current order has been submitted")
            GlobalScope.launch {
                val order = Order(null,5001,2001)
                val db = POSAppDatabase.getInstance(applicationContext)
                val orderID : Long = db.orderDAO().insert(order)
                for(order in orderlist){
                    val orderLine = OrderLine(null,orderID,order.id.toLong(),order.price,order.quantity)
                    db.orderLineDAO().insertAll(orderLine)
                }
            }
        }

        val retrieveorderbtn = findViewById<Button>(R.id.retrieveOrderBtn)
        retrieveorderbtn.setOnClickListener {
            GlobalScope.launch {
                val db = POSAppDatabase.getInstance(applicationContext)
                val orders = db.orderDAO().getAll()
                val orderlines = db.orderLineDAO().getAll()
                for (order in orders){
                    Log . i ("Order", "Order ID = ${order.uid}, " +
                            "Branch ID = ${order.branchID} " +
                            "Staff ID = ${order.staffID}")
                }
                for(orderLine in orderlines){
                    Log . i ("Orderline", "orderLine ID = ${orderLine.uid}, " +
                            "Order ID = ${orderLine.orderID} " +
                            "Product ID = ${orderLine.productID}" +
                            "Product Quantity = ${orderLine.quantity}")
                }


            }
        }


    }



}


