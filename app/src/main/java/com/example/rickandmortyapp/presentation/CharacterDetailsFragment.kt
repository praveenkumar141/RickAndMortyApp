package com.example.rickandmortyapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.rickandmortyapp.presentation.composables.CharacterDetailsScreen
import com.example.rickandmortyapp.util.CHARACTER_ID
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CharacterDetailsFragment : Fragment() {

    private val viewModel by sharedViewModel<RickAndMortyViewModel>()
    private var characterId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        characterId = arguments?.getInt(CHARACTER_ID) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val state = viewModel.characterList.collectAsState().value.results?.getOrNull(characterId-1)
                if (state != null) {
                    CharacterDetailsScreen(state)
                }
            }
        }
    }
}