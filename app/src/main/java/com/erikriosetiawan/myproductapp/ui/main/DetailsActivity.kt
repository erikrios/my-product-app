package com.erikriosetiawan.myproductapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.erikriosetiawan.myproductapp.R
import com.erikriosetiawan.myproductapp.data.model.Product
import com.erikriosetiawan.myproductapp.databinding.ActivityDetailsBinding
import com.erikriosetiawan.myproductapp.ui.viewmodel.DetailsViewModel
import com.erikriosetiawan.myproductapp.ui.viewmodel.DetailsViewModelFactory

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var viewModel: DetailsViewModel

    companion object {
        const val PRODUCT_ID_KEY = "product_id_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        setUpViewModel()
        setUpUI()
    }

    private fun setUpViewModel() {
        val viewModelFactory = DetailsViewModelFactory(this)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java)
    }

    private fun setUpUI() {

        // Observe the live data
        viewModel.apply {
            isShow.observe(this@DetailsActivity, Observer {
                if (it)
                    binding.progressBar.visibility = View.VISIBLE
                else
                    binding.progressBar.visibility = View.INVISIBLE
            })

            product.observe(this@DetailsActivity, Observer {
                binding.apply {
                    textViewProductId.text = it.productId.toString()
                    editTextProductName.setText(it.productName)
                    editTextProductPrice.setText(it.productPrice)
                }
            })
        }

        // Update the data
        binding.buttonUpdateData.setOnClickListener {
            val productId = binding.textViewProductId.text.toString().toInt()
            val productName = binding.editTextProductName.text.toString()
            val productPrice = binding.editTextProductPrice.text.toString()
            val newProduct = Product(productId, productName, productPrice)
            viewModel.updateProduct(newProduct)
        }
    }
}
