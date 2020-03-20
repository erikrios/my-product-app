package com.erikriosetiawan.myproductapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.erikriosetiawan.myproductapp.data.model.Product
import com.erikriosetiawan.myproductapp.databinding.ListItemBinding
import com.erikriosetiawan.myproductapp.ui.main.DetailsActivity
import com.erikriosetiawan.myproductapp.ui.main.DetailsActivity.Companion.PRODUCT_ID_KEY

class ProductAdapter(private val context: Context, private val products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = ListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(products[position]) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(PRODUCT_ID_KEY, products[position].productId)
        }

    inner class ViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, clickListener: (Product) -> Unit) {
            binding.textViewProductName.text = product.productName
            itemView.setOnClickListener { clickListener(product) }
        }
    }
}