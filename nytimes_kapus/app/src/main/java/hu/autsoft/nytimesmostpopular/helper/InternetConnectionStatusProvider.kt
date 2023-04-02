package hu.autsoft.nytimesmostpopular.helper

import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternetConnectionStatusProvider @Inject constructor() {
    val internetConnectionIsActive: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun setInternetConnectionStatus(isActive: Boolean) {
        internetConnectionIsActive.postValue(isActive)
    }

}