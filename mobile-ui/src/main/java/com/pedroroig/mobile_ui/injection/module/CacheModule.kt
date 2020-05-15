package com.pedroroig.mobile_ui.injection.module

import android.app.Application
import com.pedroroig.cache.ProjectsCacheImpl
import com.pedroroig.cache.db.ProjectsDatabase
import com.pedroroig.data.repository.ProjectsCache
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CacheModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesDataBase(app: Application): ProjectsDatabase =
            ProjectsDatabase.getInstance(app)
    }

    @Binds
    abstract fun bindProjectsCache(projectsCache: ProjectsCacheImpl): ProjectsCache

}