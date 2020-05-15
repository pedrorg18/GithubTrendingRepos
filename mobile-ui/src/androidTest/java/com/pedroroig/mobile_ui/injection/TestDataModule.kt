package com.pedroroig.mobile_ui.injection

import com.nhaarman.mockito_kotlin.mock
import com.pedroroig.domain.repository.ProjectsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestDataModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideDataRepository(): ProjectsRepository {
        return mock()
    }

}