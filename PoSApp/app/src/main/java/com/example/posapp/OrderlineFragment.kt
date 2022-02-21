package com.example.posapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderlineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderlineFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var listoforderline = arrayListOf<OrderLine>()
        val layout= inflater.inflate(R.layout.fragment_orderline, container, false)
        val ClickedOrderID = arguments?.getInt("order_local_id")//get the information passed from OrderFragment
        val localorderlineRecyclerView = layout.findViewById<RecyclerView>(R.id.rvLocalOrderLine)
        val removeAllOrderLine = arguments?.getBoolean("Remove_all_orderline")
        Log.i("REMOVEALLORDERLINE",removeAllOrderLine.toString())
        var adapter:LocalOrderLineRecyclerViewAdapter? =null //we did this so that we can refer itself inside the lambda below
        adapter = LocalOrderLineRecyclerViewAdapter(listoforderline){ orderLine ->
            if(EditOrRemove.equals("Edit")){
                //if the user clicks edit, it leads to another activity, where they can fill out new informaiton and submit changes
                val intent = Intent(this.requireContext(),EditOrderLineActivity::class.java)
                intent.putExtra("order_id",orderLine.orderID)
                intent.putExtra("product_id",orderLine.productID)
                intent.putExtra("product_price",orderLine.price)
                intent.putExtra("product_quantity",orderLine.quantity)
                startActivity(intent)
            }else if(EditOrRemove.equals("Remove")){
                val alertDialog=AlertDialog.Builder(this.requireContext())
                alertDialog.setTitle("OrderLine Remove Confirmation")
                alertDialog.setMessage("Are you sure that you want to remove this selected orderline?")
                // create a button that when clicked, it deletes the clicked orderline from db
                alertDialog.setPositiveButton("YES"){ dialong,which->
                    val index = listoforderline.indexOf(orderLine) //get the index of that orderline from the listoforderline
                    GlobalScope.launch {
                        val db=POSAppDatabase.getInstance(requireContext())
                        db.orderLineDAO().delete(orderLine) //delete that from the database
                        listoforderline.removeAt(index) //remove that orderline from the listoforderline
                    }
                    adapter?.notifyItemRemoved(index) //update the recyclerview
                    Toast.makeText(requireContext(),"the selected orderline has been removed from the database",Toast.LENGTH_SHORT).show()
                }
                // create a cancel button in case the user misclick (did not want to delete but misclicked)
                alertDialog.setNegativeButton("NO"){
                        dialong,which-> Toast.makeText(this.requireContext(),"the remove operation is canceled",Toast.LENGTH_SHORT).show()
                }
                alertDialog.show()
            }
        }



        localorderlineRecyclerView.adapter=adapter
        val db = POSAppDatabase.getInstance(this.requireContext())
        GlobalScope.launch {
            val orderlines = db.orderLineDAO().getAll()
            for(orderline in orderlines){//for each orderline in all orderline
                if(orderline.orderID.toInt()==(ClickedOrderID)){ // if this orderline is in the chosen order_id
                    if(removeAllOrderLine==true){ //did the user click "x" button on the LocalOrderRecyclerView?
                        db.orderLineDAO().delete(orderline) //if yes then delete orderline that has the same orderID
                    }else{
                        listoforderline.add(orderline)//if no, then add to the list, so that it will displayed
                    }
                }
            }
        }
        adapter.notifyDataSetChanged()
        localorderlineRecyclerView.layoutManager=LinearLayoutManager(this.requireContext())

        return layout
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderlineFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderlineFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        var EditOrRemove:String=""
    }
}