package com.erikriosetiawan.myproductapp.ui.viewmodel

import android.app.Activity
import android.util.Log
import android.widget.Toast
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

    private val productService = ServiceBuilder.buildService(ProductService::class.java)

    init {
        getProduct()
    }

    private fun getProduct() {
        showProgress(true)
        val productId = getIntent()
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

    private fun updateProduct(newProduct: Product) {
        var productId = 0
        var productName = ""
        var productPrice = ""

        newProduct.apply {
            this.productId?.let { productId = it }
            this.productName?.let { productName = it }
            this.productPrice?.let { productPrice = it }
        }

        val requestCall = productService.updateProduct(productId, productName, productPrice)

        requestCall.enqueue(object : Callback<ProductResponse> {

            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        activity.baseContext,
                        "Product update successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.i(LOG, "Updating the data is successfully")
                    activity.finish()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                t.message?.let { Log.e(LOG, it) }
                Toast.makeText(activity.baseContext, "Product uupdate failed", Toast.LENGTH_SHORT)
                    .show()
            }
        });
    }

    private fun getIntent(): Int =
        activity.intent.getIntExtra(PRODUCT_ID_KEY, -1);

}