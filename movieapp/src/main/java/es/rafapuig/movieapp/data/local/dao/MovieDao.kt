package es.rafapuig.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.rafapuig.movieapp.data.local.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    suspend fun getNowPlayingMovies() : List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movies:List<MovieEntity>)

    @Query("DELETE FROM movies")
    suspend fun clear()

}