package com.example.task.ui.main

import com.example.base.BaseViewHolder
import com.example.task.databinding.RowUniversityItemLayoutBinding
import com.example.model.UniversityUiModel

/**
 * ViewHolder class for University
 */
class UniversityViewHolder constructor(
    private val binding : RowUniversityItemLayoutBinding,
    private val onItemClicked : ((UniversityUiModel?) -> Unit) = {}
) : BaseViewHolder<UniversityUiModel, RowUniversityItemLayoutBinding>(binding) {


    override fun bind() {

        binding.root.setOnClickListener {
            onItemClicked(getRowItem())
        }

        getRowItem()?.let {
            binding.university = it

            binding.executePendingBindings()

        }
    }
}