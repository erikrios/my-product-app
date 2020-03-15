package com.erikriosetiawan.myproductapp.data.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("error")
    val error: String? = null,
    @SerializedName("response")
    val response: List<Product>? = null
)