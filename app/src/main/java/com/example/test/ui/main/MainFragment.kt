package com.example.test.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.test.R
import com.example.test.data.AppDb
import com.example.test.data.CleanUriApi
import com.example.test.data.CleanUriResponse
import com.example.test.data.LinkItem
import com.example.test.databinding.MainFragmentBinding
import com.example.test.model.MainViewModel
import com.example.test.util.Util
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private lateinit var binding : MainFragmentBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shortenUrlLiveData.observe(viewLifecycleOwner){ res -> res?.also { onShortUrlResult(it) } }
        binding.textInputEditText.doOnTextChanged { _, _, _, _ ->
            if(binding.textInputLayout.error != null)
                binding.textInputLayout.error = null
        }
        binding.historyButton.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_historyFragment)
        }
        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            binding.progressbar.visibility = if(it) View.VISIBLE else View.GONE
        }
        binding.tvResult.setOnClickListener { copyToClipboard() }
        binding.submitButton.setOnClickListener { shortUrl() }
    }

    private fun shortUrl(){
        binding.tvResult.text = null
        viewModel.shortenUrlLiveData.value = null

        Util.hideKeyboard(requireContext(), binding.textInputEditText)
        val url = binding.textInputEditText.text?.toString() ?: ""
        if(url.startsWith("http")){
            viewModel.shorten(url)
            viewModel.progressBarLiveData.value = true
        }else{
            binding.textInputLayout.error = getString(R.string.error_invalid_url)
        }
    }

    private fun onShortUrlResult(result : CleanUriResponse){
        viewModel.progressBarLiveData.value = false
        if(result.shortenUrl == null){
            // todo handle specific error code
            Snackbar.make(binding.root, R.string.error_connection, Snackbar.LENGTH_SHORT).show()
            viewModel.shortenUrlLiveData.value = null
        }else{
            binding.tvResult.text = result.shortenUrl
        }
    }

    private fun copyToClipboard(){
        val url = viewModel.shortenUrlLiveData.value?.shortenUrl ?: return
        Util.copyToClipboard(requireContext(), url)
        Snackbar.make(binding.root, R.string.copied_to_clipboard, Snackbar.LENGTH_SHORT).show()
    }
}