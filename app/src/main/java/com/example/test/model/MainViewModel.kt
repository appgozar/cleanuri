package com.example.test.model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.test.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async

class MainViewModel @ViewModelInject constructor(private val repository : LinkRepository) : ViewModel() {
    private var shortenLinkWork : Job?= null
    val responseLiveData = MutableLiveData<Resource<CleanUriResponse>>()

    fun shorten(url : String) : Boolean{
        if(!url.matches("^http(|s)://.+\\..+".toRegex())){
            responseLiveData.value = Resource.getIdle()
            return false
        }

        if(shortenLinkWork?.isActive == true)
            return true

        responseLiveData.value = Resource.getLoading()

        shortenLinkWork = viewModelScope.async(Dispatchers.IO) {
            val resultUrl = repository.shortenUrl(url)
            val resource = if(resultUrl != null) Resource(Resource.IDLE, CleanUriResponse(resultUrl, 0)) else Resource.getError()
            responseLiveData.postValue(resource)
        }
        return true
    }
}