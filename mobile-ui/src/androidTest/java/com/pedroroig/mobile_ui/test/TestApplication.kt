package com.pedroroig.mobile_ui.test

import android.app.Activity
import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import com.pedroroig.mobile_ui.injection.DaggerTestApplicationComponent
import com.pedroroig.mobile_ui.injection.TestApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class TestApplication : Application(), HasActivityInjector {

    @Inject lateinit var injector: DispatchingAndroidInjector<Activity>
    private lateinit var appComponent: TestApplicationComponent

    companion object {
        fun appComponent() =
            (InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication)
                .appComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerTestApplicationComponent
            .builder()
            .application(this)
            .build()
        appComponent.inject(this)
    }
    override fun activityInjector(): AndroidInjector<Activity> = injector
}