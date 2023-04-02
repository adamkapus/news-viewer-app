package hu.autsoft.nytimesmostpopular.data.network.interceptor

import androidx.viewbinding.BuildConfig
import hu.autsoft.nytimesmostpopular.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class NyTimesApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url
        val apiKey = BuildConfig.NYTIMES_API_KEY
        val requestBuilder = original.newBuilder()
            .url(originalHttpUrl.newBuilder().addQueryParameter("api-key", apiKey).build())
        return chain.proceed(requestBuilder.build())
    }
}