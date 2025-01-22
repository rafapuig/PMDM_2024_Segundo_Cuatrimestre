package es.rafapuig.apiservicesdemo

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import es.rafapuig.apiservicesdemo.api.MovieService
import es.rafapuig.apiservicesdemo.model.Movie
import es.rafapuig.apiservicesdemo.model.MoviesResponse
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val API_TOKEN =
    "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4YjYwYmIwMWM5M2FlZDZjNjQzYTlmNThkNWVmNTAzMCIsIm5iZiI6MTczNzU1MDc2Ni45NjcsInN1YiI6IjY3OTBlYmFlMmQ2MWMzM2U2M2RmZmI4OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.FetknARzdQhKbAQXWyxiKAW4tH33zyzZNUIxUAQzbzM"

fun main() {
    //ejemplo1()
    //ejemplo2()
    //ejemplo3()
    //ejemplo4()
    //ejemplo5()
    ejemplo6()

    println("Fin de la demo")
}

fun ejemplo1(): String {

    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://api.themoviedb.org/3/movie/now_playing?language=es-ES&page=1")
        .get()
        .addHeader("accept", "application/json")
        .addHeader("Authorization", "Bearer $API_TOKEN")
        .build()

    val call = client.newCall(request)
    val response = call.execute()

    val json = response.body?.string() ?: ""

    println(json)

    return json
}


@OptIn(ExperimentalStdlibApi::class)
fun ejemplo2() {

    val json = ejemplo1()

    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val jsonAdapter = moshi.adapter<MoviesResponse>()

    val moviesResponse = jsonAdapter.fromJson(json)

    moviesResponse?.results?.forEach {
        println(it.title)
    }
}


fun ejemplo3() {

    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val movieService = retrofit.create(MovieService::class.java)

    val call = movieService.getMovies()

    val response = call.execute()

    val moviesResponse = response.body()

    moviesResponse?.results?.forEach {
        println(it.title)
    }
}


class RequestTokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer $API_TOKEN")
            .build()

        return chain.proceed(newRequest)
    }
}


fun ejemplo4() {

    val client = OkHttpClient.Builder()
        .addInterceptor(RequestTokenInterceptor())
        .build()

    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val movieService = retrofit.create(MovieService::class.java)

    val call = movieService.getMovies2()

    val response = call.execute()

    val moviesResponse = response.body()

    moviesResponse?.results?.forEach {
        println(it.title)
    }
}


fun getMovieService(): MovieService {

    val client = OkHttpClient.Builder()
        .addInterceptor(RequestTokenInterceptor())
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


fun ejemplo5() {

    val service = getMovieService()

    runBlocking {
        val moviesResponse = service.getMoviesAsyncResponse()

        if (moviesResponse.isSuccessful) {

            val movies = moviesResponse.body()

            movies?.results?.forEach {
                println(it.title)
            }
        }
    }

}


fun getMovies(): List<Movie> {

    val service = getMovieService()

    return runBlocking {
        val moviesResponse = service.getMoviesAsync()
        return@runBlocking moviesResponse.results
    }
}

fun ejemplo6() {
    getMovies().forEach {
        println(it.title)
    }
}





