package hu.autsoft.nytimesmostpopular.data.network

import hu.autsoft.nytimesmostpopular.data.network.model.toNews
import hu.autsoft.nytimesmostpopular.model.News
import hu.autsoft.nytimesmostpopular.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkDataSource @Inject constructor(val nyTimesApi: NyTimesApi) {

    suspend fun getNewsList(period: Int = 1): Result =
        withContext(Dispatchers.IO) {
            try {
                val response = nyTimesApi.getMostViewedNews(period)
                if (response.isSuccessful) {
                    val articles = response.body()?.results
                    val data: List<News>? = articles?.mapNotNull {
                        it.toNews()
                    }
                    if (data != null) {
                        return@withContext Result.Success(data)
                    }
                    Result.Error("No data available")
                } else {
                    Result.Error("Unsuccessful request")
                }
            } catch (error: IOException) {
                Result.NetworkError("Network error during getting news")
            }
        }

}