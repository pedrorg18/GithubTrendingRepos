package com.pedroroig.mobile_ui.injection

import com.nhaarman.mockito_kotlin.mock
import com.pedroroig.data.repository.ProjectsRemote
import com.pedroroig.remote.service.GithubTrendingService
import dagger.Module
import dagger.Provides

@Module
object TestRemoteModule {

    @Provides
    @JvmStatic
    fun provideGithubService(): GithubTrendingService = mock()

    @Provides
    @JvmStatic
    fun provideProjectsRemote(): ProjectsRemote = mock()


}