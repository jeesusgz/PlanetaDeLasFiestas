package com.jesus.planetadelasfiestas.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.jesus.planetadelasfiestas.model.Datasource.albumList
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import com.jesus.planetadelasfiestas.ui.components.AlbumCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElemListScreen() {
    val albums = albumList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Álbumes") },
            )
        },
        content = { paddingValues ->
            LazyColumn(
                contentPadding = paddingValues
            ) {
                items(albums) { album ->
                    AlbumCard(album = album, onFavoriteClick = {

                    })
                }
            }
        }
    )
}