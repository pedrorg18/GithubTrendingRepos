package com.pedroroig.mobile_ui.injection

import android.app.Application
import com.nhaarman.mockito_kotlin.mock
import com.pedroroig.cache.db.ProjectsDatabase
import com.pedroroig.data.repository.ProjectsCache
import dagger.Module
import dagger.Provides

@Module
object TestCacheModule {

    @Provides
    @JvmStatic
    fun provideDatabase(application: Application): ProjectsDatabase {
        return ProjectsDatabase.getInstance(application)
    }

    @Provides
    @JvmStatic
    fun provideProjectsCache(): ProjectsCache {
        return mock()
    }

}