package com.jesus.planetadelasfiestas.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jesus.planetadelasfiestas.R
import com.jesus.planetadelasfiestas.ViewModel.ProfileViewModel
import com.jesus.planetadelasfiestas.ViewModel.ProfileViewModelFactory
import com.jesus.planetadelasfiestas.data.AppTheme
import com.jesus.planetadelasfiestas.ui.components.ImageComp
import com.jesus.planetadelasfiestas.ui.components.StandardButtonComp
import com.jesus.planetadelasfiestas.ui.components.StandardInputTextComp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCompactScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(context.applicationContext as Application)
    )

    // Observamos el estado desde ViewModel
    val profileName by viewModel.username.collectAsState()
    val selectedTheme by viewModel.theme.collectAsState()
    val isLogged by viewModel.isLogged.collectAsState() // si tienes esta propiedad en el VM

    var tempProfileName by remember { mutableStateOf(profileName) }

    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(
                    text = if (profileName.isBlank())
                        stringResource(id = R.string.profile_default_title)  // "Perfil de usuario"
                    else
                        "Perfil de $profileName"
                )
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            StandardInputTextComp(
                label = stringResource(id = R.string.profile_name),
                text = tempProfileName,
                modifier = Modifier.padding(8.dp),
                onValueChange = { tempProfileName = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { viewModel.setUsername(tempProfileName) },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = stringResource(id = R.string.save))
            }

            Spacer(modifier = Modifier.height(16.dp))

            ImageComp(
                drawable = R.drawable.ic_launcher,
                modifier = Modifier.clip(CircleShape),
                height = 250,
                width = 250
            )

            Spacer(modifier = Modifier.height(16.dp))

            StandardButtonComp(
                text = if (isLogged) stringResource(id = R.string.logout) else stringResource(id = R.string.login),
                modifier = Modifier.padding(8.dp),
                onClick = { viewModel.toggleLogin() } // función en VM para cambiar estado
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Tema de la aplicación:", style = MaterialTheme.typography.titleMedium)

            Row(modifier = Modifier.fillMaxWidth()) {
                ThemeRadioButton("Claro", AppTheme.LIGHT, selectedTheme) { viewModel.setTheme(it) }
                ThemeRadioButton("Oscuro", AppTheme.DARK, selectedTheme) { viewModel.setTheme(it) }
                ThemeRadioButton("Sistema", AppTheme.SYSTEM, selectedTheme) { viewModel.setTheme(it) }
            }
        }
    }
}

@Composable
fun ThemeRadioButton(
    label: String,
    theme: AppTheme,
    selectedTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 16.dp)
    ) {
        RadioButton(
            selected = selectedTheme == theme,
            onClick = { onThemeSelected(theme) }
        )
        Text(label, modifier = Modifier.padding(start = 4.dp))
    }
}

