package com.pedroroig.mobile_ui.injection

import com.nhaarman.mockito_kotlin.mock
import com.pedroroig.domain.repository.ProjectsRepository
import dagger.Module
import dagger.Provides

@Module
object TestCacheModule {

        @Provides
        @JvmStatic
        fun providesDataRepository(): ProjectsRepository = mock()

}