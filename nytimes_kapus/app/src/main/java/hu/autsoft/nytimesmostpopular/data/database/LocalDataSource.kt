package hu.autsoft.nytimesmostpopular.data.database

import hu.autsoft.nytimesmostpopular.model.News
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(val newsDao : NewsDao) {

    fun insertNews(news: News){
        newsDao.insertNews(news)
    }

    fun getAllNews(): List<News> {
        return newsDao.getAllNews()
    }

    fun deleteAllNews() {
        newsDao.deleteAllNews()
    }

    fun getNewsById(id: Double?): News? {
        return newsDao.getNewsById(id)
    }
}