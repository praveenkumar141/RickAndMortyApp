package com.example.rickandmortyapp.data

import com.example.rickandmortyapp.domain.repository.RickMortyRepository

class RickMortyRepositoryImpl(
    private val rickMortyApi: RickMortyApi
) : RickMortyRepository {
    override suspend fun getLatestRates(page: Int): CharacterListResponse {
        return try {
            rickMortyApi.getCharacterList(page)
        } catch (e: Exception) {
            println("Error fetching character list: ${e.message}")
            throw e
        }
    }
}