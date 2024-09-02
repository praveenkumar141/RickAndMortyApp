package com.example.rickandmortyapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.rickandmortyapp.util.CHARACTER_ID
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import androidx.navigation.fragment.findNavController

class CharacterDetailsFragment : Fragment() {

    private val viewModel by sharedViewModel<RickAndMortyViewModel>()
    private var characterId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        characterId = arguments?.getInt(CHARACTER_ID) ?: 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.controlEventsFlow.onEach { onControlEvent(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val state = viewModel.currencyData.collectAsState().value.results?.getOrNull(characterId-1)
                if (state != null) {
                    val coroutineScope = rememberCoroutineScope()
                    CharacterDetailsScreen(state) {
                        coroutineScope.launch {
                            viewModel.emitControlEvent(it)
                        }
                    }
                }
            }
        }
    }

    private fun onControlEvent(controlEvent: RickMortyEvent) {
        when (controlEvent) {
            is RickMortyEvent.NavigateBack -> {
                findNavController().popBackStack()
            }
            else -> Unit
        }
    }
}