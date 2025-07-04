package com.jesus.planetadelasfiestas

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.PlanetaDeLasFiestasTheme
import com.jesus.planetadelasfiestas.ViewModel.AlbumDetailViewModel
import com.jesus.planetadelasfiestas.ViewModel.AlbumDetailViewModelFactory
import com.jesus.planetadelasfiestas.ViewModel.MainViewModel
import com.jesus.planetadelasfiestas.data.AppTheme
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.model.Routes
import com.jesus.planetadelasfiestas.ui.components.BottomNavigationBar
import com.jesus.planetadelasfiestas.ui.screens.AlbumDetailScreen
import com.jesus.planetadelasfiestas.ui.screens.AlbumListCompactScreen
import com.jesus.planetadelasfiestas.ui.screens.AlbumListMedExpScreen
import com.jesus.planetadelasfiestas.ui.screens.DetailItemScreen
import com.jesus.planetadelasfiestas.ui.screens.FavListCompactScreen
import com.jesus.planetadelasfiestas.ui.screens.FavListMedExpScreen
import com.jesus.planetadelasfiestas.ui.screens.ProfileCompactScreen
import com.jesus.planetadelasfiestas.ui.theme.about.AboutScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = applicationContext as Application

            val mainViewModel: MainViewModel = hiltViewModel()

            val appTheme by mainViewModel.appTheme.collectAsState()
            val isDarkTheme = when (appTheme) {
                AppTheme.LIGHT -> false
                AppTheme.DARK -> true
                AppTheme.SYSTEM -> isSystemInDarkTheme()
            }

            val windowSize = LocalConfiguration.current.screenWidthDp.let {
                when {
                    it <= 600 -> WindowWidthSizeClass.Compact
                    it <= 1024 -> WindowWidthSizeClass.Medium
                    else -> WindowWidthSizeClass.Expanded
                }
            }

            PlanetaDeLasFiestasTheme(darkTheme = isDarkTheme, dynamicColor = false) {
                PlanetaDeLasFiestasApp(
                    viewModel = mainViewModel,
                    favoriteAlbums = mainViewModel.favoriteAlbums.collectAsState().value,
                    handleFavoriteClick = { mainViewModel.toggleFavorite(it) },
                    onDeleteAlbum = { album -> mainViewModel.deleteAlbum(album) },
                    windowSize = windowSize,
                )
            }
        }
    }

    @Composable
    fun PlanetaDeLasFiestasApp(
        viewModel: MainViewModel,
        favoriteAlbums: Set<Long>,
        handleFavoriteClick: (Album) -> Unit,
        onDeleteAlbum: (Album) -> Unit,
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
                    val albums by viewModel.albums.collectAsState()

                    if (windowSize == WindowWidthSizeClass.Compact) {
                        AlbumListCompactScreen(
                            viewModel = viewModel,
                            favoriteAlbums = favoriteAlbums,
                            onFavoriteClick = handleFavoriteClick,
                            onDeleteAlbum = onDeleteAlbum,
                            navController = navController,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        AlbumListMedExpScreen(
                            viewModel = viewModel,
                            favoriteAlbums = favoriteAlbums,
                            onFavoriteClick = handleFavoriteClick,
                            onDeleteAlbum = onDeleteAlbum,
                            navController = navController,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                composable(Routes.FavList) {
                    val albums by viewModel.albums.collectAsState()
                    val favAlbums by viewModel.favoriteAlbumsList.collectAsState()

                    if (windowSize == WindowWidthSizeClass.Compact) {
                        FavListCompactScreen(
                            albums = favAlbums,
                            favoriteAlbums = favAlbums.map { it.id }.toSet(),
                            onFavoriteClick = handleFavoriteClick,
                            onDeleteAlbum = onDeleteAlbum,
                            onDetailsClick = { album ->
                                navController.navigate(Routes.albumDetailRoute(album.id))
                            },
                            navController = navController,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        FavListMedExpScreen(
                            albums = favAlbums,
                            onFavoriteClick = handleFavoriteClick,
                            onDeleteAlbum = onDeleteAlbum,
                            favoriteAlbums = favAlbums.map { it.id }.toSet(),
                            onDetailsClick = { album ->
                                navController.navigate(Routes.albumDetailRoute(album.id))
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
                    route = Routes.albumDetailRoute,
                    arguments = listOf(navArgument("albumId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val albumId = backStackEntry.arguments?.getLong("albumId") ?: return@composable

                    AlbumDetailScreen(
                        albumId = albumId,
                        mainViewModel = viewModel,
                        onBackClick = { navController.popBackStack() },
                        onDeleteAlbum = { album ->
                            onDeleteAlbum(album)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}