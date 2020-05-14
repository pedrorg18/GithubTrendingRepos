package com.pedroroig.mobile_ui.injection

import android.app.Application
import com.pedroroig.mobile_ui.GitHubTrendingApplication
import com.pedroroig.mobile_ui.injection.module.*
import com.pedroroig.mobile_ui.test.TestApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        TestApplicationModule::class,
        TestCacheModule::class,
        TestDataModule::class,
        PresentationModule::class,
        UiModule::class,
        TestRemoteModule::class
    ]
)
interface TestApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestApplicationComponent
    }

    fun inject(application: TestApplication)
}