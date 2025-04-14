package es.rafapuig.movieapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.rafapuig.movieapp.data.local.entity.TvShowEntity

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tvShow: TvShowEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tvShows: List<TvShowEntity>)

    @Delete
    suspend fun delete(tvShow: TvShowEntity)

    @Query("SELECT * FROM tv_shows")
    fun getAll() : List<TvShowEntity>

    @Query("SELECT * FROM tv_shows")
    fun getAllAsLiveData() : LiveData<List<TvShowEntity>>

}