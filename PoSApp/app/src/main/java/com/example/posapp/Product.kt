package com.example.posapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

open class Product(){
    open val id : Int = 0
    open val name : String = "product name"
    open val price : Int = 0
    open var quantity : Int = 1

}


//data class is not suitable for inheritance. And Product is being used by Macaron and Drinks, etc
//so we need to duplicate the class
@Entity(tableName = "productTable")
data class ProductDB(
    @PrimaryKey (autoGenerate = true)var uid :Long?,
    @ColumnInfo(name = "name")var name : String,
    @ColumnInfo(name = "price") var price : Int
)