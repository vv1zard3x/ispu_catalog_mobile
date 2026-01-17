package com.example.vv1zard3x.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.vv1zard3x.data.model.Genre
import com.example.vv1zard3x.data.model.Movie

@Database(
    entities = [Movie::class, Genre::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
}
