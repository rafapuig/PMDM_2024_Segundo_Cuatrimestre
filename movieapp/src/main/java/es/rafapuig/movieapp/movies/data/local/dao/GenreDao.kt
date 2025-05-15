package es.rafapuig.movieapp.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import es.rafapuig.movieapp.movies.data.local.entity.GenreEntity

@Dao
interface GenreDao {

    @Upsert
    suspend fun upsertAll(genres: List<GenreEntity>)

    @Query("SELECT * FROM genres")
    suspend fun getAll() : List<GenreEntity>

}