package com.example.swissborgtest.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.swissborgtest.R
import com.example.swissborgtest.databinding.MainFragmentBinding
import com.example.swissborgtest.ui.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by viewModels<MainViewModel>()

    private var binding by autoCleared<MainFragmentBinding>()

    private var askAdapter by autoCleared<OrderBookAdapter>()
    private var bidAdapter by autoCleared<OrderBookAdapter>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLists()
        observeOrderBook()

    }

    private fun setupLists(){
        askAdapter = OrderBookAdapter()
        binding.askList.adapter = askAdapter

        bidAdapter = OrderBookAdapter()
        binding.bidList.adapter = bidAdapter
    }

    private fun observeOrderBook(){
        viewModel.askOrderBooks.observe(viewLifecycleOwner, {
            Timber.d("submitList ${it.size}")
            askAdapter.submitList(it)
        })
        viewModel.bidOrderBooks.observe(viewLifecycleOwner, {
            bidAdapter.submitList(it)
        })
    }
}