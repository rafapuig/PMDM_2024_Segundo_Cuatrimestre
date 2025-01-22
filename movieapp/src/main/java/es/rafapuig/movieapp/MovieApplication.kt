package es.rafapuig.movieapp

import android.app.Application
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import es.rafapuig.movieapp.data.MovieRepository
import es.rafapuig.movieapp.data.network.RequestTokenInterceptor
import es.rafapuig.movieapp.data.network.api.MovieService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieApplication : Application() {

    private val API_TOKEN =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4YjYwYmIwMWM5M2FlZDZjNjQzYTlmNThkNWVmNTAzMCIsIm5iZiI6MTczNzU1MDc2Ni45NjcsInN1YiI6IjY3OTBlYmFlMmQ2MWMzM2U2M2RmZmI4OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.FetknARzdQhKbAQXWyxiKAW4tH33zyzZNUIxUAQzbzM"


    lateinit var movieRepository: MovieRepository

    private fun getMovieService(token:String): MovieService {

        val client = OkHttpClient.Builder()
            .addInterceptor(RequestTokenInterceptor(token))
            .build()

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(MovieService::class.java)
    }

    override fun onCreate() {
        super.onCreate()

        val service = getMovieService(API_TOKEN)

        movieRepository = MovieRepository(service)
    }
}