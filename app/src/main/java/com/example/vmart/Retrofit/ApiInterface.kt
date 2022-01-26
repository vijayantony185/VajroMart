package com.example.vmart.Retrofit

import com.example.vmart.Model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

interface ApiInterface {
    @Headers("Content-Type: application/json")
    @GET
    fun getproducts(@Url url: String): retrofit2.Call<ProductResponse>
}