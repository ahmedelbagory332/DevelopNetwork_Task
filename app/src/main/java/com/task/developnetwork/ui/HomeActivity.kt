package com.task.developnetwork.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.task.developnetwork.R
import com.task.developnetwork.adapter.ProductAdapter
import com.task.developnetwork.data.UserSharedEmail
import com.task.developnetwork.models.Product
import com.task.developnetwork.ui.viewModels.ProductsViewModel
import com.task.developnetwork.ui.viewModels.UserAuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val productsViewModel: ProductsViewModel by  viewModels()
    private val userAuthViewModel: UserAuthViewModel by  viewModels()

    @Inject
    lateinit var productAdapter: ProductAdapter
    @Inject
    lateinit  var userSharedEmail: UserSharedEmail
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_main)

        supportActionBar!!.title = "Home"

        productsViewModel.getProducts()

        val recyclerProducts:RecyclerView = findViewById(R.id.rv_products)
        val progressBar:ProgressBar = findViewById(R.id.dataLoading)
        val linearLayoutErrorMessage:LinearLayout = findViewById(R.id.layoutError)

        recyclerProducts.adapter = productAdapter
        productAdapter.onProductClick={
            showProductDetails(it)
        }

        productsViewModel.products.observe(this, Observer {
            productAdapter.submitList(it)
        })

        productsViewModel.isLoading.observe(this, Observer {
            if (it){
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })

        productsViewModel.showErrorMessage.observe(this, Observer {
            if (it) {
                linearLayoutErrorMessage.visibility = View.VISIBLE
                progressBar.visibility = View.GONE


            }else {
                productsViewModel.getProducts()
                linearLayoutErrorMessage.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
        })
    }

    private fun  showProductDetails(product: Product){
        val detailsDialogView: View = LayoutInflater.from(this).inflate(R.layout.product_dialog, null)
        val detailsDialog: AlertDialog = AlertDialog.Builder(this).create()
        val productImage:ImageView = detailsDialogView.findViewById(R.id.img_product_dialog)

        Picasso.get().load(product.images[0]).placeholder(R.drawable.image_place_holder)
            .fit().noFade().centerInside().into(productImage, object : Callback {
            override fun onSuccess() {
                productImage.alpha = 0f
                productImage.animate().setDuration(200).alpha(1f).start()
            }

            override fun onError(e: Exception) {}
        })

        detailsDialogView.findViewById<TextView>(R.id.tv_title_product_dialog).text = product.title
        detailsDialogView.findViewById<TextView>(R.id.tv_price_product_dialog).text = product.price.toString()
        detailsDialogView.findViewById<TextView>(R.id.tv_description_product_dialog).text = product.description

        detailsDialog.setView(detailsDialogView)


        detailsDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.logout, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
               userAuthViewModel.updateUserStatus(userSharedEmail.getUserMail(),"false")
                startActivity(Intent(this,SignInActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}