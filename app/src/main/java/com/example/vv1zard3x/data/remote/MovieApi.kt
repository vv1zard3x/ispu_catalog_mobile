package com.example.vv1zard3x.data.remote

import com.example.vv1zard3x.data.model.Actor
import com.example.vv1zard3x.data.model.Genre
import com.example.vv1zard3x.data.model.Movie

/**
 * API интерфейс для получения данных о фильмах.
 * Для демонстрации используются мок-данные.
 * Замените на реальные Retrofit endpoints при наличии бэкенда.
 */
interface MovieApi {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getMovieDetails(movieId: Int): Movie?
    suspend fun getMovieCast(movieId: Int): List<Actor>
    suspend fun searchMovies(query: String): List<Movie>
    suspend fun getGenres(): List<Genre>
}
