package com.example.rickandmortyapp.presentation

import androidx.lifecycle.ViewModel
import com.example.rickandmortyapp.data.CharacterListResponse
import com.example.rickandmortyapp.domain.repository.RickMortyRepository
import com.example.rickandmortyapp.presentation.model.RickMortyEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RickAndMortyViewModel(
    private val rickMortyRepository: RickMortyRepository
): ViewModel() {

    private val _characterList = MutableStateFlow(CharacterListResponse())
    val characterList: StateFlow<CharacterListResponse>
        get() = _characterList.asStateFlow()

    private val _controlEventsFlow = MutableSharedFlow<RickMortyEvent>()
    val controlEventsFlow: SharedFlow<RickMortyEvent> get() = _controlEventsFlow.asSharedFlow()

    suspend fun emitControlEvent(controlEvent: RickMortyEvent) = _controlEventsFlow.emit(controlEvent)

    init {
        getList()
    }

    fun getList(page: Int = 0) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = rickMortyRepository.getLatestRates(page)
            val currentList = _characterList.value.results ?: emptyList()
            val newResults = response.results ?: emptyList()
            _characterList.updateValue { copy(info = response.info ,results = currentList + newResults) }
        }
    }
}

private inline fun <T> MutableStateFlow<T>.updateValue(block: T.() -> T) {
    value = value.block()
}