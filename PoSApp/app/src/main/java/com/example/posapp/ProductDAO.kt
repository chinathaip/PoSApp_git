package com.example.posapp

import android.util.proto.ProtoOutputStream
import androidx.room.*


@Dao
interface ProductDAO {

    @Query("SELECT * FROM productTable")
    fun getAll():List<ProductDB>

    @Query("SELECT * FROM productTable WHERE uid = :id")
    fun loadAllByIds (id:IntArray):List<ProductDB>

    @Insert
    fun insertAll (vararg products:ProductDB)

    @Delete
    fun delete (product:ProductDB)
}