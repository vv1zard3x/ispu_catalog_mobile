package com.example.vv1zard3x.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null
) {
    data object Home : Screen(
        route = "home",
        title = "Главная",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )
    
    data object Search : Screen(
        route = "search",
        title = "Поиск",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search
    )
    
    data object Favorites : Screen(
        route = "favorites",
        title = "Избранное",
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder
    )
    
    data object MovieDetail : Screen(
        route = "movie/{movieId}",
        title = "Детали"
    ) {
        fun createRoute(movieId: Int) = "movie/$movieId"
    }

    data object ActorDetail : Screen(
        route = "actor/{actorId}",
        title = "Актер"
    ) {
        fun createRoute(actorId: Int) = "actor/$actorId"
    }
}

val bottomNavItems = listOf(Screen.Home, Screen.Search, Screen.Favorites)
