package es.rafapuig.apiservicesdemo

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import es.rafapuig.apiservicesdemo.api.MovieService
import es.rafapuig.apiservicesdemo.model.Movie
import es.rafapuig.apiservicesdemo.model.MoviesResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.coroutines.CoroutineContext

const val API_TOKEN =
    "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4YjYwYmIwMWM5M2FlZDZjNjQzYTlmNThkNWVmNTAzMCIsIm5iZiI6MTczNzU1MDc2Ni45NjcsInN1YiI6IjY3OTBlYmFlMmQ2MWMzM2U2M2RmZmI4OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.FetknARzdQhKbAQXWyxiKAW4tH33zyzZNUIxUAQzbzM"

fun main() {
    //ejemploOkHttp()
    //ejemploMoshi()
    //ejemploRetrofit()
    //ejemploRetrofit2()
    //ejemploRetrofitCoroutines()
    ejemplo6()

    println("Fin de la demo")
}

fun ejemploOkHttp() {
    println(getMoviesJSON())
}

fun getMoviesJSON(): String {

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

    return json
}


@OptIn(ExperimentalStdlibApi::class)
fun ejemploMoshi() {

    val json = getMoviesJSON()

    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val jsonAdapter = moshi.adapter<MoviesResponse>()

    val moviesResponse = jsonAdapter.fromJson(json)

    printMoviesTitle(moviesResponse)
}


private fun printMoviesTitle(moviesResponse: MoviesResponse?) {
    moviesResponse?.results?.forEach {
        println(it.title)
    }
}


fun ejemploRetrofit() {

    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val movieService = retrofit.create(MovieService::class.java)

    val call = movieService.getMoviesWithHeaders()

    val response = call.execute()

    val moviesResponse = response.body()

    moviesResponse?.results?.forEach {
        println(it.title)
    }
}


class RequestTokenInterceptor(private val token: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(newRequest)
    }
}


fun ejemploRetrofitInterceptor() {

    val client = OkHttpClient.Builder()
        .addInterceptor {
            it.proceed(
                it.request().newBuilder()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer $API_TOKEN")
                    .build()
            )
        }
        //.addInterceptor(RequestTokenInterceptor(API_TOKEN))
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

    val call = movieService.getNowPlayingMovies()

    val response = call.execute()

    val moviesResponse = response.body()

    moviesResponse?.results?.forEach {
        println(it.title)
    }
}


fun getMovieService(): MovieService {

    val client = OkHttpClient.Builder()
        .addInterceptor(RequestTokenInterceptor(API_TOKEN))
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


fun ejemploRetrofitCoroutines() {

    val service = getMovieService()

    runBlocking {
        val moviesResponse = service.getNowPlayingMoviesAsyncResponse()

        if (moviesResponse.isSuccessful) {

            val movies = moviesResponse.body()

            movies?.results?.forEach {
                println(it.title)
            }
        }
    }

}


fun getMovies(context: CoroutineContext): List<Movie> {

    val service = getMovieService()

    return runBlocking(context) {
        val moviesResponse = service.getNowPlayingMoviesAsync()
        return@runBlocking moviesResponse.results
    }

}

fun ejemplo6() {
    val scope = CoroutineScope(Dispatchers.IO)

    getMovies(scope.coroutineContext).forEach {
        println(it.title)
    }
    scope.cancel()

}

fun ejemploRetrofitCoroutines1() {
    val service = getMovieService();

    runBlocking {

        val response = service.getNowPlayingMoviesAsyncResponse(page = 1)
        if (response.isSuccessful) {
            printMoviesTitle(response.body())
        }  else {
            println(response.errorBody()?.string())
        }
    }
}

fun ejemploRetrofitCoroutines2() {
    val service = getMovieService();

    runBlocking {
        try {
            val moviesResponse = service.getNowPlayingMoviesAsync(page = 1)
            printMoviesTitle(moviesResponse)
        } catch (ex: HttpException) {
            println(ex.message())
        }
    }
}





