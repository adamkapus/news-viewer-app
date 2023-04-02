package hu.autsoft.nytimesmostpopular.data.network


import hu.autsoft.nytimesmostpopular.data.network.model.NyTimesApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

//example call
//https://api.nytimes.com/svc/mostpopular/v2/viewed/1.json?api-key=yourkey

interface NyTimesApi {
    companion object {
        const val ENDPOINT_URL = "https://api.nytimes.com/svc/mostpopular/v2/viewed/"
    }

    @GET("{period}.json")
    suspend fun getMostViewedNews(@Path("period") period : Int): Response<NyTimesApiResponse>
}