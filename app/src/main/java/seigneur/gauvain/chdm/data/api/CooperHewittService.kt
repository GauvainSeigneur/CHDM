package seigneur.gauvain.chdm.data.api

import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import seigneur.gauvain.chdm.data.model.exhibition.Exhibition
import seigneur.gauvain.chdm.data.model.exhibition.ExhibitionList

/**
 * Created by Gauvain on 25/02/2018.
 */
interface CooperHewittService {

    @GET("?method=cooperhewitt.cafe.openingHours")
    fun getCafeHours(
        @Query("access_token") accessToken: String):
            Single<Response<Void>>

    @GET("?method=cooperhewitt.exhibitions.getList")
    fun getExhibitions(
        @Query("access_token") accessToken: String,
        @Query("page") page: Long,
        @Query("per_page") pagePage: Int
    ): Flowable<ExhibitionList>

    @GET("?method=cooperhewitt.exhibitions.getObjects")
    fun getObjectsOfExhibition(
        @Query("access_token") accessToken: String,
        @Query("exhibition_id") id: Long,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Flowable<Exhibition.ExhibitionObjects>

}
