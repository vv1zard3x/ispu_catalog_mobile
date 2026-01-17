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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vv1zard3x.data.model.Genre

data class FilterState(
    val genreId: Int? = null,
    val year: Int? = null,
    val minRating: Float? = null
)

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

    val sheetState = rememberModalBottomSheetState()
    
    var selectedGenreId by remember(currentFilter) { mutableIntStateOf(currentFilter.genreId ?: -1) }
    var selectedYear by remember(currentFilter) { mutableIntStateOf(currentFilter.year ?: 0) }
    var selectedRating by remember(currentFilter) { mutableFloatStateOf(currentFilter.minRating ?: 0f) }

    val years = listOf(0, 2024, 2023, 2022, 2021, 2020, 2019, 2018, 2015, 2010, 2005, 2000)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
        ) {
            // Заголовок
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Фильтры",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Закрыть")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Жанры
            Text(
                text = "Жанр",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedGenreId == -1,
                    onClick = { selectedGenreId = -1 },
                    label = { Text("Все") }
                )
                genres.forEach { genre ->
                    FilterChip(
                        selected = selectedGenreId == genre.id,
                        onClick = { selectedGenreId = genre.id },
                        label = { Text(genre.name) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Год
            Text(
                text = "Год выхода",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                years.forEach { year ->
                    FilterChip(
                        selected = selectedYear == year,
                        onClick = { selectedYear = year },
                        label = { Text(if (year == 0) "Все" else year.toString()) }
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
                valueRange = 0f..9f,
                steps = 17,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Кнопки
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        selectedGenreId = -1
                        selectedYear = 0
                        selectedRating = 0f
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Сбросить")
                }
                
                Button(
                    onClick = {
                        onApply(
                            FilterState(
                                genreId = if (selectedGenreId == -1) null else selectedGenreId,
                                year = if (selectedYear == 0) null else selectedYear,
                                minRating = if (selectedRating == 0f) null else selectedRating
                            )
                        )
                        onDismiss()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Применить")
                }
            }
        }
    }
}
