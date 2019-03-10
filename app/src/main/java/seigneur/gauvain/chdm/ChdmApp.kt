package seigneur.gauvain.chdm

import android.app.Activity
import android.app.Application
import com.squareup.leakcanary.LeakCanary
import seigneur.gauvain.chdm.utils.timber.TimberLog

class ChdmApp : Application() {

    override fun onCreate() {
        super.onCreate()
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