package com.example.vv1zard3x.data.remote.dto

import com.example.vv1zard3x.data.model.Actor
import com.example.vv1zard3x.data.model.ActorDetail
import com.example.vv1zard3x.data.model.Genre
import com.example.vv1zard3x.data.model.Movie

/**
 * Базовый URL для медиафайлов
 */
private const val BASE_MEDIA_URL = "https://films.vv1zard3x.ru"

/**
 * Преобразует относительный путь в полный URL
 */
private fun String?.toFullUrl(): String? {
    if (this.isNullOrBlank()) return null
    return if (this.startsWith("http")) this else "$BASE_MEDIA_URL$this"
}

/**
 * Маппинг DTO в доменные модели
 */
fun MovieDto.toMovie(): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath.toFullUrl(),
    backdropPath = backdropPath.toFullUrl(),
    rating = rating,
    releaseDate = releaseDate,
    genreIds = genreIds ?: genres?.map { it.id } ?: emptyList(),
    voteCount = voteCount,
    isFavorite = false
)

fun ActorDto.toActor(): Actor = Actor(
    id = id,
    name = name,
    character = character ?: "",
    profilePath = profilePath.toFullUrl()
)

fun GenreDto.toGenre(): Genre = Genre(
    id = id,
    name = name
)

fun List<MovieDto>.toMovies(): List<Movie> = map { it.toMovie() }
fun List<ActorDto>.toActors(): List<Actor> = map { it.toActor() }
fun List<GenreDto>.toGenres(): List<Genre> = map { it.toGenre() }

fun ActorDetailDto.toActorDetail(): ActorDetail = ActorDetail(
    id = id,
    name = name,
    biography = biography ?: "",
    birthday = birthday,
    placeOfBirth = placeOfBirth,
    profilePath = profilePath.toFullUrl()
)
