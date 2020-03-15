package com.erikriosetiawan.myproductapp.ui.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erikriosetiawan.myproductapp.data.api.ProductService
import com.erikriosetiawan.myproductapp.data.api.ServiceBuilder
import com.erikriosetiawan.myproductapp.data.model.Product
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

    fun getDataProducts() {
        showProgress(true)
        val productService = ServiceBuilder.buildService(ProductService::class.java)
        val requestCall = productService.getProducts()

        requestCall.enqueue(object : Callback<List<Product>> {

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                showProgress(false)
                if (response.isSuccessful)
                    _products.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                showProgress(false)
                t.message?.let { Log.e(LOG, it) }
            }
        })
    }
}