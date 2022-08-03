package com.task.developnetwork.models

data class ProductRespond(
    val products: MutableList<Product>,
    val total: Long,
    val skip: Long,
    val limit: Long,
)

data class Product(
    val id: Long,
    val title: String,
    val description: String,
    val price: Long,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Long,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>,
)
