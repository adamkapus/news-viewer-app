package hu.autsoft.nytimesmostpopular.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.autsoft.nytimesmostpopular.domain.NewsInteractor
import hu.autsoft.nytimesmostpopular.model.News
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    val newsInteractor: NewsInteractor
) : ViewModel() {

    val news: MutableLiveData<News> by lazy {
        MutableLiveData<News>()
    }

    fun loadNews(id: Double) = viewModelScope.launch {
        val result = newsInteractor.getSingleNews(id)
        if (result != null) {
            news.postValue(result)
        }
    }

}