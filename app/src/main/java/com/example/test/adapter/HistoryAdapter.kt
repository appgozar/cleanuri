package com.example.test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data.LinkItem
import com.example.test.databinding.ListItemHistoryBinding
import com.example.test.util.Util
import com.google.android.material.snackbar.Snackbar

class HistoryAdapter : ListAdapter<LinkItem, HistoryAdapter.ViewHolder>(DiffUtil()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binder = ListItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(private val binder : ListItemHistoryBinding) : RecyclerView.ViewHolder(binder.root){
        init {
            binder.ivCopyLink.setOnClickListener {
                val position = adapterPosition
                if(position >= 0){
                    val item = getItem(position)
                    Util.copyToClipboard(it.context, item.url)
                    Snackbar.make(binder.root, R.string.copied_to_clipboard, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        fun onBind(linkItem: LinkItem){
            binder.tvShortenLink.text = linkItem.url
            binder.tvOriginalLink.text = linkItem.originalUrl
        }
    }
}

private class DiffUtil : DiffUtil.ItemCallback<LinkItem>(){
    override fun areItemsTheSame(oldItem: LinkItem, newItem: LinkItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LinkItem, newItem: LinkItem): Boolean {
        return true
    }
}