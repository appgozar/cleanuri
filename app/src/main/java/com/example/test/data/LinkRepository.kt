package com.example.test.data

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LinkRepository @Inject constructor(private val cleanUriApi : CleanUriApi, private val linkDao: LinkDao) {

    suspend fun shortenUrl(url : String) : String?{
        val resultUrl =  try { cleanUriApi.shortenUrl(url).shortenUrl }catch (e : Exception){ null }
        if(resultUrl != null)
            linkDao.insertLink(LinkItem(url = resultUrl, originalUrl = url))
        return resultUrl
    }
}