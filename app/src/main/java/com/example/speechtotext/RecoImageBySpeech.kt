package com.example.speechtotext

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_reco_image_by_speech.*

class RecoImageBySpeech : AppCompatActivity() {
    private lateinit var tabsDao: TabDatabaseDao
    private lateinit var db: TabDataBase
    private val RQ_SPEECH_REC = 102
    private var tabs = ArrayList<Tab>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reco_image_by_speech)

        db = Room.databaseBuilder(
            applicationContext,
            TabDataBase::class.java,
            "tabs_database"
        ).allowMainThreadQueries().build()
        tabsDao = db.tabDao


        val catagory = intent.getStringExtra("Start_Session_Catagory_Key").toString()
        val difficulty = intent.getStringExtra("Start_Session_Difficulty_Key").toInt()

        //createListOfTabs()
        setTabTarget(catagory, difficulty)

        /*
        var list = tabsDao.getAllTabs()
        try {
            for (i in 0 until list.size)
                Log.i("@Amit", list[i].toString())
        } catch (e: Exception) {
            Log.e("Error", e.toString())
        }

         */

        btn_button.setOnClickListener(){ askSpeechInput()}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (result?.get(0).toString() == qes_text.text) {
                tv_text.text = "Correct !"
                if(tabs.size > 0)
                    session()
                else {
                    Toast.makeText(this, "END!!!", Toast.LENGTH_LONG).show()
                }
            }
            else
                tv_text.text = "Wrong !"
        }
    }

    private fun askSpeechInput() {
        if(!SpeechRecognizer.isRecognitionAvailable(this)){
            Toast.makeText(this, "Speech recogntion is not aviable", Toast.LENGTH_SHORT).show()
        } else {
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "he")
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Somathing !")
            startActivityForResult(i, RQ_SPEECH_REC)
        }
    }


    private fun createListOfTabs() {
        tabsDao.clear()
        val tabs = listOf<Tab>(
            Tab("תפוח", "apple","fruit",1 ),
            Tab( "בננה", "banana","fruit",1 ),
            Tab( "תפוז", "orange","fruit",1 ),
            Tab( "אגס", "pear","fruit",1 ),
            Tab( "אפרסק", "peach","fruit",1 ),
            Tab( "ענבים", "grape","fruit",1 ),
            Tab( "מלון", "melon","fruit",1 ),
            Tab("אבטיח", "watermelon","fruit",1 ),
            Tab( "דובדבן", "cherrie","fruit",1 ))

        for(t in tabs)
            tabsDao.insert(t)


    }

    private fun setTabTarget(category: String, diff:Int) {
        val listTabs = tabsDao.getTabs(category , diff)
        tabs.addAll(listTabs)
        session()
    }

    private fun session() {
        Log.i("@ListTabs", tabs.toString())
        var size = tabs.size
        val rnds = (0..size-1).random()

        qes_text.text = tabs[rnds].name

        var filename = tabs[rnds].url
        var id = getResources().getIdentifier(filename,"drawable", getPackageName())
        image.setImageResource(id)
        var tempList = ArrayList<Tab>()
        for(t in tabs) {
            if (t != tabs[rnds]) {
                tempList.add(t)
            }
        }
        tabs = ArrayList(tempList)
        Log.i("@ListTabs_AfterDrop", tabs.toString())


    }
}