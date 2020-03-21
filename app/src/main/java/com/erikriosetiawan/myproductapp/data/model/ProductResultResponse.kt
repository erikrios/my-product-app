package com.erikriosetiawan.myproductapp.data.model

import com.google.gson.annotations.SerializedName

data class ProductResultResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("error")
    val error: String? = null,
    @SerializedName("response")
    val response: Response
)

data class Response(
    @SerializedName("fieldCount")
    val fieldCount: Int,
    @SerializedName("affectedRows")
    val affectedRows: Int,
    @SerializedName("insertId")
    val insertId: Int,
    @SerializedName("serverStatus")
    val serverStatus: Int,
    @SerializedName("warningCount")
    val warningCount: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("protocol41")
    val protocol41: Boolean,
    @SerializedName("changedRows")
    val changedRows: Int
)