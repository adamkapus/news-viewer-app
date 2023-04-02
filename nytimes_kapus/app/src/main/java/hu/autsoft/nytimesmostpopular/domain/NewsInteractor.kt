package hu.autsoft.nytimesmostpopular.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.autsoft.nytimesmostpopular.data.database.LocalDataSource
import hu.autsoft.nytimesmostpopular.data.network.NetworkDataSource
import hu.autsoft.nytimesmostpopular.model.News
import hu.autsoft.nytimesmostpopular.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsInteractor @Inject constructor(
    val localDataSource: LocalDataSource,
    val networkDataSource: NetworkDataSource
) {
    private val _newsResult = MutableLiveData<Result>()
    val newsResult: LiveData<Result> = _newsResult

    suspend fun loadNews(forceUpdate: Boolean = false) = withContext(Dispatchers.IO) {
        if (forceUpdate) {
            refreshNews()
        } else {
            loadNewsFromDb()
        }
    }

    suspend fun getSingleNews(id: Double): News? = withContext(Dispatchers.IO) {
        localDataSource.getNewsById(id)
    }

    private suspend fun refreshNews() = withContext(Dispatchers.IO) {
        val result = fetchNews()
        if (result is Result.Success) {
            saveNewsToDb(result.data)
        }
        _newsResult.postValue(result)
    }

    private suspend fun loadNewsFromDb() = withContext(Dispatchers.IO) {
        val newsList = localDataSource.getAllNews()
        _newsResult.postValue(Result.Success(newsList))
    }

    private suspend fun fetchNews(): Result {
        return networkDataSource.getNewsList()
    }

    private suspend fun saveNewsToDb(newNewsList: List<News>) = withContext(Dispatchers.IO) {
        localDataSource.deleteAllNews()
        for (n in newNewsList) {
            localDataSource.insertNews(n)
        }
    }

}