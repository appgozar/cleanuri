package com.example.test.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.test.R
import com.example.test.data.*
import com.example.test.databinding.MainFragmentBinding
import com.example.test.model.MainViewModel
import com.example.test.util.Util
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding : MainFragmentBinding ?= null
    private val binding : MainFragmentBinding
        get() = _binding!!
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setupUi()
        setupObserver()
    }

    private fun setupUi(){
        binding.textInputEditText.doOnTextChanged { _, _, _, _ ->
            if(binding.textInputLayout.error != null)
                binding.textInputLayout.error = null
        }
        binding.historyButton.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_historyFragment)
        }
        binding.tvResult.setOnClickListener { copyToClipboard() }
        binding.submitButton.setOnClickListener { shortUrl() }
    }
    private fun setupObserver(){
        viewModel.responseLiveData.observe(viewLifecycleOwner){ resurce ->
            binding.progressbar.visibility = if(resurce.status == Resource.LOADING) View.VISIBLE else View.GONE
            binding.tvResult.text = resurce.data?.shortenUrl
            if(resurce.status == Resource.ERROR){
                // todo handle specific error code
                Snackbar.make(binding.root, R.string.error_connection, Snackbar.LENGTH_SHORT).show()
                viewModel.responseLiveData.value = Resource(Resource.IDLE, null)
            }
        }
    }

    private fun shortUrl(){
        Util.hideKeyboard(requireContext(), binding.textInputEditText)
        val url = binding.textInputEditText.text?.toString() ?: ""
        if(url.matches("^http(|s)://.+\\..+".toRegex())){
            viewModel.responseLiveData.value = Resource.getLoading()
            viewModel.shorten(url)
        }else{
            viewModel.responseLiveData.value = Resource.getIdle()
            binding.textInputLayout.error = getString(R.string.error_invalid_url)
        }
    }

    private fun copyToClipboard(){
        val url = viewModel.responseLiveData.value?.data?.shortenUrl ?: return
        Util.copyToClipboard(requireContext(), url)
        Snackbar.make(binding.root, R.string.copied_to_clipboard, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}