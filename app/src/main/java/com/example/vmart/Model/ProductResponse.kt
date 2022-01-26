package com.example.vmart.Model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ProductResponse(

    @SerializedName("products") var products: ArrayList<Products> = arrayListOf()

)

@Parcelize
@Entity(tableName = "cart_product")
data class Products(
    @SerializedName("name") var name: String? = null,
    @PrimaryKey
    @SerializedName("id") var id: Int? = null,
    @SerializedName("product_id") var productId: String? = null,
    @SerializedName("sku") var sku: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("thumb") var thumb: String? = null,
    @SerializedName("zoom_thumb") var zoomThumb: String? = null,
  //  @SerializedName("options") var options: ArrayList<String> = arrayListOf(),
    @SerializedName("description") var description: String? = null,
    @SerializedName("href") var href: String? = null,
    @SerializedName("quantity") var quantity: Int? = null,
   // @SerializedName("images") var images: ArrayList<String> = arrayListOf(),
    @SerializedName("price") var price: String? = null,
    @SerializedName("special") var special: String? = null,
    var cartQty:Int = 0

):Parcelable