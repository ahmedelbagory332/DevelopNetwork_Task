package com.task.developnetwork.ui.viewModels

import androidx.lifecycle.ViewModel
import com.task.developnetwork.data.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    val isLoading = productRepository.isLoading
    val showErrorMessage = productRepository.showErrorMessage
    val products = productRepository.products

    fun getProducts() {
        productRepository.getProducts()
    }


}