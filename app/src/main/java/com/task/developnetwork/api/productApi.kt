package com.task.developnetwork.api

import com.task.developnetwork.models.ProductRespond
import retrofit2.http.GET

interface ProductApi {
    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }

    @GET("products")
    suspend fun getProducts(): ProductRespond

}

