package hu.autsoft.nytimesmostpopular.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import hu.autsoft.nytimesmostpopular.model.News

@Dao
interface NewsDao {

    @Insert
    fun insertNews(news: News)

    @Query("SELECT * FROM news")
    fun getAllNews(): List<News>

    @Query("DELETE FROM news")
    fun deleteAllNews()

    @Query("SELECT * FROM news WHERE id == :id")
    fun getNewsById(id: Double?): News?

}