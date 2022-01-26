package com.kazantsev.ui_favorite_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kazantsev.navigation.NavigationFlow
import com.kazantsev.navigation.ToFlowNavigatable
import com.kazantsev.ui_favorite_screen.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private var _viewBinding: FragmentFavoriteBinding? = null
    private val binding get() = checkNotNull(_viewBinding)
    private val viewModel: FavoriteViewModel by viewModels()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        FavoriteAdapter(onListItemClickListener, onItemFavoriteClickListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _viewBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataList
                    .collectLatest {
                        binding.tvNoData.isVisible=it.isNullOrEmpty()
                        adapter.submitList(it)
                    }
            }
        }
        binding.list.adapter = adapter
    }

    private val onListItemClickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(personUrl: String,name:String) {
                (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.DetailFlow(
                    personUrl))
            }
        }

    private val onItemFavoriteClickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(personUrl: String,name:String) {
                viewModel.favorite(personUrl,name)
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }
}