package seigneur.gauvain.chdm.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import io.reactivex.Maybe
import seigneur.gauvain.chdm.data.model.Token

@Dao
interface TokenDao {

    @get:Query("SELECT * FROM token")
    val token: Maybe<Token>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToken(token: Token): Long

}
