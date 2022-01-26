package com.example.vmart.Repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vmart.Model.ProductResponse
import com.example.vmart.Retrofit.ApiInterface
import com.example.vmart.Retrofit.RetrofitRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListRepo {

    var apiRequest : ApiInterface

    init {
        apiRequest = RetrofitRequest.getRetrofitInstance()!!.create(ApiInterface::class.java)
    }

    fun getProducts(url : String?) : LiveData<ProductResponse>{
        val data: MutableLiveData<ProductResponse> = MutableLiveData()
        apiRequest.getproducts(url!!).enqueue(object : Callback<ProductResponse>{
            override fun onResponse(p0: Call<ProductResponse>, p1: Response<ProductResponse>) {
                if (p1.body() != null) {
                    data.setValue(p1.body())
                    Log.d("Status succes","------------------->success")

                }else{
                    Log.d("Status failed","------------------->${p1.code()}")

                }
            }

            override fun onFailure(p0: Call<ProductResponse>, p1: Throwable) {
                Log.d("Status Failed","------------------->${p1.message}")
                data.setValue(null)
            }

        })
        return data
    }
}