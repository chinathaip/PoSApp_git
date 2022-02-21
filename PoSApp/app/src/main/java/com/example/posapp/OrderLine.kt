package com.example.posapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "orderLineTable")
data class OrderLine(
    @PrimaryKey (autoGenerate = true) var uid :Long?,
    @ColumnInfo(name = "order_id") var orderID:Long,
    @ColumnInfo(name="product_id") var productID:Long,
    @ColumnInfo(name="price")var price:Int,
    @ColumnInfo(name="quantity") var quantity:Int
)


