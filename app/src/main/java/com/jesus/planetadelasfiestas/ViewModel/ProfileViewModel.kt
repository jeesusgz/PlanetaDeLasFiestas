package com.jesus.planetadelasfiestas.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jesus.planetadelasfiestas.data.AppTheme
import com.jesus.planetadelasfiestas.data.UserPreferences
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = UserPreferences(application)

    val username: StateFlow<String> = prefs.username.stateIn(
        viewModelScope, SharingStarted.Lazily, ""
    )

    val theme: StateFlow<AppTheme> = prefs.theme.stateIn(
        viewModelScope, SharingStarted.Lazily, AppTheme.SYSTEM
    )

    val isLogged: StateFlow<Boolean> = prefs.isLogged.stateIn(
        viewModelScope, SharingStarted.Lazily, false
    )

    fun setUsername(name: String) = viewModelScope.launch {
        prefs.setUsername(name)
    }

    fun setTheme(theme: AppTheme) = viewModelScope.launch {
        prefs.setTheme(theme)
    }

    fun toggleLogin() = viewModelScope.launch {
        val current = isLogged.value
        prefs.setIsLogged(!current)
    }
}