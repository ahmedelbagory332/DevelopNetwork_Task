package com.task.developnetwork.adapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.task.developnetwork.R
import com.task.developnetwork.models.Product
import javax.inject.Inject

class ProductAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onProductClick: ((product: Product) -> Unit)? = null
    private var productList = mutableListOf<Product>()

    override fun getItemViewType(position: Int): Int {
        val product = productList[position]
        return product.stock.toInt()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder {
        return if(viewType > 50){
            ProductWithBigStockViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_item_with_big_stock, parent, false))
        }else{
            ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = productList[position]

        if(holder.itemViewType > 50){
            val productWithBigStockViewHolder: ProductWithBigStockViewHolder = holder as ProductWithBigStockViewHolder
            productWithBigStockViewHolder.productTitle.text = product.title
            productWithBigStockViewHolder.productPrice.text = product.price.toString() + "LE"
            productWithBigStockViewHolder.productDescription.text = product.description
            productWithBigStockViewHolder.itemView.setOnClickListener {
                onProductClick!!.invoke(product)
            }
        }else{
            val productViewHolder: ProductViewHolder = holder as ProductViewHolder
            productViewHolder.productTitle.text = product.title
            productViewHolder.productPrice.text = product.price.toString() + "LE"
            productViewHolder.productDescription.text = product.description
            productViewHolder.itemView.setOnClickListener {
                onProductClick!!.invoke(product)
            }
        }

    }


    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val productTitle : TextView = itemView.findViewById(R.id.tv_title)
        val productPrice : TextView = itemView.findViewById(R.id.tv_price)
        val productDescription : TextView = itemView.findViewById(R.id.tv_description)

    }

    class ProductWithBigStockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val productTitle : TextView = itemView.findViewById(R.id.tv_title)
        val productPrice : TextView = itemView.findViewById(R.id.tv_price)
        val productDescription : TextView = itemView.findViewById(R.id.tv_description)
    }


    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: MutableList<Product>){
        productList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return  productList.size
    }

}


