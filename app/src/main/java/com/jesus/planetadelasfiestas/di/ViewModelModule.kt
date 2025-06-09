package com.jesus.planetadelasfiestas.di

import com.jesus.planetadelasfiestas.ViewModel.FavoritosViewModel
import com.jesus.planetadelasfiestas.repository.DeezerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    fun provideFavoritosViewModel(repository: DeezerRepository): FavoritosViewModel {
        return FavoritosViewModel(repository)
    }
}