package com.example.vv1zard3x.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val rating: Float,
    val releaseDate: String,
    val genreIds: List<Int>,
    val voteCount: Int,
    val isFavorite: Boolean = false
)
