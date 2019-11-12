package com.omninos.kotlinproject.util

import android.app.Application
import com.omninos.kotlinproject.data.network.MyApi
import com.omninos.kotlinproject.data.network.NetworkConnectionInterceptor
import com.omninos.kotlinproject.data.repository.ApiRepository
import com.omninos.kotlinproject.ui.auth.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class App : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { ApiRepository(instance()) }
        bind() from singleton { ViewModelFactory(instance()) }
    }


}