package seigneur.gauvain.chdm.data.repository

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import retrofit2.Response
import seigneur.gauvain.chdm.data.api.ApiSecret
import seigneur.gauvain.chdm.data.api.CooperHewittService
import seigneur.gauvain.chdm.data.model.exhibition.Exhibition
import seigneur.gauvain.chdm.data.model.exhibition.ExhibitionList
import timber.log.Timber

class ApiTestRepository(val mCooperHewittService:CooperHewittService):KoinComponent {

    // lazy inject Koin instance
    //val mCooperHewittService : CooperHewittService by inject()

    //get hours
    fun getHours(): Single<Response<Void>>?  {
        return mCooperHewittService.getCafeHours(ApiSecret.ACCESS_TOKEN)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getExhibitions(): Flowable<ExhibitionList> ? {
        Timber.d("getExhibitions called")
        return mCooperHewittService.getExhibitions(ApiSecret.ACCESS_TOKEN,1,10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getExhibitionsForRV(page: Long, perPage: Int): Flowable<ExhibitionList>  {
        return mCooperHewittService.getExhibitions(ApiSecret.ACCESS_TOKEN,page,perPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    //load max ten objects of the Exhibitions
    fun getObjects(id:Long): Flowable<Exhibition.ExhibitionObjects>  {
        return mCooperHewittService.getObjectsOfExhibition(ApiSecret.ACCESS_TOKEN,id,1,10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
