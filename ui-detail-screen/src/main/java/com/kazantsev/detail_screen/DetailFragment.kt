package com.kazantsev.detail_screen

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.kazantsev.detail_screen.databinding.DetailFragmentBinding
import com.kazantsev.ui_common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailFragmentBinding>() {

    private val viewModel: DetailViewModel by viewModels()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { FilmAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listVideo.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.flowWithLifecycle(lifecycle).collect(::handleEvent)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(lifecycle).collect(::renderState)
        }
        binding.favorite.setOnClickListener { viewModel.favorite() }
    }

    private fun handleEvent(event: DetailsEvent) {
        if (event is DetailsEvent.ShowErrorEvent) {
            Snackbar.make(binding.root, event.errorMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun renderState(state: DetailsState) {
        with(binding) {
            progressInfo.isVisible = state.isLoadingInfo
            progressFilm.isVisible = state.isLoadingFilms
            content.isVisible = !state.isLoadingInfo
            tvName.text = state.name
            tvBirthYear.text = state.birth_year
            tvGender.text = state.gender
            tvNoData2.isVisible = state.films.isNullOrEmpty() && !state.isLoadingFilms
            adapter.submitList(state.films)
            favorite.setColorFilter(if (state.favorite) Color.MAGENTA else Color.GRAY)
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = DetailFragmentBinding.inflate(inflater, container, false)
}