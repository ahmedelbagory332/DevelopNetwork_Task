package com.task.developnetwork.data
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.task.developnetwork.api.ProductApi
import com.task.developnetwork.models.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class ProductRepository @Inject constructor(private val api: ProductApi) {

    private val _isLoading = MutableStateFlow(true)
    private val _showErrorMessage = MutableStateFlow(false)
    private val _products = MutableStateFlow<MutableList<Product>>(mutableListOf())

    val isLoading: LiveData<Boolean>
        get() = _isLoading.asLiveData()

    val showErrorMessage: LiveData<Boolean>
        get() = _showErrorMessage.asLiveData()

    val products: LiveData<MutableList<Product>>
        get() = _products.asLiveData()


    fun getProducts() {

        CoroutineScope(Dispatchers.IO).launch {
            _isLoading.emit(true)
            try {


                _products.emit(api.getProducts().products)
                _isLoading.emit(false)

            } catch (e: Exception) {
                _isLoading.emit(false)
                _showErrorMessage.emit(true)


            }


        }

    }

}