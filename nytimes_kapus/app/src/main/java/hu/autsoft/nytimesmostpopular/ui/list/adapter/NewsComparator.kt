package hu.autsoft.nytimesmostpopular.ui.list.adapter

import androidx.recyclerview.widget.DiffUtil
import hu.autsoft.nytimesmostpopular.model.News

object NewsComparator : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }
}