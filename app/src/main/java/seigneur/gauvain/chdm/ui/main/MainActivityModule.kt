package seigneur.gauvain.chdm.ui.main

import android.app.Activity

import dagger.Binds
import dagger.Module
import seigneur.gauvain.chdm.di.scope.PerActivity

/**
 * Provides main activity dependencies.
 */
@Module
abstract class MainActivityModule {
    /**
     * @param mainActivity the activity
     * @return the activity
     */
    @Binds
    @PerActivity
    internal abstract fun activity(mainActivity: MainActivity): Activity
}