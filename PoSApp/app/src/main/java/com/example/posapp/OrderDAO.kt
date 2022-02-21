package com.example.posapp

import androidx.room.*


@Dao
interface OrderDAO {
    @Query("SELECT * FROM orderTable")
    fun getAll(): List<Order> //this func gets all the data from order table and return as a list

    @Query("SELECT * FROM orderTable WHERE uid LIKE :id LIMIT 1")
    fun findByID(id:Long):Order

    @Insert
    fun insert(order:Order):Long

    @Delete
    fun delete(order:Order)

    @Query("DELETE FROM orderTable")
    fun deleteAll()
}