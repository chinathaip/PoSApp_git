package com.example.posapp

import android.content.Context
import androidx.room.*
import androidx.room.RoomDatabase


@Database(entities = [Order::class,OrderLine::class],version = 1)
abstract class POSAppDatabase:RoomDatabase() {
    abstract fun orderDAO() : OrderDAO
    abstract fun orderLineDAO() :OrderLineDAO

    companion object{//this creates a singleton design -- just call "getInstance" to access to this database
        private var instance :POSAppDatabase?=null

        fun getInstance(context: Context):POSAppDatabase{
            if(instance==null){ // if we've never created this database before, then create it and then return it
                instance = Room.databaseBuilder(context,POSAppDatabase::class.java,"pos_app.db").build()
            }//if already exists, just return it.
            return instance as POSAppDatabase
        }
    }
}