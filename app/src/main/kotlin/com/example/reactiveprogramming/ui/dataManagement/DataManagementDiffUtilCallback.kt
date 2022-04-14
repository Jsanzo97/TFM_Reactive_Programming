package com.example.reactiveprogramming.ui.dataManagement

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.entity.Team

class DataManagementDiffUtilCallback(
    private val newList: List<Team>,
    private val oldList: List<Team>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition] == oldList[oldItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition].name == oldList[oldItemPosition].name &&
               newList[newItemPosition].sport == oldList[oldItemPosition].sport &&
               newList[newItemPosition].players == oldList[oldItemPosition].players
    }

}
