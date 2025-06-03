package com.jesus.planetadelasfiestas.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

enum class AppTheme { SYSTEM, LIGHT, DARK }

class UserPreferences(private val context: Context) {

    companion object {
        val USERNAME_KEY = stringPreferencesKey("username")
        val THEME_KEY = intPreferencesKey("theme_mode")
        val IS_LOGGED_KEY = booleanPreferencesKey("is_logged")  // <-- aquí
    }

    val username: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[USERNAME_KEY] ?: ""
    }

    val theme: Flow<AppTheme> = context.dataStore.data.map { prefs ->
        when (prefs[THEME_KEY] ?: 0) {
            1 -> AppTheme.LIGHT
            2 -> AppTheme.DARK
            else -> AppTheme.SYSTEM
        }
    }

    val isLogged: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[IS_LOGGED_KEY] ?: false     // <-- flujo para el estado de login
    }

    suspend fun setUsername(name: String) {
        context.dataStore.edit { prefs ->
            prefs[USERNAME_KEY] = name
        }
    }

    suspend fun setTheme(theme: AppTheme) {
        context.dataStore.edit { prefs ->
            prefs[THEME_KEY] = when (theme) {
                AppTheme.LIGHT -> 1
                AppTheme.DARK -> 2
                AppTheme.SYSTEM -> 0
            }
        }
    }

    suspend fun setIsLogged(logged: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[IS_LOGGED_KEY] = logged   // <-- función para guardar login
        }
    }
}