package com.example.posapp

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView


class ContactListViewAdapter(private val context: Context, private val listofItem:ArrayList<Contacts>): BaseAdapter() {
    private inner class ViewHolder{
        var tvname:TextView? = null
        var tvnumber:TextView? =null
    }
    override fun getCount(): Int {
        return listofItem.size
    }

    override fun getItem(position: Int): Any {
        return listofItem[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView=convertView
        val holder:ViewHolder
        if (convertView==null){ //if the view hasnt been assigned, then assign "contacts_layout" to the listview.  contacts_layout is basically our viewholder
            holder=ViewHolder()
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater //create an inflater
            // use our contacts_layout as our viewholder
            convertView= inflater.inflate(R.layout.contacts_layout,null,true)
            holder.tvname=convertView!!.findViewById<TextView>(R.id.name)
            holder.tvnumber= convertView.findViewById<TextView>(R.id.number)
            convertView.tag=holder
        }else{
            holder= convertView.tag as ViewHolder

        }
        holder.tvname!!.setText(listofItem[position].name)
        holder.tvnumber!!.setText(listofItem[position].number)
        return convertView
    }
}