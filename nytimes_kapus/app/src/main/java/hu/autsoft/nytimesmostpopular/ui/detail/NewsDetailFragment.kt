package hu.autsoft.nytimesmostpopular.ui.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import hu.autsoft.nytimesmostpopular.R
import hu.autsoft.nytimesmostpopular.databinding.FragmentNewsDetailBinding
import hu.autsoft.nytimesmostpopular.helper.InternetConnectionStatusProvider
import javax.inject.Inject

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {
    private lateinit var binding: FragmentNewsDetailBinding

    private val newsDetailViewModel: NewsDetailViewModel by viewModels()

    @Inject
    lateinit var internetConnectionStatusProvider: InternetConnectionStatusProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentNewsDetailBinding.inflate(layoutInflater, container, false)


        newsDetailViewModel.news.observe(
            viewLifecycleOwner
        ) { news ->
            Glide.with(this)
                .load(news.largeImageUrl)
                .into(binding.detailNewsImage)
            binding.detailTitleText.text = news.title
            binding.detailByLineText.text = news.byline
            binding.detailDateText.text = news.published_date

            binding.viewNewsButton.setOnClickListener {
                news.url?.let { url -> onViewArticle(url) }
            }
        }

        val newsId = NewsDetailFragmentArgs.fromBundle(requireArguments()).id.toDouble()
        newsDetailViewModel.loadNews(newsId)

        return binding.root
    }

    private fun onViewArticle(url: String) {
        if (internetConnectionStatusProvider.internetConnectionIsActive.value == true) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
            try {
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                Snackbar.make(
                    binding.root,
                    R.string.news_detail_open_link_browser_not_installed,
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
        } else {
            Snackbar.make(
                binding.root,
                R.string.news_detail_open_link_no_internet,
                Snackbar.LENGTH_SHORT
            )
                .show()
        }

    }

}