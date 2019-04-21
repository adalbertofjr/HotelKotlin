package br.com.adalbertofjr.hotel.root

import android.app.Application
import br.com.adalbertofjr.hotel.di.androidModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class HotelApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@HotelApp)
            modules(androidModule)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}