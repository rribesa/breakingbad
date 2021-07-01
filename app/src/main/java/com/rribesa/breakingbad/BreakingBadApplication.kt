package com.rribesa.breakingbad

import android.app.Application
import com.rribesa.breakingbad.character.data.di.CharacterDataDI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class BreakingBadApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BreakingBadApplication)
        }
        loadKoinModules(
            listOf(
                CharacterDataDI.characterModule
            )
        )
    }
}