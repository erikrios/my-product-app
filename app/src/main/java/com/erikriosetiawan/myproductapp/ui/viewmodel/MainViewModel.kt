package com.erikriosetiawan.myproductapp.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erikriosetiawan.myproductapp.data.api.ProductService
import com.erikriosetiawan.myproductapp.data.api.ServiceBuilder
import com.erikriosetiawan.myproductapp.data.model.Product
import com.erikriosetiawan.myproductapp.data.model.ProductResponse
import com.erikriosetiawan.myproductapp.ui.main.DetailsActivity
import com.erikriosetiawan.myproductapp.ui.main.DetailsActivity.Companion.PRODUCT_ADD_ID_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val context: Context) : ViewModel() {

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
                else
                    Toast.makeText(
                        context,
                        "No response from server. Please try again later",
                        Toast.LENGTH_LONG
                    ).show()
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Toast.makeText(
                    context,
                    "No internet connection! Please check your internet connection",
                    Toast.LENGTH_LONG
                ).show()
                showProgress(false)
                t.message?.let { Log.e(LOG, it) }
            }
        })
    }

    fun addDataIntent() {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra(PRODUCT_ADD_ID_KEY, PRODUCT_ADD_ID_KEY)
        context.startActivity(intent)
    }
}