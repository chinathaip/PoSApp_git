package com.example.posapp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import javax.crypto.Mac
import kotlin.collections.ArrayList

class ProductRecyclerViewAdapter(private val itemList: ArrayList<Product>, private val onItemClicked:(Product)->Unit):RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder>(){
    class ViewHolder(ListitemView: View,onItemClicked_fun:(Int)->Unit):RecyclerView.ViewHolder(ListitemView) {
        val productImageView: ImageView = ListitemView.findViewById(R.id.productimageView)
        val productNameTextView: TextView = ListitemView.findViewById(R.id.NameView)
        val productPriceTextView: TextView = ListitemView.findViewById(R.id.PriceView)
        val productaddmorebtn = ListitemView.findViewById<Button>(R.id.addmoreBtn)
        val productremoveonebyn = ListitemView.findViewById<Button>(R.id.removeoneBtn)
        init {//init - execute immediately after the primary constructor
            //use instead of fun main() ?

            ListitemView.setOnClickListener { // ListitemView = order_layout (viewholder --> the thing that will be shown in recyclerview)
                // we set it so that if that viewholder is clicked, then it returns the position of that viewholder
                onItemClicked_fun(adapterPosition)

            }
            productaddmorebtn.setOnClickListener {
                OrderActivity.addorminusoperation=1 //pass "add" command to the OrderActivity, so they can be checked whether to remove or add item to the orderList
                onItemClicked_fun(adapterPosition)
            }
            productremoveonebyn.setOnClickListener {
                OrderActivity.addorminusoperation=2
                onItemClicked_fun(adapterPosition)
            }

        }


    }


    //this function creates a new ViewHolder(item inside a recyclerview) frame for inputting data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val MacaronView = LayoutInflater.from(context).inflate(R.layout.order_layout,parent,false)
    // take in XML file as an input and builds a View object from it.  ".from" means get the XML from the given context?
        return ViewHolder(MacaronView){
            //this lambda is onItemClicked_fun (Int) -> Unit
            onItemClicked(itemList[it])
             //we return a viewholder and call another function, and pass the value int from onItemClicked_fun(adapterposition)
            //it = adapterposition
        }
    }


    //this function puts data inside the viewholder created by "onCreateViewHolder"
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        /*
        macaronList is an arraylist of Macaron.kt object (refer to Macaron.kt)

       index 0 = ["Black Macaron","15.0",1001]
       index 1 = ["Blue Macaron","13.0",1002]
       index 2 = ["Green Macaron","16.0",1003] and so on

       This means that "macaron" is containing all those ["black","15.0",1001] depending on which index(position) it is.
       position is gathered from the linearlayout manager (refer OrderActivity.kt)
         */
//        Log.i("xD", OrderActivity.catID.toString())
        when(OrderActivity.catID){
            MACARON_CAT_ID->{
                when(position){
                    /*we use "when" keyword because there's no data about the macaron image inside "macaronList" (refer to the comment above)
                    This means that the image will not change as "position"/index changes, so we have to do it manually.
                    "when" is basically "switch" in Java
                     */
                    0-> holder.productImageView.setImageResource(R.drawable.macaron_black)
                    1-> holder.productImageView.setImageResource(R.drawable.macaron_blue)
                    2-> holder.productImageView.setImageResource(R.drawable.macaron_green)
                    3-> holder.productImageView.setImageResource(R.drawable.macaron_navy)
                    4-> holder.productImageView.setImageResource(R.drawable.macaron_pink)
                    5-> holder.productImageView.setImageResource(R.drawable.macaron_red)
                    6-> holder.productImageView.setImageResource(R.drawable.macaron_yellow)

                }
            }
            DRINK_CAT_ID-> {
                when (position) {
                    0 -> holder.productImageView.setImageResource(R.drawable.drink_softdrink)
                    1 -> holder.productImageView.setImageResource(R.drawable.drink_water)
                    2 -> holder.productImageView.setImageResource(R.drawable.drink_coffee)
                    3 -> holder.productImageView.setImageResource(R.drawable.drink_liqour)

                }
            }
            DESSERT_CAT_ID->{
                when (position) {
                    0 -> holder.productImageView.setImageResource(R.drawable.dessert_blueberry)
                    1 -> holder.productImageView.setImageResource(R.drawable.dessert_strawberry)
                    2 -> holder.productImageView.setImageResource(R.drawable.dessert_chocolate)
                    3 -> holder.productImageView.setImageResource(R.drawable.dessert_applepie)

                }
            }
            ICECREAM_CAT_ID->{
                when (position) {
                    0 -> holder.productImageView.setImageResource(R.drawable.icecream_vanilla)
                    1 -> holder.productImageView.setImageResource(R.drawable.icecream_chocholate)
                    2 -> holder.productImageView.setImageResource(R.drawable.icecream_eggnog)
                    3 -> holder.productImageView.setImageResource(R.drawable.icecream_milk)
                }
            }
            THAIDISHES_CAT_ID->{
                when (position) {
                    0 -> holder.productImageView.setImageResource(R.drawable.thaidish_padkapao)
                    1 -> holder.productImageView.setImageResource(R.drawable.thaidish_kaopad)
                    2 -> holder.productImageView.setImageResource(R.drawable.thaidish_tomyamkung)
                    3 -> holder.productImageView.setImageResource(R.drawable.thaidish_padthai)
                }
            }
            FRUITS_CAT_ID->{
                when (position) {
                    0 -> holder.productImageView.setImageResource(R.drawable.fruit_pineapple)
                    1 -> holder.productImageView.setImageResource(R.drawable.fruit_apple)
                    2 -> holder.productImageView.setImageResource(R.drawable.fruit_orange)
                    3 -> holder.productImageView.setImageResource(R.drawable.fruit_watermelon)
                }
            }
            ALL_CAT_ID->{
                //when(item_id){
                //1001 -> set macaron image
                //1xxx...
                //2001 -> set drink image
                //2xxx...
                //3001 -> set icecream image
                //3xxx...
            // }
            }
        }


        holder.productNameTextView.setText(item.name)
        holder.productPriceTextView.setText(item.price.toString())
        /*
        since "macaron" contains a Macaron.kt object, it can access the variable inside (refer to Macaron.kt)
        we change the image, name , and the price of the macaron depending on the "position"
         */

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}


