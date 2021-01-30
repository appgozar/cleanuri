package com.example.test.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.adapter.HistoryAdapter
import com.example.test.databinding.HistoryFragmentBinding
import com.example.test.model.HistoryViewModel

class HistoryFragment : Fragment() {

    private lateinit var binding : HistoryFragmentBinding
    private lateinit var viewModel : HistoryViewModel
    private val adapter = HistoryAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        viewModel.linkHistory.observe(viewLifecycleOwner){ adapter.submitList(it) }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }
}