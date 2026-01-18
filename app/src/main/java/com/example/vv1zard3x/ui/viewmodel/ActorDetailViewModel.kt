package com.example.vv1zard3x.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vv1zard3x.data.model.ActorDetail
import com.example.vv1zard3x.data.model.Movie
import com.example.vv1zard3x.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ActorDetailUiState(
    val actor: ActorDetail? = null,
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ActorDetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ActorDetailUiState())
    val state: StateFlow<ActorDetailUiState> = _state.asStateFlow()

    fun loadActorDetails(actorId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val actor = repository.getActorDetails(actorId)
                val movies = repository.getActorMovies(actorId)
                
                if (actor != null) {
                    _state.value = _state.value.copy(
                        actor = actor,
                        movies = movies,
                        isLoading = false
                    )
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Failed to load actor"
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    
    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            repository.toggleFavorite(movie)
            // Update the movie list with new favorite status locally
            val updatedMovies = _state.value.movies.map { 
                if (it.id == movie.id) it.copy(isFavorite = !it.isFavorite) else it 
            }
            _state.value = _state.value.copy(movies = updatedMovies)
        }
    }
}
