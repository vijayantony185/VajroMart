package com.example.vmart.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vmart.Adapters.ProductsAdapter
import com.example.vmart.Constents.Progress
import com.example.vmart.Model.Products
import com.example.vmart.RoomDataBase.RoomDB
import com.example.vmart.ViewModel.ProductListViewModel
import com.example.vmart.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.toolbay_lay_1.*

class HomeActivity : AppCompatActivity() {

    var progress: Progress? = null
    lateinit var productListViewModel: ProductListViewModel
    var productsAdapter: ProductsAdapter? = null
    private lateinit var binding : ActivityMainBinding
    lateinit var database: RoomDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = RoomDB.getInstance(this)
        onclicks()
        getProductsList()
    }

     fun onclicks(){
        binding.edtSearchRest.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                productsAdapter!!.filter.filter(p0)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                productsAdapter!!.filter.filter(p0)
                return true
            }
        })

         img_cart.setOnClickListener {
            val intent = Intent(applicationContext,CartActivity::class.java)
            startActivity(intent)
        }
    }
    fun getProductsList(){
        showProgress()
        productListViewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java)
        productListViewModel.getProducts("5def7b172f000063008e0aa2")
        productListViewModel.getLiveData()!!.observe(this, Observer {
            if (it != null){
                cancelProgress()
                Log.d("response","------------------------>"+it)
                setProductRecyclerview(it.products)
            }
        })
    }

     fun setProductRecyclerview(productList : ArrayList<Products>){
        productsAdapter = ProductsAdapter(applicationContext,productList,object : ProductsAdapter.Productsclick{
            override fun updateUi() {
                txt_count.text = database.productDao().allCartProducts.size.toString()
            }
        })
        binding.rcvProducts.adapter = productsAdapter
        binding.rcvProducts.layoutManager = GridLayoutManager(applicationContext,2)
    }

    private fun showProgress() {
        progress = Progress(this)
        progress!!.show()
    }

    private fun cancelProgress() {
        if (progress != null) {
            progress!!.dismiss()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onResume() {
        super.onResume()
        txt_count.text = database.productDao().allCartProducts.size.toString()
        if(productsAdapter != null){
            productsAdapter!!.notifyDataSetChanged()
        }
    }
}