package com.erikriosetiawan.myproductapp.ui.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erikriosetiawan.myproductapp.data.api.ProductService
import com.erikriosetiawan.myproductapp.data.api.ServiceBuilder
import com.erikriosetiawan.myproductapp.data.model.Product
import com.erikriosetiawan.myproductapp.data.model.ProductResponse
import com.erikriosetiawan.myproductapp.ui.main.DetailsActivity.Companion.PRODUCT_ID_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel(private val activity: Activity) : ViewModel() {

    private val LOG = DetailsViewModel::class.java.simpleName

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    private val _isShow = MutableLiveData<Boolean>()
    val isShow: LiveData<Boolean>
        get() = _isShow

    private fun showProgress(show: Boolean) {
        _isShow.value = show
    }

    init {
        getProduct()
    }

    private fun getProduct() {
        showProgress(true)
        val productId = getIntent()
        val productService = ServiceBuilder.buildService(ProductService::class.java)
        val requestCall = productService.getProduct(productId)

        requestCall.enqueue(object : Callback<ProductResponse> {

            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                showProgress(false)
                if (response.isSuccessful)
                    _product.postValue(response.body()?.response?.get(0))
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                showProgress(false)
                t.message?.let { Log.e(LOG, it) }
            }
        })
    }

    private fun getIntent(): Int =
        activity.intent.getIntExtra(PRODUCT_ID_KEY, -1);

}