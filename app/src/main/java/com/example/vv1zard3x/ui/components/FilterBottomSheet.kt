package com.example.vv1zard3x.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vv1zard3x.data.model.Genre

enum class SortOption(val displayName: String) {
    RATING_DESC("По рейтингу ↓"),
    RATING_ASC("По рейтингу ↑"),
    YEAR_DESC("По году ↓"),
    YEAR_ASC("По году ↑"),
    TITLE_ASC("По названию А-Я"),
    TITLE_DESC("По названию Я-А")
}

data class FilterState(
    val genreIds: Set<Int> = emptySet(),
    val years: Set<Int> = emptySet(),
    val minRating: Float? = null,
    val sortBy: SortOption = SortOption.RATING_DESC
) {
    val hasActiveFilters: Boolean
        get() = genreIds.isNotEmpty() || years.isNotEmpty() || minRating != null
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterBottomSheet(
    isVisible: Boolean,
    genres: List<Genre>,
    currentFilter: FilterState,
    onDismiss: () -> Unit,
    onApply: (FilterState) -> Unit
) {
    if (!isVisible) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    
    var selectedGenreIds by remember(currentFilter) { mutableStateOf(currentFilter.genreIds) }
    var selectedYears by remember(currentFilter) { mutableStateOf(currentFilter.years) }
    var selectedRating by remember(currentFilter) { mutableFloatStateOf(currentFilter.minRating ?: 0f) }
    var selectedSort by remember(currentFilter) { mutableStateOf(currentFilter.sortBy) }

    val availableYears = listOf(2024, 2023, 2022, 2021, 2020, 2019, 2018, 2015, 2010, 2005, 2000)

    fun applyCurrentFilters() {
        onApply(
            FilterState(
                genreIds = selectedGenreIds,
                years = selectedYears,
                minRating = if (selectedRating == 0f) null else selectedRating,
                sortBy = selectedSort
            )
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Заголовок
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Фильтры и сортировка",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Закрыть")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Сортировка
            Text(
                text = "Сортировка",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SortOption.entries.forEach { option ->
                    FilterChip(
                        selected = selectedSort == option,
                        onClick = { 
                            selectedSort = option
                            applyCurrentFilters()
                        },
                        label = { Text(option.displayName) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Жанры
            Text(
                text = "Жанры (можно выбрать несколько)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedGenreIds.isEmpty(),
                    onClick = { 
                        selectedGenreIds = emptySet()
                        applyCurrentFilters()
                    },
                    label = { Text("Все") }
                )
                genres.forEach { genre ->
                    FilterChip(
                        selected = genre.id in selectedGenreIds,
                        onClick = { 
                            selectedGenreIds = if (genre.id in selectedGenreIds) {
                                selectedGenreIds - genre.id
                            } else {
                                selectedGenreIds + genre.id
                            }
                            applyCurrentFilters()
                        },
                        label = { Text(genre.name) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Год
            Text(
                text = "Год выхода (можно выбрать несколько)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedYears.isEmpty(),
                    onClick = { 
                        selectedYears = emptySet()
                        applyCurrentFilters()
                    },
                    label = { Text("Все") }
                )
                availableYears.forEach { year ->
                    FilterChip(
                        selected = year in selectedYears,
                        onClick = { 
                            selectedYears = if (year in selectedYears) {
                                selectedYears - year
                            } else {
                                selectedYears + year
                            }
                            applyCurrentFilters()
                        },
                        label = { Text(year.toString()) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Рейтинг
            Text(
                text = "Минимальный рейтинг: ${if (selectedRating > 0) String.format("%.1f", selectedRating) else "Любой"}",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            Slider(
                value = selectedRating,
                onValueChange = { selectedRating = it },
                onValueChangeFinished = { applyCurrentFilters() },
                valueRange = 0f..9f,
                steps = 17,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
