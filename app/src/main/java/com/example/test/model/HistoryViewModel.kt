package com.example.test.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.example.test.data.AppDb

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    val linkHistory = AppDb.getInstance(application).linkDao().getLinks(0, 20).asLiveData()
}