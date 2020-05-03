package com.pedroroig.cache.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.pedroroig.cache.db.ProjectsDatabase
import com.pedroroig.cache.test.factory.ConfigDataFactory
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ConfigDaoTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.application.applicationContext,
        ProjectsDatabase::class.java
    )
        .allowMainThreadQueries()
        .build()

    private lateinit var dao: ConfigDao

    @Before
    fun setup() {
        dao = database.configDao()
    }

    @Test
    fun saveConfig_storesData() {
        val config = ConfigDataFactory.makeCachedConfig()
        dao.insertConfig(config)
        dao.getConfig().test().assertValue(config)
    }


}