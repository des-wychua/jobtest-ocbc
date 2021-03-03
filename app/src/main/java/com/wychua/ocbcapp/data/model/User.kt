package com.wychua.ocbcapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class User(
    @PrimaryKey @ColumnInfo(name = "userID") val userId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "balance") val balance: Double
)