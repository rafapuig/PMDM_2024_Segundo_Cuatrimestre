package es.rafapuig.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import es.rafapuig.movieapp.movies.data.local.dao.GenreDao
import es.rafapuig.movieapp.movies.data.local.dao.MovieDao
import es.rafapuig.movieapp.data.local.dao.RemoteKeyDao
import es.rafapuig.movieapp.data.local.dao.TvShowDao
import es.rafapuig.movieapp.movies.data.local.entity.GenreEntity
import es.rafapuig.movieapp.movies.data.local.entity.MovieEntity
import es.rafapuig.movieapp.movies.data.local.entity.MovieGenreCrossRef
import es.rafapuig.movieapp.data.local.entity.RemoteKey
import es.rafapuig.movieapp.data.local.entity.TvShowEntity

@Database(
    entities = [
        TvShowEntity::class,
        MovieEntity::class,
        RemoteKey::class,
        GenreEntity::class,
        MovieGenreCrossRef::class
    ],
    version = 1
)
abstract class MoviesDatabase : RoomDatabase() {
    //abstract fun movieDao(): MovieDao

    abstract val tvShowDao : TvShowDao
    abstract val movieDao: MovieDao
    abstract val remoteKeyDao: RemoteKeyDao
    abstract val genreDao: GenreDao
}