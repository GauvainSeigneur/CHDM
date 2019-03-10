package seigneur.gauvain.chdm.data.api

import io.reactivex.Flowable
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
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


    companion object {
        fun create(): CooperHewittService {


            val mHttpLoggingInterceptor = HttpLoggingInterceptor()
            mHttpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val retrofit = Retrofit.Builder()
                .client(httpClientBuilder(mHttpLoggingInterceptor).build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //basic
                //.addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create()) //extension to manage error during communication
                .baseUrl(BASE_URL)
                .build()


            return retrofit.create(CooperHewittService::class.java)


        }

        private val BASE_URL = "https://api.collection.cooperhewitt.org/rest/"
        private val CACHE_DIR = "httpCache"


        private fun httpClientBuilder(inHttpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
            return OkHttpClient.Builder()
                //enable cache strategy
               // .cache(cache)
                .addNetworkInterceptor(HeaderInterceptor())
                // Enable response caching
                //.addNetworkInterceptor(ResponseCacheInterceptor())
                //log interceptor
                .addInterceptor(inHttpLoggingInterceptor)
        }

    }

}
