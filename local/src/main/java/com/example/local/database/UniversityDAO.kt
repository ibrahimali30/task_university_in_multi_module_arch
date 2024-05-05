package com.example.local.database

import androidx.room.*
import com.example.local.model.UniversityLocalModel

@Dao
interface UniversityDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun addUniversityItem(university : UniversityLocalModel) : Long

    @Query("SELECT * FROM university WHERE name = :name")
     fun getUniversityItem(name: String): UniversityLocalModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun addUniversityItems(university: List<UniversityLocalModel>) : List<Long>

    @Query("SELECT * FROM university")
     fun getUniversityItems(): List<UniversityLocalModel>

    @Update
     fun updateUniversityItem(university: UniversityLocalModel): Int

    @Query("DELETE FROM university")
     fun clearCachedUniversityItems(): Int
}