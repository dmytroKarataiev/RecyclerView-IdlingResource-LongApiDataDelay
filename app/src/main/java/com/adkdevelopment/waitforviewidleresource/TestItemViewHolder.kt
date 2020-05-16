package com.adkdevelopment.waitforviewidleresource

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TestItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    fun bind(item: TestItem) {
        view.findViewById<TextView>(R.id.title).text = item.name
        view.findViewById<TextView>(R.id.id).text = item.id.toString()
    }

}