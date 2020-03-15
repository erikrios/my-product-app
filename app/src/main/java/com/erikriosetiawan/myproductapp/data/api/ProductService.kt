package com.erikriosetiawan.myproductapp.data.api

import com.erikriosetiawan.myproductapp.data.model.Product
import retrofit2.Call
import retrofit2.http.*

interface ProductService {

    // Get all products
    @GET("api/products")
    fun getProducts(): Call<List<Product>>

    // Get product by id
    @GET("api/products/{id}")
    fun getProduct(@Path("id") id: Int): Call<Product>

    // Add new product
    @POST("api/products")
    fun addProduct(@Body newProduct: Product): Call<Product>

    // Update the existing product
    @FormUrlEncoded
    @PUT("api/products/{id}")
    fun updateProduct(
        @Path("id") id: Int,
        @Field("product_name") productName: String,
        @Field("product_price") productPrice: String
    ): Call<Product>

    // Delete product
    @DELETE("api/products/{id}")
    fun deleteProduct(@Path("id") id: Int): Call<Unit>
}