package com.example.aboutcanada.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.aboutcanada.databinding.FragmentMainBinding
import com.example.aboutcanada.model.Fact
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
            Log.d("livedata", it.toString())
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
        viewModel.getFacts()
        binding.sw.setOnRefreshListener {
            binding.sw.isRefreshing = false
            viewModel.getFacts()
        }
    }
}