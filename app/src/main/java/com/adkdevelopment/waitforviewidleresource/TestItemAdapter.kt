package com.adkdevelopment.waitforviewidleresource

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TestItemAdapter : ListAdapter<TestItem, RecyclerView.ViewHolder>(TestItemEquality) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TestItemViewHolder(inflater.inflate(R.layout.item_list_test, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TestItemViewHolder).bind(getItem(position))
    }

}