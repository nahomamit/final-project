package com.example.speechtotext

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

class TabDatabaseTest {
    private lateinit var tabsDao: TabDatabaseDao
    private lateinit var db: TabDataBase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, TabDataBase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        tabsDao = db.tabDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetTab() {
        val tab = Tab(0, "apple", "apple.jpg","fruit",1 )
        val tab2 = Tab(0, "banana", "banana.jpg","fruit",1 )
        val tab3 = Tab(0, "orange", "orange.jpg","fruit",1 )

        tabsDao.insert(tab)
        tabsDao.insert(tab2)
        tabsDao.insert(tab3)
        val list = tabsDao.getAllTabs()

    /*
        val apple = tabsDao.get(1)
        assertEquals(apple?.name, "apple")
        val banana = tabsDao.get(2)
        assertEquals(banana?.name, "banana")
        val orange = tabsDao.get(3)
        assertEquals(orange?.name, "orange")
        */


    }

}