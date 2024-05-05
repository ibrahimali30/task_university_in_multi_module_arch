package com.example.task.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.base.BaseRecyclerAdapter
import com.example.task.databinding.RowUniversityItemLayoutBinding
import com.example.model.UniversityUiModel

/**
 * Adapter class for RecyclerView
 */
class UniversityAdapter constructor(
    private val clickFunc : ((UniversityUiModel?) -> Unit)
) : BaseRecyclerAdapter<UniversityUiModel, RowUniversityItemLayoutBinding, UniversityViewHolder>(
    UniversityItemDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityViewHolder {
        val binding = RowUniversityItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return UniversityViewHolder(binding = binding, onItemClicked = clickFunc)

    }

}

class UniversityItemDiffUtil : DiffUtil.ItemCallback<UniversityUiModel>() {
    override fun areItemsTheSame(oldItem: UniversityUiModel, newItem: UniversityUiModel): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: UniversityUiModel, newItem: UniversityUiModel): Boolean {
        return oldItem == newItem
    }
}