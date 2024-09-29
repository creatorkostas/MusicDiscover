package com.musicdiscover.Data.APIs

//@GET("users")
//suspend fun getUser(): Response<User>

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

//data class ImagesList(
//    var track: List<Images>
//)

//data class Images()
data class ResultsList(
    val results: Results
)

data class Results(
    val trackmatches: Tracks
)

data class Tracks(
    var track: List<TracksInfo>
)

data class TracksInfo(
    val name: String,
    val artist: String
)
interface ApiEndpoints {
//    @GET("?method=track.search&track={name}&api_key=979058be5971b3f2282a21f6cc28f148&format=json")
//    @GET("{fullUrl}")
    @GET
    suspend fun getSong(@Url fullUrl: String): Response<ResultsList>
//    suspend fun getSong(@Path(value = "fullUrl", encoded = true) fullUrl: String): Response<ResultsList>
//    suspend fun getSong(@Path("name") songName: String): Response<ResultsList>
}




object LastFmRetrofitHelper {

    val baseUrl = "https://ws.audioscrobbler.com/2.0/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }
}