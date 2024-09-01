package com.example.rickandmortyapp.di

import com.example.rickandmortyapp.data.RickMortyApi
import com.example.rickandmortyapp.data.RickMortyRepositoryImpl
import com.example.rickandmortyapp.domain.repository.RickMortyRepository
import com.example.rickandmortyapp.presentation.RickAndMortyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { RickMortyApi() }
    factory<RickMortyRepository> { RickMortyRepositoryImpl(rickMortyApi = get()) }
    viewModel { RickAndMortyViewModel(rickMortyRepository = get()) }
}
