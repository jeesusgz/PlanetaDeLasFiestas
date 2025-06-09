package com.jesus.planetadelasfiestas.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ConfirmDeleteDialog(
    albumName: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(text = "Eliminar álbum")
        },
        text = {
            Text(text = "¿Estás seguro de que quieres eliminar \"$albumName\" de favoritos?")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Eliminar")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text = "Cancelar")
            }
        }
    )
}
