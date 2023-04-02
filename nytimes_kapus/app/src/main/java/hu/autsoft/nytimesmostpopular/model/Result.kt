package hu.autsoft.nytimesmostpopular.model

sealed class Result {
    data class Success(val data: List<News>) : Result()
    data class Error(val error: String) : Result()
    data class NetworkError(val error: String) : Result()
}
