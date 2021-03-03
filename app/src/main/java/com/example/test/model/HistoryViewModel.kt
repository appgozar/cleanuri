package com.example.test.model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.test.data.HistoryRepository

class HistoryViewModel @ViewModelInject constructor(linkRepository: HistoryRepository) : ViewModel() {
    val linkHistory = linkRepository.getLinks().asLiveData(viewModelScope.coroutineContext)
}