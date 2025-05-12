package com.jesus.planetadelasfiestas

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.example.compose.PlanetaDeLasFiestasTheme
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.model.Datasource
import com.jesus.planetadelasfiestas.model.Routes
import com.jesus.planetadelasfiestas.ui.components.BottomNavigationBar
import com.jesus.planetadelasfiestas.ui.screens.AlbumListCompactScreen
import com.jesus.planetadelasfiestas.ui.screens.AlbumListMedExpScreen
import com.jesus.planetadelasfiestas.ui.screens.DetailItemScreen
import com.jesus.planetadelasfiestas.ui.screens.FavListCompactScreen
import com.jesus.planetadelasfiestas.ui.screens.FavListMedExpScreen
import com.jesus.planetadelasfiestas.ui.screens.ProfileCompactScreen
import com.jesus.planetadelasfiestas.utils.getWindowSizeClass
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.flow.map

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanetaDeLasFiestasTheme(
                dynamicColor = false
            ) {
                // Cargar los álbumes desde el DataSource
                val albums = Datasource.albumList()

                // Estado para los álbumes favoritos
                var favoriteAlbums by remember { mutableStateOf(setOf<String>()) }

                // Lógica para manejar el clic en el álbum favorito
                val handleFavoriteClick: (Album) -> Unit = { album ->
                    favoriteAlbums = if (favoriteAlbums.contains(album.albumName)) {
                        favoriteAlbums - album.albumName // Si ya está en favoritos, lo eliminamos
                    } else {
                        favoriteAlbums + album.albumName // Si no está en favoritos, lo agregamos
                    }
                }

                val windowSize = LocalConfiguration.current.screenWidthDp.let {
                    when {
                        it <= 600 -> WindowWidthSizeClass.Compact
                        it <= 1024 -> WindowWidthSizeClass.Medium
                        else -> WindowWidthSizeClass.Expanded
                    }
                }

                PlanetaDeLasFiestasApp(
                    albums = albums,
                    handleFavoriteClick = handleFavoriteClick,
                    favoriteAlbums = favoriteAlbums,
                    windowSize = windowSize
                )
            }
        }
    }
}

@Composable
fun PlanetaDeLasFiestasApp(
    albums: List<Album>,
    handleFavoriteClick: (Album) -> Unit,
    favoriteAlbums: Set<String>,
    windowSize: WindowWidthSizeClass

) {
    val navController = rememberNavController()
    val currentRoute by navController.currentBackStackEntryFlow
        .map { it.destination.route }
        .collectAsState(initial = null)

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, currentRoute) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.AlbumList,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.AlbumList) {
                if (windowSize == WindowWidthSizeClass.Compact) {
                    AlbumListCompactScreen(
                        albums = albums,
                        onFavoriteClick = handleFavoriteClick,
                        favoriteAlbums = favoriteAlbums,
                        onDetailsClick = { album ->
                            if (album.albumName.isNotEmpty()) {
                                navController.navigate(Routes.albumDetailRoute(album.albumName))
                            }
                        }
                    )
                } else {
                    AlbumListMedExpScreen(
                        albums = albums,
                        onFavoriteClick = handleFavoriteClick,
                        favoriteAlbums = favoriteAlbums,
                        onDetailsClick = { album ->
                            if (album.albumName.isNotEmpty()) {
                                navController.navigate(Routes.albumDetailRoute(album.albumName))
                            }
                        }
                    )
                }
            }

            composable(Routes.FavList) {
                val favAlbums = albums.filter { favoriteAlbums.contains(it.albumName) }
                if (windowSize == WindowWidthSizeClass.Compact) {
                    FavListCompactScreen(
                        albums = favAlbums,
                        onFavoriteClick = handleFavoriteClick,
                        favoriteAlbums = favoriteAlbums,
                        onDetailsClick = { album ->
                            if (album.albumName.isNotEmpty()) {
                                navController.navigate(Routes.albumDetailRoute(album.albumName))
                            }
                        }
                    )
                } else {
                    FavListMedExpScreen(
                        albums = favAlbums,
                        onFavoriteClick = handleFavoriteClick,
                        favoriteAlbums = favoriteAlbums,
                        onDetailsClick = { album ->
                            if (album.albumName.isNotEmpty()) {
                                navController.navigate(Routes.albumDetailRoute(album.albumName))
                            }
                        }
                    )
                }
            }

            composable(Routes.Profile) {
                ProfileCompactScreen()
            }

            composable("album_detail/{albumName}") { backStackEntry ->
                val encodedName = backStackEntry.arguments?.getString("albumName")
                val albumName = encodedName?.let { Uri.decode(it) }

                if (albumName.isNullOrEmpty()) {
                    Text("Álbum no válido")
                } else {
                    val album = albums.find { it.albumName == albumName }
                    if (album != null) {
                        DetailItemScreen(
                            album = album,
                            isFavorite = favoriteAlbums.contains(album.albumName),
                            onFavoriteClick = handleFavoriteClick,
                            onBackClick = { navController.popBackStack() }
                        )
                    } else {
                        Text("Álbum no encontrado")
                    }
                }
            }
        }
    }
}