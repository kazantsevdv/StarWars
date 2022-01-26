package com.kazantsev.detail_screen

import android.graphics.Color

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.kazantsev.detail_screen.databinding.DetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()
    private var _viewBinding: DetailFragmentBinding? = null
    private val binding get() = checkNotNull(_viewBinding)
    private val viewModel: DetailViewModel by viewModels()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { FilmAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _viewBinding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listVideo.adapter = adapter
        listenEvents()
        listenState()
        binding.favorite.setOnClickListener { viewModel.favorite() }
    }

    private fun listenEvents() = lifecycleScope.launch {
        viewModel.events.flowWithLifecycle(lifecycle).collect(::handleEvent)
    }

    private fun handleEvent(event: DetailsEvent) = when (event) {
        is DetailsEvent.ShowErrorEvent -> Snackbar.make(binding.root,
            event.errorMessage,
            Snackbar.LENGTH_SHORT).show()
    }

    private fun listenState() = lifecycleScope.launch {
        viewModel.state.flowWithLifecycle(lifecycle).collect(::renderState)
    }

    private fun renderState(state: DetailsState) {
        with(binding) {
            progressInfo.isVisible=state.isLoadingInfo
            progressFilm.isVisible= state.isLoadingFilms
            content.isVisible = !state.isLoadingInfo
            tvName.text = state.name
            tvBirthYear.text = state.birth_year
            tvGender.text = state.gender
            tvNoData2.isVisible = state.films.isNullOrEmpty() && !state.isLoadingFilms
            adapter.submitList(state.films)
            if (state.favorite) {
                favorite.setColorFilter(Color.MAGENTA)
            } else {
                favorite.setColorFilter(Color.GRAY)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }
}