package com.example.lab1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.model.Product
import java.text.NumberFormat
import java.util.*
import com.bumptech.glide.Glide

class ProductAdapter(
    private var products: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProductImage: ImageView = itemView.findViewById(R.id.iv_product_image)
        val tvProductName: TextView = itemView.findViewById(R.id.tv_product_name)
        val tvProductPrice: TextView = itemView.findViewById(R.id.tv_product_price)
        val tvProductDescription: TextView = itemView.findViewById(R.id.tv_product_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        
        holder.tvProductName.text = product.name
        holder.tvProductPrice.text = NumberFormat.getCurrencyInstance(Locale("vi", "VN")).format(product.price)
        holder.tvProductDescription.text = product.description
        
        // Sử dụng hình ảnh từ drawable thay vì URL
        when (product.id) {
            1 -> holder.ivProductImage.setImageResource(R.drawable.product_iphone)
            2 -> holder.ivProductImage.setImageResource(R.drawable.product_samsung)
            // ... các sản phẩm khác
            else -> holder.ivProductImage.setImageResource(R.drawable.ic_person)
        }
        
        holder.itemView.setOnClickListener {
            onItemClick(product)
        }
    }

    override fun getItemCount(): Int = products.size

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}