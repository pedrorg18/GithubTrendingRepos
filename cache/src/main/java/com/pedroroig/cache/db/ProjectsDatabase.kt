package com.pedroroig.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pedroroig.cache.dao.CachedProjectsDao
import com.pedroroig.cache.dao.ConfigDao
import com.pedroroig.cache.model.CachedProject
import com.pedroroig.cache.model.Config
import javax.inject.Inject

@Database(entities = [CachedProject::class, Config::class], version = 1)
abstract class ProjectsDatabase @Inject constructor() : RoomDatabase() {

    abstract fun cachedProjectsDao(): CachedProjectsDao
    abstract fun configDao(): ConfigDao


    companion object {
        private var INSTANCE: ProjectsDatabase? = null
        private val lock = Any()

        fun getInstances(ctx: Context): ProjectsDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            ctx.applicationContext,
                            ProjectsDatabase::class.java,
                            "projects.db"
                        )
                            .build()
                        return INSTANCE as ProjectsDatabase
                    }
                }
            }
            return INSTANCE as ProjectsDatabase
        }
    }
}