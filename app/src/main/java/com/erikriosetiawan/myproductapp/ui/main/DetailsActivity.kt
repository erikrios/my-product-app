package com.erikriosetiawan.myproductapp.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
        const val PRODUCT_ADD_ID_KEY = "product_add_id_key"
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
        if (viewModel.intentId != PRODUCT_ADD_ID_KEY)
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

        // Set button text
        binding.buttonUpdateData.apply {
            if (viewModel.intentId == PRODUCT_ADD_ID_KEY)
                text = resources.getString(R.string.add_data)
        }

        // Add button click handling
        binding.buttonUpdateData.apply {
            if (viewModel.intentId == PRODUCT_ADD_ID_KEY) {
                // Add the data
                setOnClickListener {
                    val newProduct = Product(
                        null,
                        binding.editTextProductName.text.toString(),
                        binding.editTextProductPrice.text.toString()
                    )
                    viewModel.validateData(newProduct)
                }
            } else {
                // Update the data
                setOnClickListener {
                    val productId = binding.textViewProductId.text.toString().toInt()
                    val productName = binding.editTextProductName.text.toString()
                    val productPrice = binding.editTextProductPrice.text.toString()
                    val newProduct = Product(productId, productName, productPrice)
                    viewModel.updateProduct(newProduct)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (viewModel.intentId != PRODUCT_ADD_ID_KEY) {
            menuInflater.inflate(R.menu.menu_main, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_main -> {
                val id = binding.textViewProductId.text.toString().toInt()
                viewModel.deleteProduct(id)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
