package com.musicdiscover.Data.APIs

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

class GeniusAPI {
}


//data class Images()
data class ResultsLists(
    val meta: GeniusMeta,
    val response: GeniusResponse
)

data class GeniusMeta(
    val status: Int,
    val message: String?
)

data class GeniusResponse(
    val hits: List<Hits>
)

data class Hits(
    val type: String,
    val result: GeniusResult
)

data class GeniusResult(
    val api_path: String,
    val artist_names: String,
    val id: Int,
    val song_art_image_url: String,
    val title: String
)

interface GeniusApiEndpoints {
    @GET
    suspend fun getSong(@Url fullUrl: String): Response<ResultsLists>
}




object GeniusRetrofitHelpers {
//https://api.genius.com/search?q=yes%2C%20and%3F%20Ariana%20Grande&access_token=jTJRCLJZn1svbzXHMoKAShU9Kj59j_fJaMBH_YzcmYf0Gzo0HglqAAXnoLtCqwFr
    val baseUrl = "https://api.genius.com/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }
}