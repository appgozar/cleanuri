package com.example.test.model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.test.R
import com.example.test.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async

class MainViewModel @ViewModelInject constructor(private val repository : LinkRepository) : ViewModel() {
    private var work : Job?= null

    val responseLiveData = MutableLiveData<Resource<CleanUriResponse>>()

    fun shorten(url : String) : Boolean{
        if(!url.matches("^http(|s)://.+\\..+".toRegex())){
            responseLiveData.value = Resource.getIdle()
            return false
        }
        responseLiveData.value = Resource.getLoading()

        if(work?.isActive == true)
            return true

        work = viewModelScope.async(Dispatchers.IO) {
            val resultUrl = repository.shortenUrl(url)
            val resource = if(resultUrl != null) Resource(Resource.IDLE, CleanUriResponse(resultUrl, 0)) else Resource.getError()
            responseLiveData.postValue(resource)
            resultUrl?.also { repository.insertLink(it) }
        }
        return true
    }
}