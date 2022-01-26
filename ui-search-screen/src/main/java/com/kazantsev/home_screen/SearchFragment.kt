package com.kazantsev.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.kazantsev.home_screen.databinding.SearchFragmentBinding
import com.kazantsev.navigation.NavigationFlow
import com.kazantsev.navigation.ToFlowNavigatable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _viewBinding: SearchFragmentBinding? = null
    private val binding get() = checkNotNull(_viewBinding)
    private val viewModel: SearchViewModel by viewModels()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        PersonAdapter(onListItemClickListener, onItemFavoriteClickListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewBinding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.person
                    .collectLatest {
                        adapter.submitData(it)
                    }
            }
        }
        binding.list.adapter = adapter
            .withLoadStateHeaderAndFooter(
                header = LoaderStateAdapter { adapter.retry() },
                footer = LoaderStateAdapter { adapter.retry() }
            )
        loadStateRefresh()
        binding.searchInput.doAfterTextChanged { text ->
            viewModel.onNewQuery(text.toString() ?: "")
        }
        viewModel.query
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach(::updateSearchQuery)
            .launchIn(lifecycleScope)

    }

    private fun updateSearchQuery(searchQuery: String) {
        with(binding.searchInput) {
            if ((text?.toString() ?: "") != searchQuery) {
                setText(searchQuery)
            }
        }
    }

    private val onListItemClickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(personUrl: String, name: String) {
                (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.DetailFlow(
                    personUrl))
            }
        }

    private val onItemFavoriteClickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(personUrl: String, name: String) {
                viewModel.favorite(personUrl, name)
            }
        }

    private fun loadStateRefresh() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collectLatest { loadStates ->
                    val refresh = loadStates.refresh
                    binding.searchProgress.isVisible = loadStates.refresh is LoadState.Loading
                    if (refresh is LoadState.Error) {
                        Snackbar.make(
                            binding.root,
                            refresh.error.localizedMessage ?: "",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    binding.tvNoData.isVisible =
                        loadStates.source.refresh is LoadState.NotLoading && loadStates.append.endOfPaginationReached && adapter.itemCount < 1
                    binding.list.isVisible = !(
                            loadStates.source.refresh is LoadState.NotLoading && loadStates.append.endOfPaginationReached && adapter.itemCount < 1)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }
}