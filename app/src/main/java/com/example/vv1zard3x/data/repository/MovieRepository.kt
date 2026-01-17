package com.example.vv1zard3x.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.vv1zard3x.data.local.GenreDao
import com.example.vv1zard3x.data.local.MovieDao
import com.example.vv1zard3x.data.model.Actor
import com.example.vv1zard3x.data.model.Genre
import com.example.vv1zard3x.data.model.Movie
import com.example.vv1zard3x.data.model.MovieDetails
import com.example.vv1zard3x.data.remote.RetrofitMovieApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieApi: RetrofitMovieApi,
    private val movieDao: MovieDao,
    private val genreDao: GenreDao,
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val MAX_CACHED_MOVIES = 100
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    /**
     * Получить фильмы - всегда обновляет с сервера если есть сеть
     */
    fun getMovies(): Flow<List<Movie>> = flow {
        val cachedMovies = movieDao.getAllMovies().first()
        val favoriteIds = cachedMovies.filter { it.isFavorite }.map { it.id }.toSet()
        
        if (isNetworkAvailable()) {
            // Показываем кэш пока грузится
            if (cachedMovies.isNotEmpty()) {
                emit(cachedMovies)
            }
            
            try {
                val freshMovies = movieApi.getPopularMovies()
                // Объединяем свежие данные с флагами избранного
                val moviesWithFavorites = freshMovies.take(MAX_CACHED_MOVIES).map { movie ->
                    movie.copy(isFavorite = movie.id in favoriteIds)
                }
                // Полностью заменяем кэш (кроме избранных которых нет в списке)
                val favoritesNotInList = cachedMovies.filter { 
                    it.isFavorite && it.id !in moviesWithFavorites.map { m -> m.id } 
                }
                movieDao.insertMovies(moviesWithFavorites + favoritesNotInList)
                emit(moviesWithFavorites)
            } catch (e: Exception) {
                if (cachedMovies.isEmpty()) emit(emptyList())
            }
        } else {
            emit(cachedMovies)
        }
    }

    /**
     * Принудительное обновление с сервера (для pull-to-refresh)
     */
    fun refreshMovies(): Flow<List<Movie>> = flow {
        if (!isNetworkAvailable()) {
            emit(movieDao.getAllMovies().first())
            return@flow
        }
        
        try {
            val cachedMovies = movieDao.getAllMovies().first()
            val favoriteIds = cachedMovies.filter { it.isFavorite }.map { it.id }.toSet()
            
            val freshMovies = movieApi.getPopularMovies()
            val moviesWithFavorites = freshMovies.take(MAX_CACHED_MOVIES).map { movie ->
                movie.copy(isFavorite = movie.id in favoriteIds)
            }
            
            // Сохраняем избранные, которых нет в новом списке
            val favoritesNotInList = cachedMovies.filter { 
                it.isFavorite && it.id !in moviesWithFavorites.map { m -> m.id } 
            }
            movieDao.insertMovies(moviesWithFavorites + favoritesNotInList)
            emit(moviesWithFavorites)
        } catch (e: Exception) {
            emit(movieDao.getAllMovies().first())
        }
    }

    /**
     * Получить фильмы с фильтрами
     */
    fun getMoviesFiltered(
        genreId: Int? = null,
        year: Int? = null,
        minRating: Float? = null
    ): Flow<List<Movie>> = flow {
        val cachedMovies = movieDao.getAllMovies().first()
        val favoriteIds = cachedMovies.filter { it.isFavorite }.map { it.id }.toSet()
        
        if (isNetworkAvailable()) {
            try {
                val results = movieApi.getMoviesFiltered(
                    genreId = genreId,
                    year = year,
                    minRating = minRating
                )
                val resultsWithFavorites = results.map { it.copy(isFavorite = it.id in favoriteIds) }
                emit(resultsWithFavorites)
            } catch (e: Exception) {
                emit(filterLocally(genreId, year, minRating))
            }
        } else {
            emit(filterLocally(genreId, year, minRating))
        }
    }

    private suspend fun filterLocally(
        genreId: Int?,
        year: Int?,
        minRating: Float?
    ): List<Movie> {
        var movies = movieDao.getAllMovies().first()
        
        if (genreId != null) {
            movies = movies.filter { genreId in it.genreIds }
        }
        if (year != null) {
            movies = movies.filter { it.releaseDate.startsWith(year.toString()) }
        }
        if (minRating != null) {
            movies = movies.filter { it.rating >= minRating }
        }
        
        return movies.sortedByDescending { it.rating }
    }

    fun searchMovies(query: String): Flow<List<Movie>> = flow {
        val cachedMovies = movieDao.getAllMovies().first()
        val favoriteIds = cachedMovies.filter { it.isFavorite }.map { it.id }.toSet()
        
        if (isNetworkAvailable()) {
            try {
                val results = movieApi.searchMovies(query)
                val resultsWithFavorites = results.map { it.copy(isFavorite = it.id in favoriteIds) }
                emit(resultsWithFavorites)
            } catch (e: CancellationException) {
                throw e // Пробрасываем отмену дальше
            } catch (e: Exception) {
                emit(movieDao.searchMovies(query).first())
            }
        } else {
            emit(movieDao.searchMovies(query).first())
        }
    }

    fun getMoviesByGenre(genreId: Int): Flow<List<Movie>> = movieDao.getMoviesByGenre(genreId)

    suspend fun getMovieDetails(movieId: Int): MovieDetails? {
        val favoriteIds = movieDao.getFavoriteMovies().first().map { it.id }.toSet()
        
        // Всегда пробуем получить свежие данные с сервера
        val movie = if (isNetworkAvailable()) {
            try {
                movieApi.getMovieDetails(movieId)?.also { 
                    movieDao.insertMovie(it.copy(isFavorite = movieId in favoriteIds)) 
                }
            } catch (e: Exception) { 
                movieDao.getMovieById(movieId) 
            }
        } else {
            movieDao.getMovieById(movieId)
        }
        
        val actors = if (isNetworkAvailable()) {
            try { movieApi.getMovieCast(movieId) } catch (e: Exception) { emptyList() }
        } else emptyList()
        
        return movie?.let { 
            MovieDetails(it.copy(isFavorite = movieId in favoriteIds), actors) 
        }
    }

    fun getFavoriteMovies(): Flow<List<Movie>> = movieDao.getFavoriteMovies()

    suspend fun toggleFavorite(movie: Movie) {
        val updated = movie.copy(isFavorite = !movie.isFavorite)
        if (movieDao.getMovieById(movie.id) != null) {
            movieDao.updateMovie(updated)
        } else {
            movieDao.insertMovie(updated)
        }
    }

    fun getGenres(): Flow<List<Genre>> = flow {
        val cached = genreDao.getAllGenres().first()
        if (cached.isNotEmpty()) emit(cached)
        
        if (isNetworkAvailable()) {
            try {
                val fresh = movieApi.getGenres()
                genreDao.insertGenres(fresh)
                emit(fresh)
            } catch (e: Exception) {
                if (cached.isEmpty()) emit(emptyList())
            }
        } else if (cached.isEmpty()) {
            emit(emptyList())
        }
    }
}
