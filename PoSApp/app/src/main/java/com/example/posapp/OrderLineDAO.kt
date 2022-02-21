package com.example.posapp

import androidx.room.*


@Dao
interface OrderLineDAO {

    @Query("SELECT * FROM orderLineTable")
    fun getAll(): List<OrderLine> //this func gets all the data from order table and return as a list

    @Query("SELECT * FROM orderLineTable WHERE uid LIKE :id LIMIT 1")
    fun findByID(id:Long):OrderLine

    @Insert
    fun insertAll(orderLine:OrderLine):Long // vararg = n number of parameters. Decide the number of parameters at runtime

    @Delete
    fun delete(orderLine:OrderLine)

    @Query("DELETE FROM orderLineTable")
    fun deleteAll()

    @Query("UPDATE orderLineTable SET price=:pprice,quantity=:qquantity WHERE product_id LIKE :productid")
    fun update(productid:Long,pprice:Int,qquantity:Int)

    @Update
    fun updatelol(orderLine: OrderLine)
}