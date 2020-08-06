package com.example.aboutcanada.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.aboutcanada.R
import com.example.aboutcanada.databinding.FragmentMainBinding
import com.example.aboutcanada.model.Fact
import com.example.aboutcanada.util.isConnectedToInternet
import com.example.aboutcanada.util.t
import com.example.aboutcanada.viewmodel.AppResult
import com.example.aboutcanada.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var mainActivity: MainActivity? = null
    private val adapter by lazy {
        mainActivity?.let { MainAdapter(it) }
    }
    private var networkCallback: ConnectivityManager.NetworkCallback? = null
    private var isConnected: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvData.adapter = adapter
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
            when (it) {
                is AppResult.Success -> {
                    val fact = it.output as Fact
                    mainActivity?.setTitle(fact.title)
                    adapter?.setData(fact.rows)
                }
                is AppResult.Error -> {
                    mainActivity?.t(it.error)
                }
                is AppResult.IntError -> {
                    mainActivity?.t(it.error)
                }
                is AppResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
        getData()
        binding.sw.setOnRefreshListener {
            binding.sw.isRefreshing = false
            getData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val manager =
                mainActivity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    isConnected = true
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    isConnected = false
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    isConnected = false
                }
            }
            manager.registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)
            this.networkCallback = networkCallback
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val manager =
                mainActivity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            networkCallback?.let { manager.unregisterNetworkCallback(it) }
        }
    }

    private fun getData() {
        if ((mainActivity?.isConnectedToInternet() == true
                    && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) || isConnected
        ) {
            viewModel.getFacts()
        } else {
            mainActivity?.t(R.string.please_connect_to_internet)
        }
    }
}