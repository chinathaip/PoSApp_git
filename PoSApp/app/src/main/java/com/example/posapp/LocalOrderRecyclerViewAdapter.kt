package com.example.posapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LocalOrderRecyclerViewAdapter(private val localorderlist:List<Order>,private val onOrderClicked:(Order)->Unit):RecyclerView.Adapter<LocalOrderRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(ListitemView: View,onOrderClicked_fun:(Int)->Unit):RecyclerView.ViewHolder(ListitemView){
        val localorderid = ListitemView.findViewById<TextView>(R.id.order_localid_text)
        val localorderbranchid = ListitemView.findViewById<TextView>(R.id.order_branchid_text)
        val localorderstaffid = ListitemView.findViewById<TextView>(R.id.order_staffid_text)
        val removeOrderBtn = ListitemView.findViewById<Button>(R.id.item_removeBtn)
        init{
            ListitemView.setOnClickListener {
                OrderFragment.OrderFragmentOperation="ViewOrderLine"
                onOrderClicked_fun(adapterPosition)
            }
            removeOrderBtn.setOnClickListener {
                OrderFragment.OrderFragmentOperation="RemoveOrder"
                onOrderClicked_fun(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val context = parent.context
        val localOrderView = LayoutInflater.from(context).inflate(R.layout.localorder_viewholder_layout,parent,false)
        return ViewHolder(localOrderView){
            onOrderClicked(localorderlist[it])
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = localorderlist[position]
        holder.localorderid.text = item.uid.toString()
        holder.localorderbranchid.text = item.branchID.toString()
        holder.localorderstaffid.text = item.staffID.toString()

    }

    override fun getItemCount(): Int {
        return localorderlist.size
    }

}