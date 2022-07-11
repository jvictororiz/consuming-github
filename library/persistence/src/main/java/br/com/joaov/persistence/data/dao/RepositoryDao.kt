package br.com.joaov.persistence.data.dao

import androidx.room.*
import br.com.joaov.persistence.data.entity.RepositoryEntity

@Dao
interface RepositoryDao {
    
    @Query("SELECT * FROM repository WHERE page = :page")
    suspend fun getRepositoriesByPage(page: Int): List<RepositoryEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRepositories(repositories: List<RepositoryEntity>)
    
    @Query("DELETE FROM repository ")
    suspend fun deleteAll()
    
}