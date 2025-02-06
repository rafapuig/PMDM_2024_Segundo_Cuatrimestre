package es.rafapuig.movieapp.data.network.provider

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import es.rafapuig.movieapp.data.network.RequestTokenInterceptor
import es.rafapuig.movieapp.data.network.api.MovieService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object MovieNetworkProvider {

    private const val API_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4YjYwYmIwMWM5M2FlZDZjNjQzY" +
            "TlmNThkNWVmNTAzMCIsIm5iZiI6MTczNzU1MDc2Ni45NjcsInN1YiI6IjY3OTBlYmFlMmQ2MWM" +
            "zM2U2M2RmZmI4OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.FetknARzdQ" +
            "hKbAQXWyxiKAW4tH33zyzZNUIxUAQzbzM"

    private const val API_SERVICE_BASE_URL = "https://api.themoviedb.org/3/"


    fun getMovieApiService() : MovieService {
        return getMovieService(API_TOKEN)
    }


    private fun getMovieService(token: String): MovieService {

        val client = OkHttpClient.Builder()
            .addInterceptor(RequestTokenInterceptor(token))
            .build()

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(API_SERVICE_BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(MovieService::class.java)
    }

}