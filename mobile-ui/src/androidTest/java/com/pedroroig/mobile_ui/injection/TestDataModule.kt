package com.pedroroig.mobile_ui.injection

import android.app.Application
import com.nhaarman.mockito_kotlin.mock
import com.pedroroig.cache.ProjectsCacheImpl
import com.pedroroig.cache.db.ProjectsDatabase
import com.pedroroig.data.repository.ProjectsCache
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
object TestDataModule {

    @Provides
    @JvmStatic
    fun providesDataBase(app: Application): ProjectsDatabase =
        ProjectsDatabase.getInstances(app)

    @Provides
    @JvmStatic
    fun provideProjectsCache(): ProjectsCache = mock()

}