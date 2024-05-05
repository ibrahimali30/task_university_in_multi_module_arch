package com.example.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.local.model.ListStringConverter
import com.example.local.model.UniversityLocalModel

@Database(entities = [UniversityLocalModel::class], version = 5, exportSchema = false) // We need migration if increase version
@TypeConverters(ListStringConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun universityDao() : UniversityDAO
}