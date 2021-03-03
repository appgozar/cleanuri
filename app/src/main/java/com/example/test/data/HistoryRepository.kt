package com.example.test.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryRepository @Inject constructor(private val linkDao: LinkDao) {
    fun getLinks() = linkDao.getLinks(0, 30)
}