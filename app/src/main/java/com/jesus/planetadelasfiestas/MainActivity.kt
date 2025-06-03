package com.jesus.planetadelasfiestas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.PlanetaDeLasFiestasTheme
import com.jesus.planetadelasfiestas.ViewModel.MainViewModel
import com.jesus.planetadelasfiestas.ViewModel.MainViewModelFactory
import com.jesus.planetadelasfiestas.data.AppTheme
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
import com.jesus.planetadelasfiestas.ui.theme.about.AboutScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = applicationContext
            val mainViewModel: MainViewModel = viewModel(
                factory = MainViewModelFactory(context as android.app.Application)
            )

            val appTheme by mainViewModel.appTheme.collectAsState()

            val isDarkTheme = when (appTheme) {
                AppTheme.LIGHT -> false
                AppTheme.DARK -> true
                AppTheme.SYSTEM -> isSystemInDarkTheme()
            }

            PlanetaDeLasFiestasTheme(darkTheme = isDarkTheme, dynamicColor = false) {

                var albumsState by remember { mutableStateOf(Datasource.albumList()) }
                var favoriteAlbums by remember { mutableStateOf(setOf<String>()) }

                val handleFavoriteClick: (Album) -> Unit = { album ->
                    favoriteAlbums = if (favoriteAlbums.contains(album.albumName)) {
                        favoriteAlbums - album.albumName
                    } else {
                        favoriteAlbums + album.albumName
                    }
                }

                val addComment: (String, String) -> Unit = { albumName, comment ->
                    albumsState = albumsState.map { album ->
                        if (album.albumName == albumName) {
                            val newComments = album.comments.toMutableList()
                            newComments.add(comment)
                            album.copy(comments = newComments)
                        } else album
                    }.toMutableList()
                }

                val windowSize = LocalConfiguration.current.screenWidthDp.let {
                    when {
                        it <= 600 -> WindowWidthSizeClass.Compact
                        it <= 1024 -> WindowWidthSizeClass.Medium
                        else -> WindowWidthSizeClass.Expanded
                    }
                }

                PlanetaDeLasFiestasApp(
                    albums = albumsState,
                    handleFavoriteClick = handleFavoriteClick,
                    favoriteAlbums = favoriteAlbums,
                    addComment = addComment,
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
    addComment: (String, String) -> Unit,
    windowSize: WindowWidthSizeClass
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            )
        }
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
                            navController.navigate(Routes.albumDetailRoute(album.albumName))
                        },
                        navController = navController,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    AlbumListMedExpScreen(
                        albums = albums,
                        onFavoriteClick = handleFavoriteClick,
                        favoriteAlbums = favoriteAlbums,
                        onDetailsClick = { album ->
                            navController.navigate(Routes.albumDetailRoute(album.albumName))
                        },
                        navController = navController,
                        modifier = Modifier.fillMaxSize()
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
                            navController.navigate(Routes.albumDetailRoute(album.albumName))
                        },
                        navController = navController,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    FavListMedExpScreen(
                        albums = favAlbums,
                        onFavoriteClick = handleFavoriteClick,
                        favoriteAlbums = favoriteAlbums,
                        onDetailsClick = { album ->
                            navController.navigate(Routes.albumDetailRoute(album.albumName))
                        },
                        navController = navController,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            composable(Routes.Profile) {
                ProfileCompactScreen()
            }

            composable(Routes.About) {
                AboutScreen()
            }

            composable(
                route = "detail/{albumName}",
                arguments = listOf(navArgument("albumName") { type = NavType.StringType })
            ) { backStackEntry ->
                val albumName = backStackEntry.arguments?.getString("albumName")
                val album = albums.find { it.albumName == albumName }

                if (album != null) {
                    val showComments = favoriteAlbums.contains(album.albumName)

                    DetailItemScreen(
                        album = album,
                        isFavorite = showComments,
                        onFavoriteClick = { handleFavoriteClick(album) },
                        onBackClick = { navController.popBackStack() },
                        comments = album.comments,
                        onAddComment = { comment -> addComment(album.albumName, comment) },
                        showComments = showComments
                    )
                }
            }
        }
    }
}