package seigneur.gauvain.chdm.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import seigneur.gauvain.chdm.data.api.CooperHewittService
import seigneur.gauvain.chdm.data.api.HeaderInterceptor


    //Single module
    val networkModule =module {

        // single instance of HelloRepository
        single<CooperHewittService> {
            //CooperHewittService.create()
            createWebService()
        }
    }

    object DatasourceProperties {
        val BASE_URL = "https://api.collection.cooperhewitt.org/rest/"
        val CACHE_DIR = "httpCache"
    }

    //Create the webservice instance
    fun createWebService(): CooperHewittService {

        val mHttpLoggingInterceptor = HttpLoggingInterceptor()
        mHttpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val retrofit = Retrofit.Builder()
            .client(httpClientBuilder(mHttpLoggingInterceptor).build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //basic
            //.addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create()) //extension to manage error during communication
            .baseUrl(DatasourceProperties.BASE_URL)
            .build()

        return retrofit.create(CooperHewittService::class.java)
    }

   //create a http client
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




