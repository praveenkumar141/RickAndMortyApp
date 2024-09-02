package com.example.rickandmortyapp.presentation

sealed interface RickMortyEvent {
    data class CharacterClicked(val id: Int): RickMortyEvent
    data class LoadNextPage(val page: Int): RickMortyEvent
    object NavigateBack: RickMortyEvent
}