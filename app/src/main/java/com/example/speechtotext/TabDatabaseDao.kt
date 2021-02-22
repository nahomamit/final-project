package com.example.speechtotext

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TabDatabaseDao {

    @Insert
    fun insert(tab: Tab)

    @Update
    fun update(tab: Tab)

    @Query("SELECT * from tabs_table WHERE name = :key")
    fun get(key: String): Tab?

    @Query("DELETE FROM tabs_table")
    fun clear()

    @Query("SELECT * FROM tabs_table ORDER BY name DESC")
    fun getAllTabs(): Array<Tab>

    @Query("SELECT category FROM tabs_table GROUP BY category")
    fun getCategories(): Array<String>

    @Query("SELECT difficulty FROM tabs_table GROUP BY difficulty")
    fun getDifficulty(): Array<Int>

    @Query("SELECT * FROM tabs_table WHERE category = :cate and difficulty = :diff")
    fun getTabs(cate: String, diff:Int): List<Tab>

}