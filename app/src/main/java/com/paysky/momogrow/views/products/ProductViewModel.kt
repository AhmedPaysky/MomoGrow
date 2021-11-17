package com.paysky.momogrow.views.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.paysky.momogrow.MyApplication
import com.paysky.momogrow.data.api.ApiServiceMomo
import com.paysky.momogrow.data.local.ProductEntity
import com.paysky.momogrow.helper.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException

class ProductViewModel(private val apiServiceMomo: ApiServiceMomo) : ViewModel() {
    val dao = MyApplication.db.productDao()

    //    fun allProducts() = dao.getAllProducts()
    fun getProductById(id: Int) = dao.getProductById(id)
    suspend fun deleteProduct(it: ProductEntity) = dao.delete(it)

    fun allProducts() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiServiceMomo.getAllProducts()))
        } catch (exception: HttpException) {
            emit(
                Resource.errorHttp(
                    data = exception,
                    message = exception.message ?: "Error occured"
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error occured"))
        }
    }

    fun deleteProduct(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiServiceMomo.deleteProduct(id)))
        } catch (exception: HttpException) {
            emit(
                Resource.errorHttp(
                    data = exception,
                    message = exception.message ?: "Error occured"
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error occured"))
        }
    }
}