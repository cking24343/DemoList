package com.sample.permissions.demolist

import android.app.Application
import com.sample.permissions.demolist.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DemoListApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DemoListApp)
            modules(appModule)
        }
    }
}