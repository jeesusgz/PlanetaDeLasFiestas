package com.jesus.planetadelasfiestas.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jesus.planetadelasfiestas.model.Album


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailItemScreen(
    album: Album,
    isFavorite: Boolean,
    onFavoriteClick: (Album) -> Unit,
    onDeleteAlbum: (Album) -> Unit,
    onBackClick: () -> Unit,
    comments: List<String>,
    onAddComment: (String) -> Unit,
    showComments: Boolean = true
) {
    var commentText by remember { mutableStateOf("") }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Función auxiliar para evitar strings nulos o vacíos
    fun safeString(str: String?): String = str?.takeIf { it.isNotBlank() } ?: "Desconocido"

    // Función auxiliar para urls de imagen
    fun safeImageUrl(url: String?): String = url?.takeIf { it.isNotBlank() }
        ?: "https://via.placeholder.com/150" // URL genérica para imagen por defecto

    // URL del cover y artista seguros
    val coverUrl = safeImageUrl(album.coverUrl)
    val artistPictureUrl = safeImageUrl(album.artistPicture)
    val albumLink = album.link.takeIf { it.isNotBlank() } ?: "https://www.deezer.com"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Detalles del Álbum",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
            }
        )

        AsyncImage(
            model = coverUrl,
            contentDescription = safeString(album.title),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Text(
            text = safeString(album.title),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = artistPictureUrl,
                contentDescription = safeString(album.artistName),
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
            Text(
                text = safeString(album.artistName),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Text(
            text = "Fecha de lanzamiento: ${safeString(album.releaseDate)}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
        )

        Text(
            text = "Número de canciones: ${album.numberOfTracks}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
        )

        Text(
            text = "Duración: ${album.duration / 60}m ${album.duration % 60}s",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
        )

        Text(
            text = "Género ID: ${album.genreId}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
        )

        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(albumLink))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Text("Ver en Deezer")
        }

        IconButton(
            onClick = { onFavoriteClick(album) },
            modifier = Modifier
                .align(Alignment.End)
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorito",
                tint = if (isFavorite) Color.Red else Color.Gray
            )
        }

        if (showComments && isFavorite) {
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = "Comentarios",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            comments.forEach { comment ->
                Text(
                    text = "• $comment",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = commentText,
                onValueChange = { commentText = it },
                label = { Text("Añadir comentario") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        if (commentText.isNotBlank()) {
                            onAddComment(commentText)
                            commentText = ""
                        }
                    },
                    enabled = commentText.isNotBlank()
                ) {
                    Text("Enviar")
                }
            }
        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Confirmar borrado") },
                text = { Text("¿Quieres borrar el álbum \"${safeString(album.title)}\" de favoritos?") },
                confirmButton = {
                    TextButton(onClick = {
                        onDeleteAlbum(album)
                        showDeleteDialog = false
                        onBackClick()
                    }) {
                        Text("Borrar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}