package com.pedroroig.mobile_ui.injection.module

import com.pedroroig.domain.executor.PostExecutionThread
import com.pedroroig.mobile_ui.browse.BrowseActivity
import com.pedroroig.mobile_ui.UiThread
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutorThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesBrowseActivity(): BrowseActivity
}