package com.kazantsev.ui_favorite_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.kazantsev.navigation.RootNavDirections
import com.kazantsev.ui_common.base.BaseFragment
import com.kazantsev.ui_common.util.findTopNavController
import com.kazantsev.ui_favorite_screen.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    private val viewModel: FavoriteViewModel by viewModels()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        FavoriteAdapter(onListItemClickListener, onItemFavoriteClickListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.dataList
                .flowWithLifecycle(lifecycle)
                .collectLatest {
                    binding.tvNoData.isVisible = it.isNullOrEmpty()
                    adapter.submitList(it)
                }
        }
        binding.list.adapter = adapter
    }

    private val onListItemClickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(personUrl: String, name: String) {
                findTopNavController().navigate(
                    RootNavDirections.actionDetailFlow(
                        personUrl
                    )
                )
            }
        }

    private val onItemFavoriteClickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(personUrl: String, name: String) {
                viewModel.favorite(personUrl, name)
            }
        }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentFavoriteBinding.inflate(inflater, container, false)
}