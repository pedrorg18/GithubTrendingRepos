package com.pedroroig.mobile_ui

import android.app.Activity
import android.app.Application
import com.pedroroig.mobile_ui.injection.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber

class GitHubTrendingApplication : Application(), HasActivityInjector {

    private lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        setupTimber()

        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

    override fun activityInjector(): AndroidInjector<Activity> =
        androidInjector
}