package com.example.feature.detail

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.navArgs
import com.example.base.BaseFragment
import com.example.feature.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val args : DetailFragmentArgs by navArgs()
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {

        args.university.let { university ->
            binding.university = university
            binding.tvUniversityWebPage.text = university.web_pages.getOrNull(0) ?: "N/A"
        }

        binding.ivRefresh.setOnClickListener {
            args.refreshCallback.onRefreshRequested()
        }

    }

}