package com.example.rickandmortyapp.domain.repository

import com.example.rickandmortyapp.data.CharacterListResponse

interface RickMortyRepository {
    suspend fun getLatestRates(page: Int): CharacterListResponse
}