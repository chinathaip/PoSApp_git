package com.example.posapp

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleySingleton constructor(context: Context) {
    companion object{
        private var instance:VolleySingleton?=null
        fun getInstance(context: Context)=
            instance?: synchronized(this){ //if instance is null, then do the code below
                instance ?: VolleySingleton(context).also{
                    instance=it
                }
            }
    }
    private val requestQueue :RequestQueue by lazy{
        Volley.newRequestQueue(context.applicationContext)
        //applicationContext prevents activity or broadcast receiver leak
    }

    fun <T>addToRequestQueue (request: Request<T>){
        requestQueue.add(request)
    }

}