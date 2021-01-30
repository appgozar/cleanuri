package com.example.test.model

import android.app.Application
import androidx.lifecycle.*
import com.example.test.data.AppDb
import com.example.test.data.CleanUriApi
import com.example.test.data.CleanUriResponse
import com.example.test.data.LinkItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import java.io.IOException

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var work : Job?= null
    private var api : CleanUriApi ?= null

    val shortenUrlLiveData = MutableLiveData<CleanUriResponse?>()
    val progressBarLiveData = MutableLiveData<Boolean>()

    fun shorten(url : String){
        if(work?.isActive == true)
            return

        val api = api ?: CleanUriApi.prepare().also { api = it }

        work = viewModelScope.async(Dispatchers.IO) {
            val serverResponse = try { api.shortenUrl(url) }catch (e : Exception){ CleanUriResponse(null, -1) }
            shortenUrlLiveData.postValue(serverResponse)
            if(serverResponse.shortenUrl != null)
                AppDb.getInstance(getApplication()).linkDao().insertLinks(listOf(LinkItem(url = serverResponse.shortenUrl)))
        }
    }
}