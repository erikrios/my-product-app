package com.erikriosetiawan.myproductapp.data.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("product_id")
    var productId: Int? = null,
    @SerializedName("product_name")
    var productName: String? = "",
    @SerializedName("product_price")
    var productPrice: String? = ""
)