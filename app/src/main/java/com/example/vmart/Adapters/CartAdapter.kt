package com.example.vmart.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vmart.Interfaces.StepperInterface
import com.example.vmart.Model.Products
import com.example.vmart.R
import com.example.vmart.RoomDataBase.RoomDB
import com.example.vmart.databinding.AdapterCartBinding
import java.lang.String

class CartAdapter(var context: Context,var model : ArrayList<Products>,var click : CartAdapter.CartClick) : RecyclerView.Adapter<CartAdapter.CartHolder>(),StepperInterface {

    var database: RoomDB
    var cartProducts : ArrayList<Products>? = null

    init {
        cartProducts = model as ArrayList<Products>
        database = RoomDB.getInstance(context)
    }

    interface CartClick {
        fun updateUi()
    }

    inner class CartHolder(val binding : AdapterCartBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var stepper_text : TextView
        fun bindview(products: Products , position:Int) {
            stepper_text = binding.stepper.findViewById(R.id.txt_count)
            Glide.with(context).load(products.image).into(binding.imgProduct)
            binding.tvProductName.setText(""+products.name)
            binding.tvProductPrice.setText(""+products.price)
            val price = products.price?.filter { it.isDigit() }
            binding.tvTotalPrice.setText("₹ "+(price!!.toInt() * products.cartQty))
            binding.stepper.productId = products.id!!
            binding.stepper.position = position
            binding.stepper.stepperInterFace = this@CartAdapter
            stepper_text.setText(String.valueOf(products.cartQty))
            binding.stepper.currentValue = products.cartQty
        }

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CartHolder {
       return CartHolder(AdapterCartBinding.inflate(LayoutInflater.from(p0.context),p0,false))
    }

    override fun onBindViewHolder(p0: CartHolder, p1: Int) {
        p0.bindview(cartProducts!![p1], p1)
    }

    override fun getItemCount(): Int {
        return cartProducts!!.size
    }

    override fun didChanged(position:Int, productId: Int, value: Int) {
        if (value <= 0){
            val Products =  database.productDao().getProductById(productId)
            database.productDao().deleteProduct(Products)
            click.updateUi()
            cartProducts!!.removeAt(position)
        }else {
            cartProducts!![position].cartQty = value
            database.productDao().updateProduct(productId, value)
            click.updateUi()
        }
           notifyDataSetChanged()
    }
}