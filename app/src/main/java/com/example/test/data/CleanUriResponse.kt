package com.example.test.data

import com.google.gson.annotations.SerializedName

data class CleanUriResponse(
    @field:SerializedName("result_url") val shortenUrl : String?,
    @field:SerializedName("error") val error : Int?
)