package hu.autsoft.nytimesmostpopular.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import hu.autsoft.nytimesmostpopular.databinding.FragmentNewsListBinding
import hu.autsoft.nytimesmostpopular.model.News
import hu.autsoft.nytimesmostpopular.model.Result
import hu.autsoft.nytimesmostpopular.ui.list.adapter.NewsRecyclerViewAdapter

@AndroidEntryPoint
class NewsListFragment : Fragment() {
    private lateinit var binding: FragmentNewsListBinding
    private lateinit var newsRecyclerViewAdapter: NewsRecyclerViewAdapter

    private val newsListViewModel: NewsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentNewsListBinding.inflate(layoutInflater, container, false)
        setupRecyclerView()

        binding.swiperefreshContainer.setOnRefreshListener {
            newsListViewModel.refreshNews()
            binding.swiperefreshContainer.isRefreshing = false
        }

        newsListViewModel.getNewsResult().observe(
            viewLifecycleOwner
        ) {
            when (it) {
                is Result.Success -> newsRecyclerViewAdapter.replaceList(it.data)
                is Result.Error -> Snackbar.make(binding.root, it.error, Snackbar.LENGTH_SHORT)
                    .show()
                is Result.NetworkError -> Snackbar.make(
                    binding.root,
                    it.error,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        newsListViewModel.initNews()

        return binding.root
    }

    private fun setupRecyclerView() {
        newsRecyclerViewAdapter = NewsRecyclerViewAdapter(context!!)
        newsRecyclerViewAdapter.setOnItemClickListener(this::onItemClick)
        val rvNewsList = binding.newsList
        rvNewsList.layoutManager = LinearLayoutManager(context)
        rvNewsList.adapter = newsRecyclerViewAdapter
    }

    private fun onItemClick(news: News) {
        val action = NewsListFragmentDirections.actionShowDetails(news.id.toString())
        findNavController().navigate(action)
    }
}