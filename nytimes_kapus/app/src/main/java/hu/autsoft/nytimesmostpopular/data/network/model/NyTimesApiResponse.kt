package hu.autsoft.nytimesmostpopular.data.network.model

import com.squareup.moshi.Json
import hu.autsoft.nytimesmostpopular.model.News

data class NyTimesApiResponse(
    val copyright: String,
    @Json(name = "num_results") val numResults: Int,
    val results: List<Article>,
    val status: String
)

data class Article(
    val id: Double?,
    val title: String?,
    @Json(name = "byline") val byLine: String?,
    @Json(name = "published_date") val publishedDate: String?,
    val url: String?,
    val media: List<Media?>?,
)

fun Article.toNews(): News? {
    if (this.id == null) {
        return null
    }

    val thumbnailUrl =
        if (this.media?.isNotEmpty() == true) {
            this.media[0]?.mediaMetadata?.get(0)?.url
        } else {
            null
        }
    val largeImageUrl =
        if (this.media?.isNotEmpty() == true) {
            this.media[0]?.mediaMetadata?.get(2)?.url
        } else {
            null
        }
    return News(
        id = this.id,
        title = this.title,
        byline = this.byLine,
        published_date = this.publishedDate,
        url = this.url,
        thumbnailUrl = thumbnailUrl,
        largeImageUrl = largeImageUrl
    )
}

data class Media(
    @Json(name = "media-metadata") val mediaMetadata: List<MediaMetadata?>?,
    @Json(name = "subtype") val subType: String?,
    val type: String?
)

data class MediaMetadata(
    val format: String?,
    val height: Int?,
    val url: String?,
    val width: Int?
)