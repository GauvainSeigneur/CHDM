package seigneur.gauvain.chdm

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.android.startKoin
import org.koin.standalone.KoinComponent
import seigneur.gauvain.chdm.di.networkModule
import seigneur.gauvain.chdm.di.repoModule
import seigneur.gauvain.chdm.di.viewModelModule
import seigneur.gauvain.chdm.utils.timber.TimberLog

class ChdmApp : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin(this, listOf(networkModule, repoModule, viewModelModule))
        TimberLog.init() //Init timberLog
        //setupLeakCanary()
    }

    private fun setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }


}