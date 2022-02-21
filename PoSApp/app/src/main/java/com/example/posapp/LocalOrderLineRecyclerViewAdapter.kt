package com.example.posapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LocalOrderLineRecyclerViewAdapter(private val localorderlinelist:List<OrderLine>,private val onOrderLineClicked:(OrderLine)->Unit):RecyclerView.Adapter<LocalOrderLineRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(Listitemview: View,onOrderLineClicked_fun: (Int) -> Unit):RecyclerView.ViewHolder(Listitemview){
        val localorderlineID = Listitemview.findViewById<TextView>(R.id.orderline_localid_text)
        val localorderproductID = Listitemview.findViewById<TextView>(R.id.orderline_localproductid_text)
        val localorderproductprice = Listitemview.findViewById<TextView>(R.id.orderline_localproductprice_text)
        val localorderproductquan = Listitemview.findViewById<TextView>(R.id.orderline_localproductquan_text)
        val localorderEditBtn = Listitemview.findViewById<Button>(R.id.orderline_editBtn)
        val localorderDeleteBtn = Listitemview.findViewById<Button>(R.id.orderline_deleteBtn)

        init{
            localorderEditBtn.setOnClickListener {
                OrderlineFragment.EditOrRemove="Edit"
                onOrderLineClicked_fun(adapterPosition)
            }
            localorderDeleteBtn.setOnClickListener {
                OrderlineFragment.EditOrRemove="Remove"
                onOrderLineClicked_fun(adapterPosition)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val context  =parent.context
        val localOrderlineView = LayoutInflater.from(context).inflate(R.layout.localorderline_viewholder_layout,parent,false)
        return ViewHolder(localOrderlineView){
            onOrderLineClicked(localorderlinelist[it])
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = localorderlinelist[position]
        holder.localorderlineID.text = item.uid.toString()
        holder.localorderproductID.text=item.productID.toString()
        holder.localorderproductprice.text=item.price.toString()
        holder.localorderproductquan.text = item.quantity.toString()
    }

    override fun getItemCount(): Int {
        return localorderlinelist.size
    }
}