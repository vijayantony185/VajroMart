package com.example.vmart.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.get
import com.example.vmart.Constents.Constants
import com.example.vmart.R
import com.example.vmart.RoomDataBase.RoomDB
import com.example.vmart.databinding.ActivityCheckOutPageBinding

class CheckOutPageActivity : AppCompatActivity() {
    var total_amount : String? = null
    var option : String? = null
    private lateinit var binding : ActivityCheckOutPageBinding
    lateinit var database: RoomDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckOutPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = RoomDB.getInstance(this)

        total_amount = intent.getStringExtra(Constants.TOTAL_AMOUNT)
        binding.tvTotalPrice.setText("₹ "+total_amount.toString())
        binding.tvSubtotalPrice.setText("₹ "+total_amount.toString())
        toolbar()
        onclicks()
    }

    fun toolbar() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.toolbar.inflateMenu(R.menu.home_icon)
        binding.toolbar.menu.get(0).setOnMenuItemClickListener { menuItem ->
           homeActivity()
            true
        }
    }

    public fun onclicks(){
        binding.radioCard.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                binding.radioNetBanking.isChecked = false
                binding.radioUpi.isChecked = false
                option = "Card"
            }
        }

        binding.radioUpi.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                binding.radioNetBanking.isChecked = false
                binding.radioCard.isChecked = false
                option = "Upi"
            }
        }

        binding.radioNetBanking.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                binding.radioCard.isChecked = false
                binding.radioUpi.isChecked = false
                option = "Net Banking"
            }
        }

        binding.btnProceed.setOnClickListener {

            if (option != null){
                database.productDao().resetProduct(database.productDao().allCartProducts)
                var intent = Intent(applicationContext,OrderPlacedActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(applicationContext,"Please Select Your Payment Method", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun homeActivity(){
        var intent = Intent(applicationContext,HomeActivity::class.java)
        startActivity(intent)
    }
}