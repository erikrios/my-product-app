package com.erikriosetiawan.myproductapp.data.api

import com.erikriosetiawan.myproductapp.data.model.Product
import com.erikriosetiawan.myproductapp.data.model.ProductResponse
import com.erikriosetiawan.myproductapp.data.model.ProductResultResponse
import retrofit2.Call
import retrofit2.http.*

interface ProductService {

    // Get all products
    @GET("api/products")
    fun getProducts(): Call<ProductResponse>

    // Get product by id
    @GET("api/products/{id}")
    fun getProduct(@Path("id") id: Int): Call<ProductResponse>

    // Add new product
    @POST("api/products")
    fun addProduct(@Body newProduct: Product): Call<ProductResultResponse>

    // Update the existing product
    @Headers("Content-Type: application/json")
    @PUT("api/products/{id}")
    fun updateProduct(
        @Path("id") id: Int,
        @Body product: Product
    ): Call<ProductResultResponse>

    // Delete product
    @DELETE("api/products/{id}")
    fun deleteProduct(@Path("id") id: Int): Call<ProductResultResponse>
}