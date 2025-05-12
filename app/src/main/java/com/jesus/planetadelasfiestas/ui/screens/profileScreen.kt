package com.jesus.planetadelasfiestas.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jesus.planetadelasfiestas.R
import com.jesus.planetadelasfiestas.ui.components.ImageComp
import com.jesus.planetadelasfiestas.ui.components.StandardButtonComp
import com.jesus.planetadelasfiestas.ui.components.StandardInputTextComp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCompactScreen(modifier: Modifier = Modifier) {
    var profileName by remember { mutableStateOf("") }
    var isLogged by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.profile_title)) }
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
                text = profileName,
                modifier = Modifier.padding(8.dp),
                onValueChange = { profileName = it }
            )

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
                onClick = { isLogged = !isLogged }
            )
        }
    }
}
