package seigneur.gauvain.chdm

import android.app.Activity
import android.app.Application

import com.squareup.leakcanary.LeakCanary

import javax.inject.Inject

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import seigneur.gauvain.chdm.di.AppComponent
import seigneur.gauvain.chdm.di.modules.NetworkModule
import seigneur.gauvain.chdm.di.modules.RoomModule
import seigneur.gauvain.chdm.di.DaggerAppComponent
import seigneur.gauvain.chdm.utils.timber.TimberLog

/**
 * Created by gse on 26/03/2018.
 */
class ChdmApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    private val applicationComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .application(this)
                .network(NetworkModule())
                .dataBase(RoomModule())
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        inject() //get Application context
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

    private fun inject() {
        applicationComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return activityInjector
    }

}