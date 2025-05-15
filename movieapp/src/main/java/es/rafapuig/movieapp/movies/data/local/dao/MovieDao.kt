package es.rafapuig.movieapp.movies.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import es.rafapuig.movieapp.movies.data.local.entity.GenreEntity
import es.rafapuig.movieapp.movies.data.local.entity.MovieEntity
import es.rafapuig.movieapp.movies.data.local.entity.MovieGenreCrossRef
import es.rafapuig.movieapp.movies.data.local.entity.MovieWithGenreDetails

@Dao
abstract class MovieDao {

    @Query("SELECT * FROM movies")
    abstract suspend fun getNowPlayingMovies() : List<MovieEntity>

    @Transaction
    @Query("SELECT * FROM movies ORDER BY modified_at")
    abstract fun getNowPlayingMoviesPaged() : PagingSource<Int, MovieWithGenreDetails>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertAll(movies:List<MovieEntity>)

    @Upsert
    abstract suspend fun upsertMovieWithGenreIds(movie: MovieEntity, genres: List<MovieGenreCrossRef>)


    suspend fun upsertMovieWithGenreIdsWithTimestamp(movie: MovieEntity, genres: List<MovieGenreCrossRef>) {
        upsertMovieWithGenreIds(movie.apply {
            createdAt = System.currentTimeMillis()
            modifiedAt = System.currentTimeMillis()
        }, genres)
    }

    @Upsert
    abstract suspend fun upsertMovieWithGenres(movie: MovieEntity, genres: List<GenreEntity>)

    @Query("DELETE FROM movies")
    abstract suspend fun clearAll()

    @Upsert
    abstract suspend fun upsertAll(movies: List<MovieEntity>)

    @Transaction
    @Query("SELECT * FROM movies WHERE id = :movieId")
   abstract suspend fun getMovieWithGenreDetails(movieId:Int) : List<MovieWithGenreDetails>

}