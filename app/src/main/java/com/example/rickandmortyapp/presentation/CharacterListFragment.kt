package com.example.rickandmortyapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.util.CHARACTER_ID
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CharacterListFragment : Fragment() {
    private val viewModel by sharedViewModel<RickAndMortyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ComposeView>(R.id.compose_view).setContent {
            val state = viewModel.currencyData.collectAsState().value
            val coroutineScope = rememberCoroutineScope()
            RickMortyParentUI(state) {
                coroutineScope.launch {
                    viewModel.emitControlEvent(it)
                }
            }
        }
        childFragmentManager.addOnBackStackChangedListener {
            if (childFragmentManager.backStackEntryCount == 0) {
                view.findViewById<ComposeView>(R.id.compose_view).visibility = View.VISIBLE
            }
        }
        viewModel.controlEventsFlow.onEach { onControlEvent(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
        parentFragmentManager.beginTransaction().setPrimaryNavigationFragment(this).commit()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (childFragmentManager.backStackEntryCount > 0) {
                childFragmentManager.popBackStack()
            } else {
                requireActivity().finish()
            }
        }
    }


    private fun onControlEvent(controlEvent: RickMortyEvent) {
        when (controlEvent) {
            is RickMortyEvent.CharacterClicked -> openDetailFragment(controlEvent.id)
            is RickMortyEvent.LoadNextPage -> viewModel.getList(page = controlEvent.page)
            RickMortyEvent.NavigateBack -> TODO()
        }
    }

    private fun openDetailFragment(characterId: Int) {
        view?.findViewById<ComposeView>(R.id.compose_view)?.visibility = View.GONE

        val childFragment = CharacterDetailsFragment()

        val args = Bundle().apply {
            putInt(CHARACTER_ID, characterId)
        }
        childFragment.arguments = args

        childFragmentManager.beginTransaction()
            .replace(R.id.child_fragment_container, childFragment)
            .addToBackStack(null)
            .commit()
    }
}


