package seigneur.gauvain.chdm.data.repository

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import retrofit2.Response
import seigneur.gauvain.chdm.data.api.ApiSecret
import seigneur.gauvain.chdm.data.api.CooperHewittService

class InfoRepository(val mCooperHewittService:CooperHewittService):KoinComponent {

    //Get opening cafe hours
    fun getCafeHours(): Single<Response<Void>>?  {
        return mCooperHewittService.getCafeHours(ApiSecret.ACCESS_TOKEN)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    //Get opening galleries hours
    fun getGalleriesHours(): Single<Response<Void>>?  {
        return mCooperHewittService.getGalleriesOpeningHours(ApiSecret.ACCESS_TOKEN)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}
