package com.example.posapp

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderRecyclerViewAdapter(private val orderlist:ArrayList<Product>):RecyclerView.Adapter<OrderRecyclerViewAdapter.ViewHolder>(){
    class ViewHolder(ListitemView: View):RecyclerView.ViewHolder(ListitemView){
        val productnameTextView = ListitemView.findViewById<TextView>(R.id.item_name)
        val productpriceTextView=ListitemView.findViewById<TextView>(R.id.item_price)
        val productquantityTextView=ListitemView.findViewById<TextView>(R.id.item_quantity)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val OrderView = LayoutInflater.from(context).inflate(R.layout.viewholder_order_recyclerview_layout,parent,false)
        return ViewHolder(OrderView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item= orderlist[position]
        holder.productnameTextView.setText(item.name)
        holder.productpriceTextView.setText(item.price.toString())
        holder.productquantityTextView.setText(item.quantity.toString())

    }

    override fun getItemCount(): Int {
        return orderlist.size
    }
}