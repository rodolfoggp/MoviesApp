package com.rodolfogusson.testepag.infrastructure.data.persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rodolfogusson.testepag.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase: RoomDatabase() {
    
}