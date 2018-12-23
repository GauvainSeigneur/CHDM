package seigneur.gauvain.chdm.ui.exhibition

import android.app.Activity

import dagger.Binds
import dagger.Module
import seigneur.gauvain.chdm.di.scope.PerActivity

/**
 * Provides main activity dependencies.
 */
@Module
abstract class ExhibitionListActivityModule {
    /**
     * @param exhibitionListActivity the activity
     * @return the activity
     */
    @Binds
    @PerActivity
    internal abstract fun activity(exhibitionListActivity: ExhibitionListActivity): Activity
}