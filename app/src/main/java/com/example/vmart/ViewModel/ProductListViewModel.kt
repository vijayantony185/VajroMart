package com.example.vmart.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.vmart.Model.ProductResponse
import com.example.vmart.Repo.ProductListRepo

class ProductListViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var productListRepo: ProductListRepo
    var productlivedate : LiveData<ProductResponse>? = null
    var initialize = false

    fun getProducts(url : String?){
        if (!initialize){
            productListRepo = ProductListRepo()
            this.productlivedate = productListRepo.getProducts(url)
        }
    }

    fun getLiveData() : LiveData<ProductResponse>?{
        return productlivedate
    }
}