package com.example.vmart.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vmart.Adapters.CartAdapter
import com.example.vmart.Model.Products
import com.example.vmart.R
import com.example.vmart.ViewModel.ProductListViewModel
import com.example.vmart.databinding.ActivityCartBinding
import android.view.animation.TranslateAnimation
import androidx.core.graphics.drawable.toDrawable
import com.example.vmart.Constents.Constants
import com.example.vmart.Constents.Progress
import com.example.vmart.RoomDataBase.RoomDB
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {
    var progress: Progress? = null
    lateinit var cartAdapter: CartAdapter
    lateinit var binding : ActivityCartBinding
    lateinit var database: RoomDB
    var total_price : Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = RoomDB.getInstance(this)
        toolbar()
        getProductsList()
        onclicks()
    }

     fun onclicks(){
        binding.imgInfo.setOnClickListener {
           binding.laySubtotal.visibility = View.VISIBLE
            binding.imgDown.visibility = View.VISIBLE
            binding.imgInfo.visibility = View.GONE
            binding.layCheckout.background = resources.getColor(R.color.light_gray).toDrawable()
        }

        binding.imgDown.setOnClickListener {
            binding.laySubtotal.visibility = View.GONE
            binding.imgDown.visibility = View.GONE
            binding.imgInfo.visibility = View.VISIBLE
            binding.layCheckout.background = resources.getColor(R.color.white).toDrawable()
        }
         binding.btnCheckout.setOnClickListener {
             var intent = Intent(applicationContext,CheckOutPageActivity::class.java)
             intent.putExtra(Constants.TOTAL_AMOUNT,""+total_price)
             startActivity(intent)
         }
    }

    fun getProductsList(){
        if (database.productDao().allCartProducts.size == 0){
            binding.gifCart.visibility = View.VISIBLE
            binding.tvCartEmpty.visibility = View.VISIBLE
            binding.rcvCart.visibility = View.GONE
        }else{
            binding.gifCart.visibility = View.GONE
            binding.rcvCart.visibility = View.VISIBLE
            binding.tvCartEmpty.visibility = View.GONE
            setCartRecyclerview(database.productDao().allCartProducts)
            calcTotal()
        }
    }

     fun toolbar() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.toolbar.inflateMenu(R.menu.home_icon)
        binding.toolbar.menu.get(0).setOnMenuItemClickListener { menuItem ->
            onBackPressed()
            true
        }
    }

     fun setCartRecyclerview(productslist : List<Products>){
        cartAdapter = CartAdapter(applicationContext, productslist as ArrayList<Products>,object : CartAdapter.CartClick{
            override fun updateUi() {
                calcTotal()
            }
        })
        binding.rcvCart.adapter = cartAdapter
        binding.rcvCart.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun calcTotal(){
        var totalAmount = 0
        if (database.productDao().allCartProducts.size <= 0){
            finish()
        }
        for (Products in database.productDao().allCartProducts){
            val price = Products.price?.filter { it.isDigit() }
            totalAmount += (price!!.toInt()* Products.cartQty)
        }
        binding.tvTotalPrice.text ="Total : ₹ "+ totalAmount.toString()
        binding.tvSubtotalPrice.text = "Sub Total : ₹ "+ totalAmount.toString()
        total_price = totalAmount
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
}