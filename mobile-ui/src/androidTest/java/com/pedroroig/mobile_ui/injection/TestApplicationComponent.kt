package com.pedroroig.mobile_ui.injection

import android.app.Application
import com.pedroroig.domain.repository.ProjectsRepository
import com.pedroroig.mobile_ui.injection.module.PresentationModule
import com.pedroroig.mobile_ui.injection.module.UiModule
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

    fun projectsRepository(): ProjectsRepository

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestApplicationComponent
    }

    fun inject(application: TestApplication)
}