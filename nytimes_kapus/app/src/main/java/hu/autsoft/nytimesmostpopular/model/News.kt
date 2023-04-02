package hu.autsoft.nytimesmostpopular.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News(
    @PrimaryKey
    val id: Double,
    val title: String? = null,
    val byline: String? = null,
    val published_date: String? = null,
    val url: String? = null,
    val thumbnailUrl: String? = null,
    val largeImageUrl: String? = null
)

