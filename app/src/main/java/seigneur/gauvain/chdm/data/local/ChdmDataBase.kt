package seigneur.gauvain.chdm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import seigneur.gauvain.chdm.data.local.dao.TokenDao
import seigneur.gauvain.chdm.data.model.Token
import seigneur.gauvain.chdm.utils.RoomConverter

@Database(entities = [Token::class], version = 1)
@TypeConverters(RoomConverter::class)
abstract class ChdmDataBase : RoomDatabase() {
        // DAO
    abstract fun tokenDao(): TokenDao
}


