package com.example.clubdeportivo.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

object GenericRecyclerAdapterUtil {

    fun <T> createAdapter(
        items: List<T>,
        layoutResId: Int,
        onBindView: (itemView: ViewGroup, item: T, position: Int) -> Unit
    ): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(layoutResId, parent, false)
                return object : RecyclerView.ViewHolder(view) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                if (items.isNotEmpty()) {
                    val item = items[position]
                    onBindView(holder.itemView as ViewGroup, item, position)
                }
            }

            override fun getItemCount(): Int = items.size
        }
    }
}