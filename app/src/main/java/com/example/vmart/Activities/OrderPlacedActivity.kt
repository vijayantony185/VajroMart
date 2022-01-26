package com.example.vmart.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.vmart.R
import com.example.vmart.databinding.ActivityOrderPlacedBinding

class OrderPlacedActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderPlacedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderPlacedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(applicationContext,"Order Placed Successfully", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(applicationContext,HomeActivity::class.java)
            startActivity(intent)
        }, 2500)
    }
}