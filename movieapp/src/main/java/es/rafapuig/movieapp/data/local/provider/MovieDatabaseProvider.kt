package es.rafapuig.movieapp.data.local.provider

import android.content.Context
import androidx.room.Room
import es.rafapuig.movieapp.data.local.MoviesDatabase

object MovieDatabaseProvider {

    private const val DB_FILENAME = "movies.db"

    fun provideDatabase(context: Context) =
        Room
            .databaseBuilder(
                context,
                MoviesDatabase::class.java,
                DB_FILENAME)
            .fallbackToDestructiveMigration()
            .build()

}