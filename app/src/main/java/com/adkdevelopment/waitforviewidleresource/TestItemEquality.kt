package com.adkdevelopment.waitforviewidleresource

import androidx.recyclerview.widget.DiffUtil

object TestItemEquality : DiffUtil.ItemCallback<TestItem>() {

    override fun areItemsTheSame(oldItem: TestItem, newItem: TestItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TestItem, newItem: TestItem): Boolean {
        return oldItem == newItem
    }

}