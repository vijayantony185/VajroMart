package com.example.vmart.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vmart.Interfaces.StepperInterface
import com.example.vmart.Model.Products
import com.example.vmart.R
import com.example.vmart.RoomDataBase.RoomDB
import com.example.vmart.databinding.AdapterProductsBinding
import java.lang.String

class ProductsAdapter(var context: Context,var model : ArrayList<Products>,var productsclick : Productsclick) : RecyclerView.Adapter<ProductsAdapter.ProductHolder>(),Filterable, StepperInterface {

    var searchmodel : ArrayList<Products>? = null
    var database: RoomDB

    init {
        searchmodel = model as ArrayList<Products>
        database = RoomDB.getInstance(context)
    }

    inner class ProductHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        lateinit var img_product : ImageView
        lateinit var btn_add : TextView
        lateinit var stepper_text : TextView
        lateinit var stepper : com.example.vmart.CartStorage.Stepper
        lateinit var tv_productname : TextView
        lateinit var tv_price : TextView
        fun bindview(products: Products, position: Int) {
            img_product = itemView.findViewById(R.id.img_product)
            btn_add = itemView.findViewById(R.id.btn_add)
            stepper = itemView.findViewById(R.id.stepper)
            stepper_text = stepper.findViewById(R.id.txt_count)
            tv_productname = itemView.findViewById(R.id.tv_productname)
            tv_price = itemView.findViewById(R.id.tv_price)
            btn_add.setOnClickListener {
                products.cartQty = 1
                database.productDao().insertProduct(products)
                updateUI(products)
                productsclick.updateUi()
            }
            Glide.with(context).load(products.image).into(img_product)
            tv_productname.setText(""+products.name)
            tv_price.setText(""+products.price)
            updateUI(products)
            stepper.productId = products.id!!
            stepper.stepperInterFace = this@ProductsAdapter
        }

        private fun updateUI(product: Products) {
            val cartProduct =  database.productDao().getProductById(product.id!!)
            if (cartProduct != null) {
                stepper.setVisibility(View.VISIBLE)
                btn_add.setVisibility(View.INVISIBLE)
                stepper.currentValue = cartProduct.cartQty
                stepper_text.setText(String.valueOf(cartProduct.cartQty))
            } else {
                stepper.setVisibility(View.INVISIBLE)
                btn_add.setVisibility(View.VISIBLE)
            }
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductHolder {
        val view : View = LayoutInflater.from(p0.context).inflate(R.layout.adapter_products,null,false)
        return ProductHolder(view)
    }

    override fun onBindViewHolder(p0: ProductHolder, p1: Int) {
        p0.bindview(searchmodel!![p1] , p1)
    }

    override fun getItemCount(): Int {
        return searchmodel!!.size
    }

    interface Productsclick {
       fun updateUi()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    searchmodel = model as ArrayList<Products>
                } else {
                    val resultList = ArrayList<Products>()
                    for (row in model) {
                        if (row.name!!.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            resultList.add(row)
                        }else if (row.name!!.toLowerCase().contains(constraint.toString().toLowerCase())){
                            resultList.add(row)
                        }
                    }
                    searchmodel = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = searchmodel
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                searchmodel  = results?.values as ArrayList<Products>
                notifyDataSetChanged()
            }
        }
    }

    override fun didChanged(position:Int,productId: Int, value: Int) {
        if (value <= 0){
            val Products =  database.productDao().getProductById(productId)
            database.productDao().deleteProduct(Products)
            productsclick.updateUi()
            notifyDataSetChanged()
        }else
       database.productDao().updateProduct(productId , value)
    }
}