package hu.autsoft.nytimesmostpopular.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.autsoft.nytimesmostpopular.domain.NewsInteractor
import hu.autsoft.nytimesmostpopular.model.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(val newsInteractor : NewsInteractor): ViewModel() {

    fun getNewsResult(): LiveData<Result> {
        return newsInteractor.newsResult
    }

    fun initNews() = viewModelScope.launch {
        newsInteractor.loadNews(forceUpdate = false)
        newsInteractor.loadNews(forceUpdate = true)
    }

    fun loadNews() = viewModelScope.launch {
        newsInteractor.loadNews(forceUpdate = false)
    }

    fun refreshNews() = viewModelScope.launch {
        newsInteractor.loadNews(forceUpdate = true)
    }
}