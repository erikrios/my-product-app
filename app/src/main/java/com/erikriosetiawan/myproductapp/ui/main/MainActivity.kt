package com.erikriosetiawan.myproductapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.erikriosetiawan.myproductapp.R
import com.erikriosetiawan.myproductapp.adapter.ProductAdapter
import com.erikriosetiawan.myproductapp.databinding.ActivityMainBinding
import com.erikriosetiawan.myproductapp.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        setUpViewModel()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private fun setUpUI() {
        // Observe the LiveData
        viewModel.apply {
            isShow.observe(this@MainActivity, Observer {
                binding.swipeMain.isRefreshing = it
            })
            products.observe(this@MainActivity, Observer {
                val adapter = ProductAdapter(this@MainActivity, it)
                binding.recyclerViewProduct.layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                binding.recyclerViewProduct.adapter = adapter
                adapter.notifyDataSetChanged()
            })
        }

        binding.swipeMain.setOnRefreshListener { viewModel.getDataProducts() }
    }
}