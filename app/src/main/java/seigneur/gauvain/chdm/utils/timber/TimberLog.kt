package seigneur.gauvain.chdm.utils.timber

import seigneur.gauvain.chdm.BuildConfig
import timber.log.Timber

object TimberLog {

    fun init() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        else
            Timber.plant(TimberReleaseTree())
    }
}