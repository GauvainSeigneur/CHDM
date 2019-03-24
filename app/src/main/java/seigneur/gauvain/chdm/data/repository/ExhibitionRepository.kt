package seigneur.gauvain.chdm.data.repository

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import retrofit2.Response
import seigneur.gauvain.chdm.data.api.ApiSecret
import seigneur.gauvain.chdm.data.api.CooperHewittService
import seigneur.gauvain.chdm.data.model.exhibition.Exhibition
import seigneur.gauvain.chdm.data.model.exhibition.ExhibitionList
import timber.log.Timber

class ExhibitionRepository(val mCooperHewittService: CooperHewittService): KoinComponent {

    /**
     *
     */
    fun getExhibitions(): Flowable<Response<ExhibitionList>> {
        Timber.d("getExhibitions called")
        return mCooperHewittService.getExhibibitionList(ApiSecret.ACCESS_TOKEN, 1, 5)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * Load max ten objects of the Exhibitions
     */
    fun getExhibitionObjects(id:Long): Flowable<Exhibition.ExhibitionObjects>  {
        return mCooperHewittService.getObjectsOfExhibition(ApiSecret.ACCESS_TOKEN,id,1,5)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}