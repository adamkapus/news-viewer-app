package hu.autsoft.nytimesmostpopular.ui.list.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.autsoft.nytimesmostpopular.R
import hu.autsoft.nytimesmostpopular.databinding.NewsListItemBinding
import hu.autsoft.nytimesmostpopular.model.News

class NewsRecyclerViewAdapter(private val context: Context) :
    ListAdapter<News, NewsRecyclerViewAdapter.ViewHolder>(NewsComparator) {
    private lateinit var binding: NewsListItemBinding
    private var newsList = emptyList<News>()

    private var itemClickListener: ((news: News) -> Unit)? = null

    fun setOnItemClickListener(listener: ((news: News) -> Unit)?) {
        itemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = newsList[position]

        holder.news = news
        holder.titleText.text = news.title
        holder.byLineText.text = news.byline
        holder.datePublishedText.text = news.published_date

        Glide.with(context)
            .load(news.thumbnailUrl)
            .error(R.drawable.ic_default_news_thumbnail)
            .circleCrop()
            .into(holder.thumbnailImage)
    }


    fun replaceList(news: List<News>) {
        newsList = news
        submitList(newsList)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnailImage: ImageView = binding.itemThumbnailImage
        val titleText: TextView = binding.itemTitleText
        val byLineText: TextView = binding.itemByLineText
        val datePublishedText: TextView = binding.itemDatePublishedText

        var news: News? = null

        init {
            itemView.setOnClickListener {
                news?.let { itemClickListener?.let { it1 -> it1(it) } }
            }
        }
    }
}


