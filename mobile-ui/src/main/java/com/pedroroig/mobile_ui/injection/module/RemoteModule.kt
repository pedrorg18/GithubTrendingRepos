package com.pedroroig.mobile_ui.injection.module

import com.pedroroig.data.repository.ProjectsRemote
import com.pedroroig.mobile_ui.BuildConfig
import com.pedroroig.remote.ProjectsRemoteImpl
import com.pedroroig.remote.service.GithubTrendingService
import com.pedroroig.remote.service.GithubTrendingServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideGithubService(): GithubTrendingService =
            GithubTrendingServiceFactory().makeGithubTrendingService(BuildConfig.DEBUG)
    }

    @Binds
    abstract fun bindProjectsRemote(projectsRemote: ProjectsRemoteImpl): ProjectsRemote


}