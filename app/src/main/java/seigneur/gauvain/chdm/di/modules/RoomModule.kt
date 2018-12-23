package seigneur.gauvain.chdm.di.modules

import android.app.Application
import android.arch.persistence.room.Room

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import seigneur.gauvain.chdm.data.local.ChdmDataBase
import seigneur.gauvain.chdm.data.local.dao.TokenDao

@Module
class RoomModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): ChdmDataBase {
        return Room.databaseBuilder(application,
            ChdmDataBase::class.java, "ChdmDataBase.db")
                //.fallbackToDestructiveMigration()//cleared before migrate
                .build()
    }

    @Provides
    @Singleton
    internal fun provideTokenDao(database: ChdmDataBase): TokenDao {
        return database.tokenDao()
    }

}
