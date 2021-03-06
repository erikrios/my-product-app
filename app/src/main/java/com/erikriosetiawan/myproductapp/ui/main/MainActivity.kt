package com.erikriosetiawan.myproductapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.erikriosetiawan.myproductapp.R
import com.erikriosetiawan.myproductapp.adapter.ProductAdapter
import com.erikriosetiawan.myproductapp.data.model.Product
import com.erikriosetiawan.myproductapp.databinding.ActivityMainBinding
import com.erikriosetiawan.myproductapp.ui.viewmodel.MainViewModel
import com.erikriosetiawan.myproductapp.ui.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    var productList = listOf<Product>()
    var adapter = ProductAdapter(this, productList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        setUpViewModel()
        setUpUI()
    }

    private fun setUpViewModel() {
        val viewModelFactory = MainViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private fun setUpUI() {

        // Observe the LiveData
        viewModel.apply {
            isShow.observe(this@MainActivity, Observer {
                binding.swipeMain.isRefreshing = it
            })
            products.observe(this@MainActivity, Observer {
                productList = it
                adapter = ProductAdapter(this@MainActivity, productList)
                binding.recyclerViewProduct.layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                binding.recyclerViewProduct.adapter = adapter
                adapter.notifyDataSetChanged()
            })
        }

        binding.recyclerViewProduct.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewProduct.adapter = adapter

        binding.swipeMain.setOnRefreshListener { viewModel.getDataProducts() }

        binding.floatingActionButton.setOnClickListener {
            viewModel.addDataIntent()
        }
    }

    override fun onResume() {
        super.onResume()
        // Update the data when onResume() function is called
        viewModel.getDataProducts()
    }
}