package seigneur.gauvain.chdm.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import seigneur.gauvain.chdm.ui.main.MainActivity
import seigneur.gauvain.chdm.ui.main.MainActivityModule
import seigneur.gauvain.chdm.di.scope.PerActivity
import seigneur.gauvain.chdm.ui.exhibition.ExhibitionListActivity
import seigneur.gauvain.chdm.ui.exhibition.ExhibitionListActivityModule

/**
 * Created by Gauvain on 26/03/2018.
 * Module which allows activities to have access to injected dependencies
 */
@Module(includes = [AndroidSupportInjectionModule::class, ViewModelModule::class])
abstract class AppModule {
    /**
     * Provides the injector for the [ExhibitionListActivity], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = [ExhibitionListActivityModule::class])
    internal abstract fun exhibitionListActivityInjector(): ExhibitionListActivity

    /**
     * Provides the injector for the [MainActivity], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun mainActivityInjector(): MainActivity
}