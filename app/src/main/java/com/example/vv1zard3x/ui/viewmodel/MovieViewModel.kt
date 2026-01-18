package com.example.vv1zard3x.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vv1zard3x.data.model.Genre
import com.example.vv1zard3x.data.model.Movie
import com.example.vv1zard3x.data.model.MovieDetails
import com.example.vv1zard3x.data.repository.MovieRepository
import com.example.vv1zard3x.ui.components.FilterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MoviesUiState(
    val movies: List<Movie> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val filterState: FilterState = FilterState(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class MovieDetailUiState(
    val movieDetails: MovieDetails? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class SearchUiState(
    val query: String = "",
    val results: List<Movie> = emptyList(),
    val allMovies: List<Movie> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _moviesState = MutableStateFlow(MoviesUiState())
    val moviesState: StateFlow<MoviesUiState> = _moviesState.asStateFlow()

    private val _movieDetailState = MutableStateFlow(MovieDetailUiState())
    val movieDetailState: StateFlow<MovieDetailUiState> = _movieDetailState.asStateFlow()

    private val _searchState = MutableStateFlow(SearchUiState())
    val searchState: StateFlow<SearchUiState> = _searchState.asStateFlow()

    private val _favoritesState = MutableStateFlow<List<Movie>>(emptyList())
    val favoritesState: StateFlow<List<Movie>> = _favoritesState.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadMovies()
        loadGenres()
        observeFavorites()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _moviesState.value = _moviesState.value.copy(isLoading = true, error = null)
            try {
                repository.getMovies().collect { movies ->
                    val favorites = _favoritesState.value.map { it.id }.toSet()
                    val moviesWithFavorites = movies.map { movie ->
                        movie.copy(isFavorite = movie.id in favorites)
                    }
                    _moviesState.value = _moviesState.value.copy(
                        movies = moviesWithFavorites,
                        isLoading = false
                    )
                    _searchState.value = _searchState.value.copy(allMovies = moviesWithFavorites)
                }
            } catch (e: Exception) {
                _moviesState.value = _moviesState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    /**
     * Принудительное обновление с сервера
     */
    fun refreshMovies() {
        viewModelScope.launch {
            _moviesState.value = _moviesState.value.copy(isLoading = true, error = null)
            try {
                repository.refreshMovies().collect { movies ->
                    val favorites = _favoritesState.value.map { it.id }.toSet()
                    val moviesWithFavorites = movies.map { movie ->
                        movie.copy(isFavorite = movie.id in favorites)
                    }
                    _moviesState.value = _moviesState.value.copy(
                        movies = moviesWithFavorites,
                        isLoading = false
                    )
                    _searchState.value = _searchState.value.copy(allMovies = moviesWithFavorites)
                }
            } catch (e: Exception) {
                _moviesState.value = _moviesState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun loadGenres() {
        viewModelScope.launch {
            repository.getGenres().collect { genres ->
                _moviesState.value = _moviesState.value.copy(genres = genres)
            }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            repository.getFavoriteMovies().collect { favorites ->
                _favoritesState.value = favorites
                val favoriteIds = favorites.map { it.id }.toSet()
                val updatedMovies = _moviesState.value.movies.map { movie ->
                    movie.copy(isFavorite = movie.id in favoriteIds)
                }
                _moviesState.value = _moviesState.value.copy(movies = updatedMovies)
                
                val updatedResults = _searchState.value.results.map { movie ->
                    movie.copy(isFavorite = movie.id in favoriteIds)
                }
                val updatedAllMovies = _searchState.value.allMovies.map { movie ->
                    movie.copy(isFavorite = movie.id in favoriteIds)
                }
                _searchState.value = _searchState.value.copy(
                    results = updatedResults,
                    allMovies = updatedAllMovies
                )
                
                // Обновляем состояние избранного в деталях фильма
                _movieDetailState.value.movieDetails?.let { details ->
                    val updatedMovie = details.movie.copy(isFavorite = details.movie.id in favoriteIds)
                    _movieDetailState.value = _movieDetailState.value.copy(
                        movieDetails = details.copy(movie = updatedMovie)
                    )
                }
            }
        }
    }

    /**
     * Применить фильтры
     */
    fun applyFilters(filterState: FilterState) {
        _moviesState.value = _moviesState.value.copy(filterState = filterState, isLoading = true)
        viewModelScope.launch {
            try {
                repository.getMoviesFiltered(
                    genreIds = filterState.genreIds,
                    years = filterState.years,
                    minRating = filterState.minRating,
                    sortBy = filterState.sortBy
                ).collect { movies ->
                    val favorites = _favoritesState.value.map { it.id }.toSet()
                    val moviesWithFavorites = movies.map { it.copy(isFavorite = it.id in favorites) }
                    _moviesState.value = _moviesState.value.copy(
                        movies = moviesWithFavorites,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _moviesState.value = _moviesState.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    /**
     * Сбросить фильтры
     */
    fun clearFilters() {
        _moviesState.value = _moviesState.value.copy(filterState = FilterState())
        loadMovies()
    }

    fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _movieDetailState.value = MovieDetailUiState(isLoading = true)
            try {
                val details = repository.getMovieDetails(movieId)
                val favoriteIds = _favoritesState.value.map { it.id }.toSet()
                val movieWithFavorite = details?.let {
                    it.copy(movie = it.movie.copy(isFavorite = it.movie.id in favoriteIds))
                }
                _movieDetailState.value = MovieDetailUiState(movieDetails = movieWithFavorite)
            } catch (e: Exception) {
                _movieDetailState.value = MovieDetailUiState(error = e.message)
            }
        }
    }

    fun search(query: String) {
        _searchState.value = _searchState.value.copy(query = query)
        
        searchJob?.cancel()
        
        if (query.isBlank()) {
            val allMovies = _searchState.value.allMovies.sortedByDescending { it.rating }
            _searchState.value = _searchState.value.copy(results = allMovies, isLoading = false)
            return
        }
        
        searchJob = viewModelScope.launch {
            _searchState.value = _searchState.value.copy(isLoading = true)
            delay(300)
            try {
                val results = repository.searchMovies(query).first()
                val favorites = _favoritesState.value.map { it.id }.toSet()
                val resultsWithFavorites = results.map { it.copy(isFavorite = it.id in favorites) }
                _searchState.value = _searchState.value.copy(
                    results = resultsWithFavorites,
                    isLoading = false
                )
            } catch (e: CancellationException) {
                throw e // Пробрасываем отмену, не обрабатываем как ошибку
            } catch (e: Exception) {
                _searchState.value = _searchState.value.copy(
                    results = emptyList(),
                    isLoading = false
                )
            }
        }
    }

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            repository.toggleFavorite(movie)
        }
    }

    fun clearMovieDetails() {
        _movieDetailState.value = MovieDetailUiState()
    }
}
