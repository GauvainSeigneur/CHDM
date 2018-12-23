package seigneur.gauvain.chdm.di

import android.app.Application

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import seigneur.gauvain.chdm.ChdmApp
import seigneur.gauvain.chdm.di.modules.AppModule
import seigneur.gauvain.chdm.di.modules.NetworkModule
import seigneur.gauvain.chdm.di.modules.RoomModule

/**
 * Created by gse on 26/03/2018.
 */
@Singleton
@Component(modules = [
    AppModule::class,
    NetworkModule::class,
    RoomModule::class
    ])
interface AppComponent {
    fun inject(app: ChdmApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun network(networkModule: NetworkModule): Builder
        fun dataBase(roomModule: RoomModule): Builder
        fun build(): AppComponent
    }

}


