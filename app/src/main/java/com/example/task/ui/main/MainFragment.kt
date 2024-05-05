package com.example.task.ui.main

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.base.BaseFragment
import com.example.task.databinding.FragmentMainBinding
import com.example.task.ui.contract.MainContract
import com.example.task.ui.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 * Main Fragment
 */
@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val viewModel: MainViewModel by viewModels()
    private val adapter: UniversityAdapter by lazy {
        UniversityAdapter { university ->
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(
                university!!,
                 object : com.example.feature.detail.RefreshCallback {
                    override fun onRefreshRequested() {
                        onRefreshCallBackRequested()
                    }
                }
            )

            findNavController().navigate(action)
        }
    }

    private fun onRefreshCallBackRequested() {
        findNavController().popBackStack()
        viewModel.onRefreshCallBackRequested()
    }

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        val dividerItemDecoration = com.example.base.DividerItemDecoration(requireContext())
        binding.rvUniversity.addItemDecoration(dividerItemDecoration)
        binding.rvUniversity.adapter = adapter
        initObservers()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchUniversities()
    }

    private fun fetchUniversities() {
        viewModel.fetchUniversity()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (val state = it.universityState) {
                        is MainContract.UniversityState.Empty -> {
                            binding.loadingPb.isVisible = false
                            showErrorView()
                        }
                        is MainContract.UniversityState.Loading -> {
                            binding.loadingPb.isVisible = true
                            hideErrorView()
                        }
                        is MainContract.UniversityState.Success -> {
                            val data = state.universityList
                            adapter.submitList(data)
                            binding.loadingPb.isVisible = false
                        }
                    }
                }
            }
        }

    }

    private fun hideErrorView() {
        binding.errorMsgContainer.isVisible = false
    }

    private fun showErrorView() {
        binding.errorMsgContainer.isVisible = true
        binding.btTryAgain.setOnClickListener {
            fetchUniversities()
        }
    }
}