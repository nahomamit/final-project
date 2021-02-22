package com.example.speechtotext
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabs_table")
data class Tab(
    @PrimaryKey
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "url")
    var url:String,
    @ColumnInfo(name = "category")
    var category:String,
    @ColumnInfo(name = "difficulty")
    var difficulty:Int
)