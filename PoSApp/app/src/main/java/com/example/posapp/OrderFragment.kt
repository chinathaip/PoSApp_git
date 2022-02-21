package com.example.posapp

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderFragment : Fragment() {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val layout= inflater.inflate(R.layout.fragment_order, container, false)
        val localorderRecyclerView = layout.findViewById<RecyclerView>(R.id.rvLocalOrder)
        val db = POSAppDatabase.getInstance(this.requireContext())
        var localorderList= arrayListOf<Order>()
        //retrieve orderline data from our local database, and put in "localorderlist"
        GlobalScope.launch {
            val orders = db.orderDAO().getAll()
            localorderList.addAll(orders)
        }
        val arg = Bundle()
        var adapter:LocalOrderRecyclerViewAdapter?=null
        adapter=LocalOrderRecyclerViewAdapter(localorderList){ order->
            Log.i("OrderFragmentViewHolder","Order clicked ${order.uid}")
            arg.putInt("order_local_id", order.uid!!.toInt()) //pass information from this fragment to OrderLineFragment for further use
            fragmentManager?.commit{
                setReorderingAllowed(true)
                if(OrderFragmentOperation.equals("RemoveOrder")){
                    arg.putBoolean("Remove_all_orderline",true)
                    val index = localorderList.indexOf(order)
                    GlobalScope.launch {
                        db.orderDAO().delete(order)
                        localorderList.removeAt(index)
//                        db.orderDAO().deleteAll()
//                        db.orderLineDAO().deleteAll()
                    }
                    adapter?.notifyItemRemoved(index)
                    Toast.makeText(context,"The selected order and its orderlines are deleted",Toast.LENGTH_SHORT).show()
                }else if(OrderFragmentOperation.equals("ViewOrderLine")){
                    arg.putBoolean("Remove_all_orderline",false)
                }
                //replace whatever is in the OrderLineFragContainer with OrderlineFragment
                replace<OrderlineFragment>(R.id.OrderLineFragContainer,"XD",arg)
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            }
        }
        localorderRecyclerView.adapter=adapter
        localorderRecyclerView.layoutManager=LinearLayoutManager(this.requireContext())
        return layout
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        var OrderFragmentOperation :String=""
    }
}