package seigneur.gauvain.chdm.data.api

import java.io.IOException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by gauvain on 25/02/2018.
 */
class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("Authorization", "Bearer "  + "5b24fceacf9c5564b524cf44494b3a7a")
        //builder.addHeader("Authorization", "Bearer " + TokenRepository.accessToken)
        return chain.proceed(builder.build())

    }
}
