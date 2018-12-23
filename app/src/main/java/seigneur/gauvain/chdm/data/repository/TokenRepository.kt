package seigneur.gauvain.chdm.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import seigneur.gauvain.chdm.data.local.dao.TokenDao
import seigneur.gauvain.chdm.data.model.Token

@Singleton
class TokenRepository

@Inject
constructor() {

    @Inject
    lateinit var mTokenDao: TokenDao

    val accessTokenFromDB: Maybe<Token>
        get() = mTokenDao.token
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())

    /**
     * TODO - use cyphering to store a crypted version of the token...
     * @param token
     * @return
     */
    fun insertToken(token: Token): Completable {
        accessToken = token.accessToken
        return Completable.fromAction { mTokenDao.insertToken(token) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    companion object {
        //Access Token for API request
        var accessToken: String=""
        var accessTokenQuery="&access_token="+accessToken
    }

}
