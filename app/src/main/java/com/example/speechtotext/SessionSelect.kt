package com.example.speechtotext

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_session_select.*

class SessionSelect : AppCompatActivity() {
    private lateinit var tabsDao: TabDatabaseDao
    private lateinit var db: TabDataBase
    var catagory_selected = ""
    var diff_selected = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_select)

        db = Room.databaseBuilder(
            applicationContext,
            TabDataBase::class.java,
            "tabs_database"
        ).allowMainThreadQueries().build()
        tabsDao = db.tabDao

        val categories = tabsDao.getCategories()
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        catagorySpinner.adapter = arrayAdapter

        val diff = tabsDao.getDifficulty()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, diff)
        difficultySpinner.adapter = adapter

        catagorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                catagory_selected = categories[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        difficultySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                diff_selected = diff[position].toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        startSession_btn.setOnClickListener(){ startOnClick()}

    }

    private fun startOnClick() {
        val catagory_selected = catagory_selected
        val diff_selected = diff_selected
        val intent = Intent(this, RecoImageBySpeech::class.java)
        .apply {
            putExtra("Start_Session_Catagory_Key", catagory_selected)
            putExtra("Start_Session_Difficulty_Key", diff_selected)
        }
        startActivity(intent)


    }
}