package es.rafapuig.movieapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import es.rafapuig.movieapp.data.local.entity.GenreEntity
import es.rafapuig.movieapp.data.local.entity.MovieEntity
import es.rafapuig.movieapp.data.local.entity.MovieGenreCrossRef
import es.rafapuig.movieapp.data.local.entity.MovieWithGenreDetails

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    suspend fun getNowPlayingMovies() : List<MovieEntity>

    @Transaction
    @Query("SELECT * FROM movies")
    fun getNowPlayingMoviesPaged() : PagingSource<Int, MovieWithGenreDetails>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movies:List<MovieEntity>)

    @Upsert
    suspend fun upsertMovieWithGenreIds(movie:MovieEntity, genres: List<MovieGenreCrossRef>)

    @Upsert
    suspend fun upsertMovieWithGenres(movie:MovieEntity, genres: List<GenreEntity>)

    @Query("DELETE FROM movies")
    suspend fun clearAll()

    @Upsert
    suspend fun upsertAll(movies: List<MovieEntity>)

}