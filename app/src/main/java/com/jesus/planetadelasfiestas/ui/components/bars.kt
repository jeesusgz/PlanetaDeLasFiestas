package com.jesus.planetadelasfiestas.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.AccountCircle

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String?) {
    NavigationBar {
        // Definir los items para la barra de navegación
        val items = listOf(
            BottomNavItem("album_list", Icons.Filled.Home, "Álbumes"),
            BottomNavItem("fav_list", Icons.Filled.Favorite, "Favoritos"),
            BottomNavItem("profile", Icons.Filled.AccountCircle, "Perfil")
        )
        // Iterar sobre los items y crear cada NavigationBarItem
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            // Manejo del comportamiento de la navegación
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

// Data class para representar los items de la barra de navegación
data class BottomNavItem(val route: String, val icon: ImageVector, val label: String)