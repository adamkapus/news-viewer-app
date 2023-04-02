package hu.autsoft.nytimesmostpopular.ui

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import hu.autsoft.nytimesmostpopular.R
import hu.autsoft.nytimesmostpopular.databinding.ActivityMainBinding
import hu.autsoft.nytimesmostpopular.helper.InternetConnectionStatusProvider
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var internetConnectionStatusProvider: InternetConnectionStatusProvider

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        val connectivityManager =
            getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)

        internetConnectionStatusProvider.internetConnectionIsActive.observe(this)
        {
            if (!it) {
                Snackbar.make(
                    binding.root,
                    R.string.no_internet,
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        // network is available for use
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            internetConnectionStatusProvider.setInternetConnectionStatus(true)
        }

        // lost network connection
        override fun onLost(network: Network) {
            internetConnectionStatusProvider.setInternetConnectionStatus(false)
        }
    }

}