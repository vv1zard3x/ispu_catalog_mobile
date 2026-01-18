package com.example.vv1zard3x.data.remote

import com.example.vv1zard3x.data.model.Actor
import com.example.vv1zard3x.data.model.ActorDetail
import com.example.vv1zard3x.data.model.Genre
import com.example.vv1zard3x.data.model.Movie
import com.example.vv1zard3x.data.remote.dto.toActorDetail
import com.example.vv1zard3x.data.remote.dto.toActors
import com.example.vv1zard3x.data.remote.dto.toGenres
import com.example.vv1zard3x.data.remote.dto.toMovie
import com.example.vv1zard3x.data.remote.dto.toMovies
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Реализация MovieApi через Retrofit
 */
@Singleton
class RetrofitMovieApi @Inject constructor(
    private val apiService: MovieApiService
) : MovieApi {

    override suspend fun getPopularMovies(): List<Movie> {
        return apiService.getMovies().results.toMovies()
    }

    override suspend fun getMovieDetails(movieId: Int): Movie? {
        return try {
            apiService.getMovieDetails(movieId).toMovie()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getMovieCast(movieId: Int): List<Actor> {
        return try {
            apiService.getMovieCast(movieId).toActors()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun searchMovies(query: String): List<Movie> {
        return try {
            apiService.getMovies(search = query).results.toMovies()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getGenres(): List<Genre> {
        return try {
            apiService.getGenres().toGenres()
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Получить фильмы с фильтрами
     */
    suspend fun getMoviesFiltered(
        genreId: Int? = null,
        actorId: Int? = null,
        year: Int? = null,
        minRating: Float? = null,
        search: String? = null,
        ordering: String = "-rating"
    ): List<Movie> {
        return try {
            apiService.getMovies(
                genreId = genreId,
                actorId = actorId,
                year = year,
                minRating = minRating,
                search = search,
                ordering = ordering
            ).results.toMovies()
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Получить список актёров
     */
    suspend fun getActors(search: String? = null): List<Actor> {
        return try {
            apiService.getActors(search = search).results.toActors()
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Получить фильмы актёра
     */
    override suspend fun getActorMovies(actorId: Int): List<Movie> {
        return try {
            apiService.getActorMovies(actorId).results.toMovies()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getActorDetails(actorId: Int): ActorDetail? {
        return try {
            apiService.getActorDetails(actorId).toActorDetail()
        } catch (e: Exception) {
            null
        }
    }
}
