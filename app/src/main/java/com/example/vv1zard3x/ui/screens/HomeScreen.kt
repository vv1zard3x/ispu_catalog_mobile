package com.example.vv1zard3x.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vv1zard3x.ui.components.FilterBottomSheet
import com.example.vv1zard3x.ui.components.MovieCard
import com.example.vv1zard3x.ui.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MovieViewModel,
    onMovieClick: (Int) -> Unit
) {
    val state by viewModel.moviesState.collectAsState()
    var showFilterSheet by remember { mutableStateOf(false) }

    // Подсчёт активных фильтров
    val activeFiltersCount = listOfNotNull(
        state.filterState.genreId,
        state.filterState.year,
        state.filterState.minRating
    ).size

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Кинокаталог",
                    fontWeight = FontWeight.Bold
                )
            },
            actions = {
                IconButton(onClick = { showFilterSheet = true }) {
                    BadgedBox(
                        badge = {
                            if (activeFiltersCount > 0) {
                                Badge { Text(activeFiltersCount.toString()) }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Фильтры",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        )

        // Показываем активные фильтры
        if (activeFiltersCount > 0) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.filterState.genreId?.let { genreId ->
                    val genreName = state.genres.find { it.id == genreId }?.name ?: ""
                    FilterTag(text = genreName)
                }
                state.filterState.year?.let { year ->
                    FilterTag(text = year.toString())
                }
                state.filterState.minRating?.let { rating ->
                    FilterTag(text = "★ ${String.format("%.1f", rating)}+")
                }
            }
        }

        PullToRefreshBox(
            isRefreshing = state.isLoading,
            onRefresh = { 
                if (activeFiltersCount > 0) {
                    viewModel.applyFilters(state.filterState)
                } else {
                    viewModel.refreshMovies()
                }
            },
            modifier = Modifier.fillMaxSize()
        ) {
            when {
                state.isLoading && state.movies.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                
                state.error != null && state.movies.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Ошибка загрузки: ${state.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
                
                state.movies.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Фильмы не найдены",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = state.movies,
                            key = { it.id }
                        ) { movie ->
                            MovieCard(
                                movie = movie,
                                onMovieClick = onMovieClick,
                                onFavoriteClick = { viewModel.toggleFavorite(it) }
                            )
                        }
                    }
                }
            }
        }
    }

    // Filter Bottom Sheet
    FilterBottomSheet(
        isVisible = showFilterSheet,
        genres = state.genres,
        currentFilter = state.filterState,
        onDismiss = { showFilterSheet = false },
        onApply = { newFilter ->
            viewModel.applyFilters(newFilter)
        }
    )
}

@Composable
private fun FilterTag(text: String) {
    androidx.compose.material3.SuggestionChip(
        onClick = { },
        label = { Text(text, style = MaterialTheme.typography.labelSmall) }
    )
}
