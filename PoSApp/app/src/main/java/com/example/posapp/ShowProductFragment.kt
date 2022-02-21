package com.example.posapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShowProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowProductFragment : Fragment() {
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
        val layout =  inflater.inflate(R.layout.fragment_show_product, container, false)
        val macaronID = arguments?.getInt("macaron_id") // this is the line to retrieve int value from ProductFragment (works just like intent.getExtra("CatID")
        Log.d("FragmentB",macaronID.toString())
        val imageview = layout.findViewById<ImageView>(R.id.showproductImageView2)
        when(macaronID){
            1001->imageview.setImageResource(R.drawable.macaron_black)
            1002->imageview.setImageResource(R.drawable.macaron_blue)
            1003->imageview.setImageResource(R.drawable.macaron_green)
            1004->imageview.setImageResource(R.drawable.macaron_navy)
            1005->imageview.setImageResource(R.drawable.macaron_pink)
            1006->imageview.setImageResource(R.drawable.macaron_red)
            1007->imageview.setImageResource(R.drawable.macaron_yellow)
            else->imageview.setImageResource(R.drawable.drink_coffee)
        }
        return layout
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShowProductFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShowProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}