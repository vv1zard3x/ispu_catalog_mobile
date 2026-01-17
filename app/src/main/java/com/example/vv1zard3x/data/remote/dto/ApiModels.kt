package com.example.vv1zard3x.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO для ответа со списком фильмов (Django пагинация)
 */
data class MoviesResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<MovieDto>
)

/**
 * DTO для ответа со списком актёров (Django пагинация)
 */
data class ActorsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<ActorDto>
)

/**
 * DTO фильма от бэкенда
 */
data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val rating: Float,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("genre_ids")
    val genreIds: List<Int>
)

/**
 * DTO актёра от бэкенда (в списке актёров фильма)
 */
data class ActorDto(
    val id: Int,
    val name: String,
    val character: String? = null,
    @SerializedName("profile_path")
    val profilePath: String?
)

/**
 * DTO детальной информации об актёре
 */
data class ActorDetailDto(
    val id: Int,
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String?,
    val biography: String? = null,
    val birthday: String? = null,
    @SerializedName("place_of_birth")
    val placeOfBirth: String? = null
)

/**
 * DTO жанра от бэкенда
 */
data class GenreDto(
    val id: Int,
    val name: String
)
