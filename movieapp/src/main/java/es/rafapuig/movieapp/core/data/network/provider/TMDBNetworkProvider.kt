package es.rafapuig.movieapp.core.data.network.provider

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object TMDBNetworkProvider {

    private const val API_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4YjYwYmIwMWM5M2FlZDZjNjQzY" +
            "TlmNThkNWVmNTAzMCIsIm5iZiI6MTczNzU1MDc2Ni45NjcsInN1YiI6IjY3OTBlYmFlMmQ2MWM" +
            "zM2U2M2RmZmI4OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.FetknARzdQ" +
            "hKbAQXWyxiKAW4tH33zyzZNUIxUAQzbzM"

    private const val API_SERVICE_BASE_URL = "https://api.themoviedb.org/3/"


    private fun getTNDBRetrofit(token: String): Retrofit {

        val tokenClient = OkHttpClient.Builder()
            .addInterceptor {
                it.proceed(
                    it.request().newBuilder()
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                )
            }
            //.addInterceptor(RequestTokenInterceptor(token)) // Inserción del token en la request
            .build()

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(API_SERVICE_BASE_URL)
            .client(tokenClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit
    }



    fun provideMoshi() = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    fun provideConverterFactory() = MoshiConverterFactory.create(provideMoshi())

    fun provideOkHttpClient(token: String) = OkHttpClient.Builder()
        .addInterceptor {
            it.proceed(
                it.request().newBuilder()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            )
        }
        .build()


    fun provideRetrofit(
        client: OkHttpClient,
        converterFactory: Converter.Factory
    ) = Retrofit.Builder()
        .baseUrl(API_SERVICE_BASE_URL)
        .client(client)
        .addConverterFactory(converterFactory)
        .build()


    fun provideRetrofit(): Retrofit =
        provideRetrofit(
            provideOkHttpClient(API_TOKEN),
            provideConverterFactory()
        )

}