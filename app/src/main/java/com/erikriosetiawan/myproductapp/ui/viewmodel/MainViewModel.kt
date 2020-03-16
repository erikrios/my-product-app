package com.erikriosetiawan.myproductapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erikriosetiawan.myproductapp.data.api.ProductService
import com.erikriosetiawan.myproductapp.data.api.ServiceBuilder
import com.erikriosetiawan.myproductapp.data.model.Product
import com.erikriosetiawan.myproductapp.data.model.ProductResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val LOG = MainViewModel::class.java.simpleName

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    private val _isShow = MutableLiveData<Boolean>()
    val isShow: LiveData<Boolean>
        get() = _isShow

    private fun showProgress(show: Boolean) {
        _isShow.value = show
    }

    init {
        getDataProducts()
    }

    fun getDataProducts() {
        showProgress(true)
        val productService = ServiceBuilder.buildService(ProductService::class.java)
        val requestCall = productService.getProducts()

        requestCall.enqueue(object : Callback<ProductResponse> {

            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                showProgress(false)
                if (response.isSuccessful)
                    _products.postValue(response.body()?.response)
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                showProgress(false)
                t.message?.let { Log.e(LOG, it) }
            }
        })
    }
}