package com.example.speechtotext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_reco_image_by_speech.*
import java.lang.Exception
import java.lang.reflect.Field


class MainActivity : AppCompatActivity() {

    private lateinit var tabsDao: TabDatabaseDao
    private lateinit var db: TabDataBase

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            TabDataBase::class.java,
            "tabs_database"
        ).allowMainThreadQueries().build()
        tabsDao = db.tabDao

        try {
            createListOfTabs()
        } catch (e: Exception) {
            Log.e("@ERROR", e.toString())
        }
        session_btn.setOnClickListener(){ sessionOnClick()}

    }

    private fun sessionOnClick() {
        val intent = Intent(this, SessionSelect::class.java)
        startActivity(intent)
    }

    private fun createListOfTabs() {
        val tabs = listOf<Tab>(
            Tab("תפוח", "apple", "fruit", 1),
            Tab("בננה", "banana", "fruit", 1),
            Tab("תפוז", "orange", "fruit", 1),
            Tab("אגס", "pear", "fruit", 1),
            Tab("אפרסק", "peach", "fruit", 1),
            Tab("ענבים", "grape", "fruit", 1),
            Tab("מלון", "melon", "fruit", 1),
            Tab("אבטיח", "watermelon", "fruit", 1),
            Tab("דובדבן", "cherrie", "fruit", 1),

            Tab("תות", "strawberry", "fruit", 2),
            Tab("קלמנטינה", "clementine", "fruit", 2),
            Tab("אשכולית", "grapefruit", "fruit", 2),
            Tab("אפרסמון", "persimmon", "fruit", 2),
            Tab("תמר", "Tamar", "fruit", 2),
            Tab("אננס", "pineapple", "fruit", 3),
            Tab("שזיף", "plum", "fruit", 3),
            Tab("מישמיש", "Apricot", "fruit", 3),
            Tab("שסק", "loquat", "fruit", 3),
            Tab("פומלה", "Pomelo", "fruit", 3),


            Tab("מלפפון", "cucumber", "vegetable", 1),
            Tab("עגבניה", "tomato", "vegetable", 1),
            Tab("גזר", "carrot", "vegetable", 1),
            Tab("פלפל", "pepper", "vegetable", 1),
            Tab("כרוב", "cabbage", "vegetable", 1),
            Tab("חסה", "lettuce", "vegetable", 1),
            Tab("לימון", "lemon", "vegetable", 1),
            Tab("חציל", "eggplant", "vegetable", 1),
            Tab("בטטה", "sweet_potato", "vegetable", 1),

            Tab("תפוח אדמה", "potato", "vegetable", 2),
            Tab("שום", "garlic", "vegetable", 2),
            Tab("סלק", "beet", "vegetable", 2),
            Tab("קישוא", "Squash", "vegetable", 2),
            Tab("דלעת", "pumpkin", "vegetable", 3),
            Tab("בצל", "onion", "vegetable", 3),
            Tab("ארטישוק", "artichoke", "vegetable", 3),
            Tab("פטריות", "mushrooms", "vegetable", 3),
            Tab("זיתים", "olive", "vegetable", 3)


        )

        for (t in tabs)
            tabsDao.insert(t)
    }

}