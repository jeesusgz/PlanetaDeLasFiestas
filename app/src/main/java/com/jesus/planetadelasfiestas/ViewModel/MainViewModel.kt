package com.jesus.planetadelasfiestas.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import androidx.lifecycle.viewModelScope
import com.jesus.planetadelasfiestas.data.AppTheme
import com.jesus.planetadelasfiestas.data.UserPreferences

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = UserPreferences(application)

    val appTheme: StateFlow<AppTheme> = prefs.theme.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AppTheme.SYSTEM
    )
}