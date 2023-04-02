package hu.autsoft.nytimesmostpopular.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.autsoft.nytimesmostpopular.model.News

@Database(
    version = 1,
    exportSchema = false,
    entities = [News::class]
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}
