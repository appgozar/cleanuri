package com.example.test.data

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LinkRepository @Inject constructor(private val cleanUriApi : CleanUriApi, private val linkDao: LinkDao) {

    suspend fun shortenUrl(url : String) : String?{
        return try { cleanUriApi.shortenUrl(url).shortenUrl }catch (e : Exception){ null }
    }

    suspend fun insertLink(url : String) {
        linkDao.insertLink(LinkItem(url = url))
    }
}