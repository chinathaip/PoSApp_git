package com.example.posapp

import androidx.room.*


@Entity (tableName = "orderTable")
data class Order(
    @PrimaryKey (autoGenerate = true) var uid :Long?,
    @ColumnInfo (name="branch_id") var branchID : Long,
    @ColumnInfo (name = "staff_id") var staffID : Long
)


