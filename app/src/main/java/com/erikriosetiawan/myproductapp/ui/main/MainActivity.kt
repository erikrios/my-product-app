package com.erikriosetiawan.myproductapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.erikriosetiawan.myproductapp.R
import com.erikriosetiawan.myproductapp.adapter.ProductAdapter
import com.erikriosetiawan.myproductapp.data.model.Product
import com.erikriosetiawan.myproductapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        testRecyclerView()
    }

    private fun testRecyclerView() {
        val products: MutableList<Product> = mutableListOf()
        for (i in 0..20) {
            products.add(
                Product(i, "Produk $i", "Rp. ${i}.000,00")
            )
        }
        binding.recyclerViewProduct.apply {
            adapter = ProductAdapter(this@MainActivity, products)
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }
}
