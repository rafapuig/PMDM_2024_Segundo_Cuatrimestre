package es.rafapuig.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import es.rafapuig.movieapp.data.local.dao.MovieDao
import es.rafapuig.movieapp.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    //abstract fun movieDao(): MovieDao

    abstract val movieDao : MovieDao
}