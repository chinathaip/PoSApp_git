package com.example.posapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductFragment : Fragment() {
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

        val layout= inflater.inflate(R.layout.fragment_product, container, false)
        val productlistRecyclerView =layout.findViewById<RecyclerView>(R.id.rvProductlistFragment)
        val macaron = ProductItem.createMacaronList()
        val adapter = ProductRecyclerViewAdapter(macaron){ macaron->
            //this is the onclicklistener of the viewholder
            Log.d("IN FRAGMENT", macaron.name)

            //pass information from one fragment to another
            val arg = Bundle() //create a bundle instance
            arg.putInt("macaron_id",macaron.id) //similar as "intent.putExtra("CatID",MACARON_CAT_ID")", and "intent.getExtra("CatID")
            val fragmentB:FragmentContainerView? = inflater.inflate(R.layout.activity_product_crudactivity,container,false).findViewById(R.id.fragmentB)
            //if fragmentB doesnt exist in activity_product_crudactivity(smaller screen wont have it), this returns null
            if(fragmentB==null){//if the screen is small
                fragmentManager?.commit {
                    setReorderingAllowed(true)
                    //replace whatever is in the fragmentA with the one in ShowProductFragment
                    //second and third parameters are used to pass information from one fragment to another (like intent.putExtra and intent.getExtra)
                    replace<ShowProductFragment>(R.id.fragmentA,"macaron_id",arg)
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    addToBackStack(null)
                }
            }else{// if the screen is xlarge
                fragmentManager?.commit {
                    setReorderingAllowed(true)
                    replace<ShowProductFragment>(R.id.fragmentB,"macaron_id",arg)
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                    addToBackStack(null)
                // no need to add to backstack, we wont be able to exit to the previous page because the stack will be filled with friking macaron images LOL
                }
            }
        }

        //TODO(Use ALL_CAT_ID when showing EVERY item not just macaron images)
        OrderActivity.catID= MACARON_CAT_ID

        productlistRecyclerView.adapter=adapter
        productlistRecyclerView.layoutManager=LinearLayoutManager(layout.context)
        return layout
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductFragment.
         */

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}