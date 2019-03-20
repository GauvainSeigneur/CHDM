package seigneur.gauvain.chdm.data.repository

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import seigneur.gauvain.chdm.data.api.ApiSecret
import seigneur.gauvain.chdm.data.api.CooperHewittService
import seigneur.gauvain.chdm.data.model.chdmObject.ChdmObjectResponse

class ObjectRepository(val mCooperHewittService:CooperHewittService):KoinComponent {

    //load max ten objects of the Exhibitions
    fun getRandomObject(): Single<ChdmObjectResponse> {
        return mCooperHewittService.getRandomObject(ApiSecret.ACCESS_TOKEN,1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
